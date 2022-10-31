package com.bu.softwareengineering.contest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contest.
 */
@Entity
@Table(name = "contest")
public class Contest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity")
    @NotNull(message = "Capacity can not be empty")
    private Integer capacity;

    @Column(name = "date")
    @NotNull(message = "Please provide a valid date")
    private String date;

    @Column(name = "name")
    @NotNull(message = "Please provide a name ")
    private String name;

    @Column(name= "isWriteable")
    //@NotNull(message = "Need this flag or null exception will occur!")
    private Boolean isWriteable = false;

    /*@Column(name = "isReadable")
    private Boolean isReadable = true;*/

    @Column(name = "registration_allowed")
    private Boolean registrationAllowed;

    @Column(name = "registration_from")
    private Date registrationFrom;

    @Column(name = "registration_to")
    private Date registrationTo;

    @OneToMany(mappedBy = "parent")
    private Set<Contest> contests = new HashSet<>();

    @OneToMany(mappedBy = "contest")
    /*@Size(min = 1,
            max = 3,
            message = "Contest team size should be between one and three!")*/
    private Set<ContestTeam> contestTeams = new HashSet<>();

    @OneToMany(mappedBy = "contest")
    /*@Size(min = 1, message = "Contest needs at least one manager")*/
    private Set<ContestManager> contestManagers  = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "contests", allowSetters = true)
    private Contest parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistrationAllowed() {
        return registrationAllowed;
    }

    public void setRegistrationAllowed(Boolean registrationAllowed) {
        this.registrationAllowed = registrationAllowed;
    }

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = registrationFrom;
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = registrationTo;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    public Set<ContestTeam> getContestTeams() {
        return contestTeams;
    }

    public void setContestTeams(Set<ContestTeam> contestTeams) {
        this.contestTeams = contestTeams;
    }

    public Set<ContestManager> getContestManagers() {
        return contestManagers;
    }

    public void setContestManagers(Set<ContestManager> contestManagers) {
        this.contestManagers = contestManagers;
    }

    public Contest getParent() {
        return parent;
    }

    public void setParent(Contest parent) {
        this.parent = parent;
    }

    public Boolean getIsWriteable() {
        return isWriteable;
    }

    public void setIsWriteable(Boolean isWriteable) {
        this.isWriteable = isWriteable;
    }

    /*public Boolean getWriteable() {
        return isWriteable;
    }

    public void setWriteable(Boolean writeable) {
        isWriteable = writeable;
    }*/

   /* public Boolean getReadable() {
        return isReadable;
    }

    public void setReadable(Boolean readable) {
        isReadable = readable;
    }*/
}
