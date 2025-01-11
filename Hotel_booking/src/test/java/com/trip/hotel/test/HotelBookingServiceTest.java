package com.trip.hotel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.trip.hotel.entity.Booking;
import com.trip.hotel.entity.Hotel;
import com.trip.hotel.entity.RoomType;
import com.trip.hotel.entity.RoomTypeEnum;
import com.trip.hotel.repository.BookingRepository;
import com.trip.hotel.repository.HotelRepository;
import com.trip.hotel.repository.RoomTypeRepository;
import com.trip.hotel.service.HotelService;

public class HotelBookingServiceTest {

	    @Mock
	    private HotelRepository hotelRepository;

	    @Mock
	    private RoomTypeRepository roomTypeRepository;

	    @Mock
	    private BookingRepository bookingRepository;

	    @InjectMocks
	    private HotelService hotelBookingService;

	    private Hotel hotel;
	    private RoomType roomType;
	    private Booking booking;
	    
	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);

	        hotel = new Hotel();
	        hotel.setId(1L);
	        hotel.setName("Test Hotel");
	        hotel.setLocation("Test Location");

	        roomType = new RoomType();
	        roomType.setId(1L);
	        roomType.setType(RoomTypeEnum.SINGLE);
	        roomType.setCapacity(2);
	        roomType.setPrice(100);
	        roomType.setAvailableRooms(5);
	        roomType.setHotel(hotel);

	        booking = new Booking();
	        booking.setId(1L);
	        booking.setCustomerName("John Doe");
	        booking.setCheckInDate(LocalDate.of(2025, 1, 10)); 
	        booking.setCheckOutDate(LocalDate.of(2025, 1, 15));
	        booking.setRoomType(roomType);
	    }
	    

	    // Hotel Management Tests

	    @Test
	    void testAddHotel() {
	        when(hotelRepository.save(hotel)).thenReturn(hotel);

	        Hotel savedHotel = hotelBookingService.addHotel(hotel);
	        assertNotNull(savedHotel);
	        assertEquals("Test Hotel", savedHotel.getName());
	        verify(hotelRepository, times(1)).save(hotel);
	    }

	    @Test
	    void testUpdateHotelSuccess() {
	        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
	        when(hotelRepository.save(hotel)).thenReturn(hotel);

	        Optional<Hotel> updatedHotel = hotelBookingService.updateHotel(1L, hotel);
	        assertTrue(updatedHotel.isPresent());
	        assertEquals("Test Hotel", updatedHotel.get().getName());
	        verify(hotelRepository, times(1)).findById(1L);
	        verify(hotelRepository, times(1)).save(hotel);
	    }

	    @Test
	    void testUpdateHotelNotFound() {
	        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

	        Optional<Hotel> updatedHotel = hotelBookingService.updateHotel(1L, hotel);
	        assertFalse(updatedHotel.isPresent());
	        verify(hotelRepository, times(1)).findById(1L);
	        verify(hotelRepository, never()).save(hotel);
	    }

	    @Test
	    void testDeleteHotelSuccess() {
	        when(hotelRepository.existsById(1L)).thenReturn(true);

	        boolean isDeleted = hotelBookingService.deleteHotel(1L);
	        assertTrue(isDeleted);
	        verify(hotelRepository, times(1)).existsById(1L);
	        verify(hotelRepository, times(1)).deleteById(1L);
	    }

	    @Test
	    void testDeleteHotelNotFound() {
	        when(hotelRepository.existsById(1L)).thenReturn(false);

	        boolean isDeleted = hotelBookingService.deleteHotel(1L);
	        assertFalse(isDeleted);
	        verify(hotelRepository, times(1)).existsById(1L);
	        verify(hotelRepository, never()).deleteById(1L);
	    }
	    
	 // Room Type Management Tests

	    @Test
	    void testAddRoomType() {
	        when(roomTypeRepository.save(roomType)).thenReturn(roomType);

	        RoomType savedRoomType = hotelBookingService.addRoomType(roomType);
	        assertNotNull(savedRoomType);
	        assertEquals(RoomTypeEnum.SINGLE, savedRoomType.getType());
	        verify(roomTypeRepository, times(1)).save(roomType);
	    }

	    @Test
	    void testUpdateRoomTypeSuccess() {
	        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));
	        when(roomTypeRepository.save(roomType)).thenReturn(roomType);

	        Optional<RoomType> updatedRoomType = hotelBookingService.updateRoomType(1L, roomType);
	        assertTrue(updatedRoomType.isPresent());
	        assertEquals(RoomTypeEnum.SINGLE, updatedRoomType.get().getType());
	        verify(roomTypeRepository, times(1)).findById(1L);
	        verify(roomTypeRepository, times(1)).save(roomType);
	    }

	    @Test
	    void testUpdateRoomTypeNotFound() {
	        when(roomTypeRepository.findById(1L)).thenReturn(Optional.empty());

	        Optional<RoomType> updatedRoomType = hotelBookingService.updateRoomType(1L, roomType);
	        assertFalse(updatedRoomType.isPresent());
	        verify(roomTypeRepository, times(1)).findById(1L);
	        verify(roomTypeRepository, never()).save(roomType);
	    }

	    // Booking Management Tests

	    @Test
	    void testCreateBookingSuccess() {
	        when(roomTypeRepository.save(roomType)).thenReturn(roomType);
	        when(bookingRepository.save(booking)).thenReturn(booking);

	        String result = hotelBookingService.createBooking(booking);
	        assertEquals("Booking created successfully.", result);
	        verify(roomTypeRepository, times(1)).save(roomType);
	        verify(bookingRepository, times(1)).save(booking);
	    }

	    @Test
	    void testCreateBookingFailure() {
	        roomType.setAvailableRooms(0); // No rooms available
	        String result = hotelBookingService.createBooking(booking);
	        assertEquals("No rooms available.", result);
	        verify(roomTypeRepository, never()).save(any(RoomType.class));
	        verify(bookingRepository, never()).save(any(Booking.class));
	    }

	    @Test
	    void testCancelBookingSuccess() {
	        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

	        String result = hotelBookingService.cancelBooking(1L);
	        assertEquals("Booking canceled successfully.", result);
	        verify(roomTypeRepository, times(1)).save(roomType);
	        verify(bookingRepository, times(1)).deleteById(1L);
	    }

	    @Test
	    void testCancelBookingNotFound() {
	        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

	        String result = hotelBookingService.cancelBooking(1L);
	        assertEquals("Booking not found.", result);
	        verify(roomTypeRepository, never()).save(any(RoomType.class));
	        verify(bookingRepository, never()).deleteById(anyLong());
	    }
}
