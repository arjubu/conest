package com.bu.softwareengineering.contest.service;

import com.bu.softwareengineering.contest.controller.dto.TeamUpdateDto;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;

import javax.validation.constraints.Size;

@Size
public interface TeamService {
    ServiceResponseHelper saveTeam(Team team);

    ServiceResponseHelper updateTeam(Long contestId, TeamUpdateDto teamUpdateDto);
}
