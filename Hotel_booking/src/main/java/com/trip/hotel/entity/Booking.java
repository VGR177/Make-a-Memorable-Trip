package com.trip.hotel.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Booking {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String customerName;

//	    @ManyToOne
//	    @JoinColumn(name = "hotel_id")
//	    private Hotel hotel;

	    @ManyToOne
	    @JoinColumn(name = "room_id")
	    private RoomType roomType;

	    private LocalDate checkInDate;
	    private LocalDate checkOutDate;

//	    private double totalPrice;
}
