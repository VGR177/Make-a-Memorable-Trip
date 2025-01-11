package com.holiday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.holiday.model.BookingStatus;
import com.holiday.model.HolidayBooking;


@Repository
public interface HolidayBookingRepository   extends JpaRepository<HolidayBooking, Long>{

	
	Optional<HolidayBooking> findById(Long bookId);
    List<HolidayBooking> findByHolidayIdAndStatus(Long holidayId, BookingStatus status);


}
