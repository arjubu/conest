package com.bu.softwareengineering.contest.service.impl;

import com.bu.softwareengineering.contest.controller.dto.TeamUpdateDto;
import com.bu.softwareengineering.contest.domain.ContestTeam;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.TeamMember;
import com.bu.softwareengineering.contest.helpers.ServiceErrorsEnum;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;
import com.bu.softwareengineering.contest.helpers.ServiceSuccessEnum;
import com.bu.softwareengineering.contest.repository.*;
import com.bu.softwareengineering.contest.service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {
    private boolean updateFlag = false;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    ContestTeamRepository contestTeamRepository;


    @Override
    public ServiceResponseHelper saveTeam(Team team) {
        //Optional<Team> savedTeam = teamRepository.findTeamByName(team.getName());
        Map teamServiceResponse = new HashMap();
        //if(!savedTeam.isPresent()){
            if(team.getTeamMembers().size()>3){
                teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team size can't be larger than 3");
                return new ServiceResponseHelper(true,null, teamServiceResponse);
            }
            for(TeamMember teamMember : team.getTeamMembers()){
                Optional<TeamMember> savedTeamMember = teamMemberRepository.findTeamMemberByPerson(teamMember.getPerson());
                if(savedTeamMember.isPresent()){
                    teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team member: "+teamMember.getPerson().getId()+" is already added to another team!");
                    return new ServiceResponseHelper(true,null, teamServiceResponse);
                }
            }
            Team result = teamRepository.save(team);
            Set<TeamMember> teamMembers = new HashSet<>();
            for(TeamMember tm : team.getTeamMembers()){
                TeamMember teamMember = new TeamMember();
                teamMember.setTeam(result);
                teamMember.setPerson(tm.getPerson());
                teamMembers.add(teamMember);
            }
            teamMemberRepository.saveAll(teamMembers);
            teamServiceResponse.put(ServiceSuccessEnum.TEAM_CREATION_SUCCESSFUL,"Team Create Successfully!");
            return new ServiceResponseHelper(false, team, teamServiceResponse);
        /*}else{
            teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team with name: "+ team.getName()+" already exists!");
            return new ServiceResponseHelper(true,null, teamServiceResponse);
        }*/

    }

    @Override
    public ServiceResponseHelper updateTeam(Long contestId, TeamUpdateDto teamUpdateDto) {
        Optional<ContestTeam> contestTeam = contestTeamRepository.findByContestIdAndTeamId(contestId, teamUpdateDto.getId());
        Map teamServiceResponse = new HashMap();
        if(contestTeam.isPresent()){
            if(contestTeam.get().getContest().getIsWriteable()){
                Team savedTeam = contestTeam.get().getTeam();
                BeanUtils.copyProperties(teamUpdateDto,savedTeam,getNullPropertyNames(teamUpdateDto));
                if(savedTeam.getTeamMembers().size()>3){
                    teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team size can't be larger than 3");
                    return new ServiceResponseHelper(true,null, teamServiceResponse);
                }
                for(TeamMember teamMember : savedTeam.getTeamMembers()){
                    Optional<TeamMember> savedTeamMember = teamMemberRepository.findByPersonIdAndTeamIdNot(teamMember.getPerson().getId(),savedTeam.getId());
                    if(savedTeamMember.isPresent()){
                        teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team member: "+teamMember.getPerson().getId()+" is already added to another team!");
                        return new ServiceResponseHelper(true,null, teamServiceResponse);
                    }
                }
               /* Set<TeamMember> teamMembers = new HashSet<>();
                teamMemberRepository.deleteAllByTeam(savedTeam);
                for(TeamMember tm : savedTeam.getTeamMembers()){
                    TeamMember teamMember = new TeamMember();
                    teamMember.setTeam(savedTeam);
                    teamMember.setPerson(tm.getPerson());
                    teamMemberRepository.save(teamMember);
                }*/
                teamRepository.save(savedTeam);
                savedTeam.setTeamMembers(new HashSet<>());
                savedTeam.setContestTeams(new HashSet<>());
                teamServiceResponse.put(ServiceSuccessEnum.TEAM_CREATION_SUCCESSFUL,"Team Updated Successfully!");
                return new ServiceResponseHelper(false, savedTeam, teamServiceResponse);
            }else{
                teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team is not editable!");
                return new ServiceResponseHelper(true, null, teamServiceResponse);
            }

        }
        teamServiceResponse.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"Team is not assigned to any contest yet so can't update!");
        return new ServiceResponseHelper(true, null, teamServiceResponse);
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
