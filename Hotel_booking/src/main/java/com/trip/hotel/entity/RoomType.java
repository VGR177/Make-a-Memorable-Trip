package com.trip.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class RoomType {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	 @Enumerated(EnumType.STRING)
	 private RoomTypeEnum type;

    @NotNull
    private int availableRooms;
    
    private int capacity;

    @NotNull
    private double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id",nullable=false)
    private Hotel hotel;
}
