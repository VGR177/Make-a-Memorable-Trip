package com.trip.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.hotel.entity.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{


}
