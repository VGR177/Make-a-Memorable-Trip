package com.trip.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.hotel.entity.Booking;
import com.trip.hotel.entity.Hotel;
import com.trip.hotel.entity.RoomType;
import com.trip.hotel.service.HotelService;

@RestController
@RequestMapping("/api")
public class HotelController {

	@Autowired
	private HotelService hotelBookingService;
	
	// Hotel Management
    @PostMapping("/hotels")
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelBookingService.addHotel(hotel);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel updatedHotel) {
        return hotelBookingService.updateHotel(id, updatedHotel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        if (hotelBookingService.deleteHotel(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
 // Room Type Management
    @PostMapping("/roomtypes")
    public RoomType addRoomType(@RequestBody RoomType roomType) {
        return hotelBookingService.addRoomType(roomType);
    }

    @PutMapping("/roomtypes/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable Long id, @RequestBody RoomType updatedRoomType) {
        return hotelBookingService.updateRoomType(id, updatedRoomType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
 // Booking Management
    @PostMapping("/bookings")
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        String result = hotelBookingService.createBooking(booking);
        if (result.equals("Booking created successfully.")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        String result = hotelBookingService.cancelBooking(id);
        if (result.equals("Booking canceled successfully.")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
	
}	
