package com.trip.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trip.hotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
	

}
