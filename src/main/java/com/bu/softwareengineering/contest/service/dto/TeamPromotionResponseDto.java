package com.bu.softwareengineering.contest.service.dto;

import com.bu.softwareengineering.contest.domain.enumeration.State;

public class TeamPromotionResponseDto {

    private String name;
    private Integer rank;
    private Long isCloneOf;
    private State state;
    private String promotedContest;
    private String previousContest;

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

    public String getPromotedContest() {
        return promotedContest;
    }

    public void setPromotedContest(String promotedContest) {
        this.promotedContest = promotedContest;
    }

    public String getPreviousContest() {
        return previousContest;
    }

    public void setPreviousContest(String previousContest) {
        this.previousContest = previousContest;
    }
}
