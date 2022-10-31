package com.bu.softwareengineering.contest.controller.dto;

import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.domain.Team;
import com.bu.softwareengineering.contest.domain.enumeration.State;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class TeamUpdateDto {
    /*@NotNull(message = "Contest id can not be null!")
    private Contest contest;*/
    @NotNull(message = "Team id is required!")
    private Long id;
    @NotNull(message = "Team name is required!")
    private String name;
    private Integer rank = null;
    private Long isCloneOf;
    private State state = State.PENDING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getIsCloneOf() {
        return isCloneOf;
    }

    public void setIsCloneOf(Long isCloneOf) {
        this.isCloneOf = isCloneOf;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
