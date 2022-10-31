package com.bu.softwareengineering.contest.controller.dto;

import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.domain.Team;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ContestRegistrationDto {


    @NotNull(message = "Manager can not be null!")
    private Person coach;

    @Size(min = 1, max = 3, message = "Team size should be between one and three")
    private List<Person> members;

    @NotNull(message = "Team can not be null")
    private  Team Team;

    public Person getCoach() {
        return coach;
    }

    public void setCoach(Person coach) {
        this.coach = coach;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public com.bu.softwareengineering.contest.domain.Team getTeam() {
        return Team;
    }

    public void setTeam(com.bu.softwareengineering.contest.domain.Team team) {
        Team = team;
    }
}
