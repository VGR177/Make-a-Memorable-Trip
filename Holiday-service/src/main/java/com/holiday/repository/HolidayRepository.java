package com.holiday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.holiday.model.Holiday;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> 
{
	
    Optional<Holiday> findByPackageId(String packageId);
    void deleteByPackageId(String packageId);
	List<Holiday> findByAvailabilityGreaterThan(Integer availability);

}
