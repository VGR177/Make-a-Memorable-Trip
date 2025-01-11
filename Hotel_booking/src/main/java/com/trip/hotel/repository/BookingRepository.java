package com.trip.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.hotel.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
