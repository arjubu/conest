package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.ContestManager;
import com.bu.softwareengineering.contest.domain.ContestTeam;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.repository.ContestManagerRepository;
import com.bu.softwareengineering.contest.repository.ContestRepository;
import com.bu.softwareengineering.contest.repository.ContestTeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ContestController {

    private final Logger log = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ContestTeamRepository teamRepository;
    @Autowired
    ContestManagerRepository contestManagerRepository;

    @PostMapping("/contest")
    public ResponseEntity<Person> createPerson(@RequestBody Contest contest) {

        Contest result = contestRepository.save(contest);
        for (ContestTeam ct :contest.getContestTeams()){
            ContestTeam team = new ContestTeam();
            team.setContest(result);
            team.setTeam(ct.getTeam());
            teamRepository.save(team);
        }
        ContestManager contestManager = new ContestManager();
        contestManager.setContest(contest);
        for (ContestManager cm : contest.getContestManagers()){
            contestManager.setPerson(cm.getPerson());
        }
        contestManagerRepository.save(contestManager);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
    @GetMapping("/contest")
    public ResponseEntity<?> getAllContests() {
        log.debug("REST request to get all Contests");
        return new ResponseEntity(contestRepository.findAll(), HttpStatus.OK);
        //return contestRepository.findAll();
    }
}
