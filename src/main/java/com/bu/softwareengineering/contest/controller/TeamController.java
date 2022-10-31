package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.controller.dto.TeamUpdateDto;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.TeamMember;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;
import com.bu.softwareengineering.contest.repository.TeamMemberRepository;
import com.bu.softwareengineering.contest.repository.TeamRepository;
import com.bu.softwareengineering.contest.service.TeamService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.*;

@RestController
@Transactional
public class TeamController {

    ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    @Autowired
    TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<?> createTeam(@RequestBody Team team) throws  JsonProcessingException {
        return new ResponseEntity(objectMapper.writeValueAsString(teamService.saveTeam(team)), HttpStatus.CREATED);
    }

    @PutMapping("/teams")
    public ResponseEntity<?> updateTeam(@RequestParam(name = "contestId") Long contestId, @RequestBody @Validated TeamUpdateDto teamUpdateDto) throws  JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = teamService.updateTeam(contestId, teamUpdateDto);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }
        return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
    }
}
