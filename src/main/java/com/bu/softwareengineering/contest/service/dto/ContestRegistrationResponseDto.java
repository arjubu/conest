package com.bu.softwareengineering.contest.service.dto;

public class ContestRegistrationResponseDto {
    private String contestName;
    private String teamName;

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
