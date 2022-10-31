/*
package com.bu.softwareengineering.contest.controller.dto;

import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ContestRegistrationDto {
    @NotNull(message = "Contest can not be null!")
    private Contest contest;

    @NotNull(message = "Manager can not be null!")
    private Person manager;

    @Size(min = 1, max = 3, message = "Team size should be between one and three")
    private List<Team> teams;

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
*/
