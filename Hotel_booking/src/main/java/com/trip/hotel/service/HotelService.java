package com.trip.hotel.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.trip.hotel.entity.Booking;
import com.trip.hotel.entity.Hotel;
import com.trip.hotel.entity.RoomType;
import com.trip.hotel.repository.BookingRepository;
import com.trip.hotel.repository.HotelRepository;
import com.trip.hotel.repository.RoomTypeRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
    private RoomTypeRepository roomTypeRepository;
	@Autowired
    private BookingRepository bookingRepository;
	
	// Hotel Management
    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Optional<Hotel> updateHotel(Long id, Hotel updatedHotel) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setName(updatedHotel.getName());
            hotel.setLocation(updatedHotel.getLocation());
            return hotelRepository.save(hotel);
        });
    }

    public boolean deleteHotel(Long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }
	
 // Room Type Management
    public RoomType addRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    public Optional<RoomType> updateRoomType(Long id, RoomType updatedRoomType) {
        return roomTypeRepository.findById(id).map(roomType -> {
            roomType.setType(updatedRoomType.getType());
            roomType.setCapacity(updatedRoomType.getCapacity());
            roomType.setPrice(updatedRoomType.getPrice());
            roomType.setAvailableRooms(updatedRoomType.getAvailableRooms());
            return roomTypeRepository.save(roomType);
        });
    }
    
 // Booking Management
    public String createBooking(Booking booking) {
        // Fetch the RoomType object from the database
        Long roomTypeId = booking.getRoomType().getId();
        Optional<RoomType> optionalRoomType = roomTypeRepository.findById(roomTypeId);
        
        // If RoomType exists and has available rooms
        if (optionalRoomType.isPresent()) {
            RoomType roomType = optionalRoomType.get();
            
            // Check if there are rooms available
            if (roomType.getAvailableRooms() > 0) {
                roomType.setAvailableRooms(roomType.getAvailableRooms() - 1);  // Decrease available rooms
                roomTypeRepository.save(roomType);  // Save the updated RoomType
                
                bookingRepository.save(booking);  // Save the new booking
                return "Booking created successfully.";
            } else {
                return "No rooms available.";  // Return when no rooms are available
            }
        } else {
            return "Room type not found.";  // Return if the RoomType does not exist
        }
    }

    public String cancelBooking(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            RoomType roomType = booking.getRoomType();
            
            // Increment available rooms when booking is canceled
            roomType.setAvailableRooms(roomType.getAvailableRooms() + 1);
            roomTypeRepository.save(roomType);  // Save the updated RoomType
            
            bookingRepository.deleteById(id);  // Delete the canceled booking
            return "Booking canceled successfully.";
        } else {
            return "Booking not found.";  // Return if the booking does not exist
        }
    }

	
}
	