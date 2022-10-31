package com.bu.softwareengineering.contest.service.impl;

import com.bu.softwareengineering.contest.controller.dto.ContestRegistrationDto;
import com.bu.softwareengineering.contest.domain.*;
import com.bu.softwareengineering.contest.helpers.ServiceErrorsEnum;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;
import com.bu.softwareengineering.contest.helpers.ServiceSuccessEnum;
import com.bu.softwareengineering.contest.repository.*;
import com.bu.softwareengineering.contest.service.ContestService;
import com.bu.softwareengineering.contest.service.dto.ContestRegistrationResponseDto;
import com.bu.softwareengineering.contest.service.dto.TeamPromotionResponseDto;
import com.bu.softwareengineering.contest.util.PersonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContestServiceImpl implements ContestService {
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ContestTeamRepository contestTeamRepository;
    @Autowired
    ContestManagerRepository contestManagerRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonUtil personUtil;

    @Override
    public ServiceResponseHelper contestRegistration(Long contestId, ContestRegistrationDto contestRegistrationDto) {
        Optional<Contest> contest = contestRepository.findById(contestId);
        Map errorMessage = new HashMap();
        Contest existingContest = new Contest();
        if (contest.isPresent()) {
            existingContest = contest.get();
            if (contest.get().getIsWriteable()) {
                if (isContestRegistrable(contest.get())) {
                    //for(Team team : contestRegistrationDto.getTeams()){
                    Optional<Person> existingCoach = personRepository.findByEmail(contestRegistrationDto.getCoach().getEmail());
                    if (existingCoach.isPresent()) {
                        errorMessage.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "Email of coach is already taken!");
                        return new ServiceResponseHelper(true, null, errorMessage);
                    }
                    Optional<ContestTeam> savedContestTeam = contestTeamRepository.findByContestIdAndTeamId(contest.get().getId(), contestRegistrationDto.getTeam().getId());
                    if (savedContestTeam.isPresent()) {
                        errorMessage.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Team id: " + contestRegistrationDto.getTeam().getId() + " is already added in the same contest!");
                        return new ServiceResponseHelper(true, null, errorMessage);
                    } else {
                        //Optional<Team> savedTeam = teamRepository.findById(team.getId());
                        //if(savedTeam.isPresent()){
                        //Set<TeamMember>teamMembers = savedTeam.get().getTeamMembers();
                        List<String> teamMemberEmailAddress = new ArrayList<>();
                        for (Person teamMember : contestRegistrationDto.getMembers()) {
                            if (teamMember.getId() != null) {
                                teamMember = personRepository.findById(teamMember.getId()).get();
                                Optional<TeamMember> savedTeamMember = teamMemberRepository.findByPersonId(teamMember.getId());
                                if (savedTeamMember.isPresent()) {
                                    errorMessage.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "Team member: " + teamMember.getName() + " is already assigned to a team!");
                                    return new ServiceResponseHelper(true, null, errorMessage);
                                }
                                teamMemberEmailAddress.add(teamMember.getEmail());
                            }
                            if (personUtil.getAge(teamMember.getBirthday()) > 24) {
                                errorMessage.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "Team member: " + teamMember.getName() + " age must be less than 24Y!");
                                return new ServiceResponseHelper(true, null, errorMessage);
                            }
                            if (teamMember.getId() == null) {
                                Optional<Person> savedPerson = personRepository.findByEmail(teamMember.getEmail());
                                if (savedPerson.isPresent()) {
                                    errorMessage.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "Member email address: " + teamMember.getEmail() + " is already taken!");
                                    return new ServiceResponseHelper(true, null, errorMessage);
                                }
                                if (teamMemberEmailAddress.contains(teamMember.getEmail())) {
                                    errorMessage.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "New members can not have same email address!");
                                    return new ServiceResponseHelper(true, null, errorMessage);
                                }
                                teamMemberEmailAddress.add(teamMember.getEmail());
                            }

                        }
                            /*}else {
                                errorMessage.put(ServiceErrorsEnum.TEAM_ENTITY_EXCEPTION,"One or more team does not exists!");
                                return new ServiceResponseHelper(true,null,errorMessage);
                            }*/

                    }
                    // }
                } else {
                    errorMessage.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest is full");
                    return new ServiceResponseHelper(true, null, errorMessage);
                }
            } else {
                errorMessage.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest is not writeable!");
                return new ServiceResponseHelper(true, null, errorMessage);
            }

        } else {
            errorMessage.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest does not exists!");
            return new ServiceResponseHelper(true, null, errorMessage);
        }
        Person savedCoach = personRepository.save(contestRegistrationDto.getCoach());
        Team savedTeam = contestRegistrationDto.getTeam();
        savedTeam.setCoach(savedCoach);
        savedTeam = teamRepository.save(savedTeam);
        for (Person member : contestRegistrationDto.getMembers()) {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeam(savedTeam);
            if (member.getId() == null) {
                teamMember.setPerson(personRepository.save(member));
            } else {
                teamMember.setPerson(personRepository.findById(member.getId()).get());
            }

            teamMemberRepository.save(teamMember);
        }

        /*ContestManager contestManager = new ContestManager();
        contestManager.setContest(contestRegistrationDto.getContest());
        contestManager.setPerson(contestRegistrationDto.getManager());
        contestManagerRepository.save(contestManager);*/

        //for(Team team : contestRegistrationDto.getTeams()){
        ContestTeam contestTeam = new ContestTeam();
        contestTeam.setContest(existingContest);
        contestTeam.setTeam(savedTeam);
        contestTeamRepository.save(contestTeam);
        //}
        Map successMessage = new HashMap();
        successMessage.put(ServiceSuccessEnum.CONTEST_REGISTRATION_SUCCESSFUL, "Registration is successful!");


        //contestTeamRepository.findAllById(contest.getContestTeams());
        /*Contest result = contestRepository.save(contest);
        for (ContestTeam ct :contest.getContestTeams()){
            ContestTeam team = new ContestTeam();
            team.setContest(result);
            team.setTeam(ct.getTeam());
            contestTeamRepository.save(team);
        }
        ContestManager contestManager = new ContestManager();
        contestManager.setContest(contest);
        for (ContestManager cm : contest.getContestManagers()){
            contestManager.setPerson(cm.getPerson());
        }
        contestManagerRepository.save(contestManager);*/
        ContestRegistrationResponseDto contestRegistrationResponseDto = new ContestRegistrationResponseDto();
        contestRegistrationResponseDto.setContestName(contest.get().getName());
        contestRegistrationResponseDto.setTeamName(savedTeam.getName());
        return new ServiceResponseHelper(false, contestRegistrationResponseDto, successMessage);
    }

    @Override
    public ServiceResponseHelper saveContest(Contest contest) {
        Contest savedContest = contestRepository.findByName(contest.getName());
        Map contestCreateMap = new HashMap();
        if (savedContest == null) {
            contest = contestRepository.save(contest);
            if (contest.getId() != null) {
                contestCreateMap.put(ServiceSuccessEnum.CONTEST_CREATED_SUCCESSFULLY, "Contest created successfully!");
                return new ServiceResponseHelper(false, contest, contestCreateMap);
            } else {
                contestCreateMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Failed to create contest!");
                return new ServiceResponseHelper(true, null, contestCreateMap);
            }
        } else {
            contestCreateMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest with name: " + contest.getName() + " already exists!");
            return new ServiceResponseHelper(true, null, contestCreateMap);
        }
    }

    @Override
    public List<ContestListProjection> getAllContest() {
        return contestRepository.findAllContest();
    }

    @Override
    public ServiceResponseHelper updateContest(Long contestId, Contest contest) {
        Optional<Contest> existingContest = contestRepository.findById(contestId);
        Map contestUpdateMap = new HashMap();
        if (existingContest.isPresent()) {
            if (existingContest.get().getIsWriteable()) {
                BeanUtils.copyProperties(contest, existingContest.get(), getNullPropertyNames(contest));
                contestRepository.save(existingContest.get());
                contestUpdateMap.put(ServiceSuccessEnum.CONTEST_CREATED_SUCCESSFULLY, "Contest updated successfully!");
                return new ServiceResponseHelper(false, contest, contestUpdateMap);
            }
            contestUpdateMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest with name: " + contest.getName() + " is not editable!");
            return new ServiceResponseHelper(true, null, contestUpdateMap);
        } else {
            contestUpdateMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest with name: " + contest.getName() + " does not exists!");
            return new ServiceResponseHelper(true, null, contestUpdateMap);
        }

    }

    @Override
    public ServiceResponseHelper promoteToSuperContest(Long superContestId, Long teamId) {
        Optional<Contest> existingSuperContest = contestRepository.findByIdAndParentIdNull(superContestId);
        Optional<Team> existingTeam;
        Map contestPromoteMap = new HashMap();
        if (existingSuperContest.isPresent()) {
            if (existingSuperContest.get().getIsWriteable()) {
                existingTeam = teamRepository.findById(teamId);
                if (existingTeam.isPresent()) {
                    Optional<ContestTeam> savedContestTeam = contestTeamRepository.findByContestIdAndTeamId(superContestId, teamId);
                    if (savedContestTeam.isPresent()) {
                        contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Team id: " + teamId + " is already added in the same contest!");
                        return new ServiceResponseHelper(true, null, contestPromoteMap);
                    }
                    if (!isContestRegistrable(existingSuperContest.get())) {
                        contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest is full");
                        return new ServiceResponseHelper(true, null, contestPromoteMap);
                    }
                    if (existingTeam.get().getRank() > 5) {
                        contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Team is not eligible for super contest");
                        return new ServiceResponseHelper(true, null, contestPromoteMap);
                    }
                    Set<TeamMember> teamMembers = existingTeam.get().getTeamMembers();
                    for (TeamMember teamMember : teamMembers) {
                        if (personUtil.getAge(teamMember.getPerson().getBirthday()) > 24) {
                            contestPromoteMap.put(ServiceErrorsEnum.PERSON_ENTITY_EXCEPTION, "Team member id: " + teamMember.getPerson().getId() + " age must be less than 24Y!");
                            return new ServiceResponseHelper(true, null, contestPromoteMap);
                        }
                    }

                    ContestTeam contestTeam = new ContestTeam();
                    contestTeam.setTeam(existingTeam.get());
                    contestTeam.setContest(existingSuperContest.get());
                    contestTeamRepository.save(contestTeam);
                    Team promotedTeam = new Team();
                    BeanUtils.copyProperties(existingTeam.get(), promotedTeam);
                    promotedTeam.setIsCloneOf(existingTeam.get().getId());
                    promotedTeam.setId(null);
                    promotedTeam.setTeamMembers(null);
                    promotedTeam.setContestTeams(null);
                    Team clonedTeam = teamRepository.save(promotedTeam);
                    TeamPromotionResponseDto teamPromotionResponseDto = new TeamPromotionResponseDto();
                    teamPromotionResponseDto.setName(promotedTeam.getName());
                    teamPromotionResponseDto.setRank(promotedTeam.getRank());
                    teamPromotionResponseDto.setState(promotedTeam.getState());
                    teamPromotionResponseDto.setIsCloneOf(clonedTeam.getIsCloneOf());
                    //teamPromotionDto.setPreviousContest(existingTeam.get().get);
                    teamPromotionResponseDto.setPromotedContest(existingSuperContest.get().getName());
                    contestPromoteMap.put(ServiceSuccessEnum.CONTEST_CREATED_SUCCESSFULLY, "Team promoted to super contest");
                    return new ServiceResponseHelper(false, teamPromotionResponseDto, contestPromoteMap);

                } else {
                    contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Team does not exits!");
                    return new ServiceResponseHelper(true, null, contestPromoteMap);
                }
            } else {
                contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Super contest is not writeable!");
                return new ServiceResponseHelper(true, null, contestPromoteMap);
            }

        } else {
            contestPromoteMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Super contest does not exits!");
            return new ServiceResponseHelper(true, null, contestPromoteMap);
        }

    }

    @Override
    public ServiceResponseHelper setContestEditable(Long contestId) {
        Optional<Contest> existingContest = contestRepository.findById(contestId);
        Map contestEditMap = new HashMap();
        if (existingContest.isPresent()) {
            existingContest.get().setIsWriteable(true);
            contestRepository.save(existingContest.get());
            existingContest.get().setContests(null);
            existingContest.get().setContestManagers(null);
            existingContest.get().setContestTeams(null);
            contestEditMap.put(ServiceSuccessEnum.CONTEST_CREATED_SUCCESSFULLY, "Contest writable status changed!");
            return new ServiceResponseHelper(false, existingContest.get(), contestEditMap);
        }
        contestEditMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest does not exits!");
        return new ServiceResponseHelper(true, null, contestEditMap);
    }

    @Override
    public ServiceResponseHelper setContestReadable(Long contestId) {
        Optional<Contest> existingContest = contestRepository.findById(contestId);
        Map contestEditMap = new HashMap();
        if (existingContest.isPresent()) {
            existingContest.get().setIsWriteable(false);
            contestRepository.save(existingContest.get());
            existingContest.get().setContests(null);
            existingContest.get().setContestManagers(null);
            existingContest.get().setContestTeams(null);
            contestEditMap.put(ServiceSuccessEnum.CONTEST_CREATED_SUCCESSFULLY, "Contest writable status changed!");
            return new ServiceResponseHelper(false, existingContest.get(), contestEditMap);
        }
        contestEditMap.put(ServiceErrorsEnum.CONTEST_ENTITY_EXCEPTION, "Contest does not exits!");
        return new ServiceResponseHelper(true, null, contestEditMap);
    }

    private boolean isContestRegistrable(Contest contest) {
        if (contest.getContestTeams().size() < contest.getCapacity())
            return true;
        return false;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
