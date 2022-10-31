package com.bu.softwareengineering.contest.repository;

import java.util.Date;

public interface ContestListProjection {
    Long getId();
    Integer getCapacity();
    String getDate();
    String getName();
    Boolean getRegistrationAllowed();
    Date getRegistrationFrom();
    Date getRegistrationTo();

}
