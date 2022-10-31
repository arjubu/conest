package com.bu.softwareengineering.contest.repository;


import com.bu.softwareengineering.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Contest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    public Contest findByName(String name);
    public List<Contest> findAllByParent(Contest contest);
    Optional<Contest> findByIdAndParentIdNull(Long contestId);

    @Query(value = "SELECT c.id as id,  c.capacity as capacity, c.date as date, c.name as name , c.isWriteable as isWriteable," +
            " c.registrationAllowed as registrationAllowed," +
            "c.registrationFrom as registrationFrom, c.registrationTo as registrationTo  FROM Contest c")
    List<ContestListProjection> findAllContest();
}
