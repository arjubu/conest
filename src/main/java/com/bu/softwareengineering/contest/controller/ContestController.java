package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.controller.dto.ContestRegistrationDto;
import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;
import com.bu.softwareengineering.contest.service.ContestService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Transactional
@CrossOrigin(origins = "http://localhost:3200")
public class ContestController {

    private final Logger log = LoggerFactory.getLogger(ContestController.class);
    ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    @Autowired
    ContestService contestService;

    @PostMapping("/contestsRegistration")
    public ResponseEntity<?> contestRegistration(@RequestParam(name = "contestId") Long contestId, @RequestBody @Validated ContestRegistrationDto contestRegistrationDto) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.contestRegistration(contestId, contestRegistrationDto);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/contest")
    public ResponseEntity<?> createContest(@RequestBody Contest contest) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.saveContest(contest);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/contest")
    public ResponseEntity<?> getAllContests() throws JsonProcessingException {
        log.debug("REST request to get all Contests");
        return new ResponseEntity(contestService.getAllContest(), HttpStatus.OK);
    }

    @PutMapping("/contest")
    public ResponseEntity<?> updateContest(@RequestParam(name = "contestId") Long contestId, @RequestBody @Validated Contest contest) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.updateContest(contestId, contest);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/promote")
    public ResponseEntity<?> promoteToSuperContest(@RequestParam(name = "superContestId") Long contestId, @RequestParam(name = "teamId") Long teamId) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.promoteToSuperContest(contestId,teamId);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/setEditable")
    public ResponseEntity<?> changeContestEditable(@RequestParam(name = "contestId") Long contestId) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.setContestEditable(contestId);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/setReadable")
    public ResponseEntity<?> changeContestReadable(@RequestParam(name = "contestId") Long contestId) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = contestService.setContestReadable(contestId);
        if(!serviceResponseHelper.getHasError()){
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.CREATED);
        }else{
            return new ResponseEntity(objectMapper.writeValueAsString(serviceResponseHelper), HttpStatus.BAD_REQUEST);
        }
    }
}
