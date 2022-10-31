package com.bu.softwareengineering.contest.repository;

import com.bu.softwareengineering.contest.domain.ContestTeam;
import com.bu.softwareengineering.contest.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestTeamRepository extends JpaRepository<ContestTeam,Long> {

    Optional<ContestTeam> findByContestIdAndTeamId(Long contestId, Long teamId);
    List<ContestTeam> findAllByTeam(Team team);
}
