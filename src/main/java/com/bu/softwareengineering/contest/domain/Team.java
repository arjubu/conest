package com.bu.softwareengineering.contest.domain;


import com.bu.softwareengineering.contest.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Team name is required!")
    private String name;

    @Column(name = "jhi_rank")
    private Integer rank;

    @Column(name = "is_clone_of")
    private Long isCloneOf;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state = State.PENDING;

    @OneToMany(mappedBy = "team")
    private Set<TeamMember> teamMembers = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<ContestTeam> contestTeams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "teams", allowSetters = true)
    private Person coach;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Person getCoach() {
        return coach;
    }

    public void setCoach(Person coach) {
        this.coach = coach;
    }

    public Set<ContestTeam> getContestTeams() {
        return contestTeams;
    }

    public void setContestTeams(Set<ContestTeam> contestTeams) {
        this.contestTeams = contestTeams;
    }

    public Long getIsCloneOf() {
        return isCloneOf;
    }

    public void setIsCloneOf(Long isCloneOf) {
        this.isCloneOf = isCloneOf;
    }
}
