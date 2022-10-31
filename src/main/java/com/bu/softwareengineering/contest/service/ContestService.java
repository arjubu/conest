package com.bu.softwareengineering.contest.service;

import com.bu.softwareengineering.contest.controller.dto.ContestRegistrationDto;
import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.helpers.ServiceErrorsEnum;
import com.bu.softwareengineering.contest.helpers.ServiceResponseHelper;
import com.bu.softwareengineering.contest.repository.ContestListProjection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContestService {
    ServiceResponseHelper contestRegistration(Long contestId, ContestRegistrationDto contestRegistrationDto);

    ServiceResponseHelper saveContest(Contest contest);
    List<ContestListProjection> getAllContest();

    ServiceResponseHelper updateContest(Long contestId, Contest contest);
    ServiceResponseHelper promoteToSuperContest(Long superContestId, Long teamId);

    ServiceResponseHelper setContestEditable(Long contestId);
    ServiceResponseHelper setContestReadable(Long contestId);

}
