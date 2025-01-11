package com.holiday.service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holiday.exception.EntityNotFoundException;
import com.holiday.exception.InvalidInputException;
import com.holiday.model.BookingStatus;
import com.holiday.model.Holiday;
import com.holiday.model.HolidayBooking;
import com.holiday.repository.HolidayBookingRepository;
import com.holiday.repository.HolidayRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HolidayService {
	
	@Autowired
    private HolidayRepository holidayRepository;
    
    @Autowired
    private HolidayBookingRepository bookingRepository;

    public List<Holiday> getAllPackages() {
    	return holidayRepository.findByAvailabilityGreaterThan(0);
    }
    
    
    public Holiday getHolidayByPackageId(String packageId) {
    	return holidayRepository.findByPackageId(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Holiday package with ID " + packageId + " does not exist."));
    }
    
    
    public HolidayBooking getBookingByBookId(Long bookId) {
    	return bookingRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookId + " does not exist."));
    }
    

    public String bookPackage(String packageId, String userEmail) {
    	
    	//HolidayBooking holidayBooking = new HolidayBooking();
        // Validate inputs
        if (packageId == null || packageId.isEmpty()) {
            throw new InvalidInputException("Package ID cannot be null or empty");
        }
        if (userEmail == null || userEmail.isEmpty()) {
            throw new InvalidInputException("User email is required");
        }

        // Find the holiday package by its ID
        Holiday holiday = holidayRepository.findByPackageId(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Holiday package not found with packageId: " + packageId));

        // Check if the holiday package has availability
        if (holiday.getAvailability() > 0) {
            // Decrease the availability by 1
            holiday.setAvailability(holiday.getAvailability() - 1);
            // Save the updated holiday package with the new availability
            holidayRepository.save(holiday);

            // Create and save a booking for the holiday package
            HolidayBooking booking = new HolidayBooking();
            booking.setHoliday(holiday);
            booking.setUserEmail(userEmail);
            booking.setBookingDate(LocalDate.now());
            bookingRepository.save(booking);

            // Return success message
            return "Holiday package booked successfully! \nPackage ID: " + holiday.getPackageId() +
                   ", \nPackage Name: " + holiday.getName()+ "\nUser ID: " +booking.getBookId();
        } else {
            // If the package is not available, throw an exception
            throw new InvalidInputException("Package not available");
        }
    }
    
    public Holiday addPackage(Holiday holiday) {
    	
    	
    	
    	if (holiday.getPrice() <= 0) {
            throw new InvalidInputException("Price must be greater than zero.");
        }

        // Ensure that the start date is before the end date
        if (holiday.getStartDate().isAfter(holiday.getEndDate())) {
            throw new InvalidInputException("End date must be after start date.");
        }

        return holidayRepository.save(holiday);
    }

    public Holiday updatePackage(String packageId, Holiday updatedPackage) {
        if (packageId == null || packageId.isEmpty()) {
            throw new InvalidInputException("Package ID is required for updating");
        }

        Holiday holiday = holidayRepository.findByPackageId(packageId)
                .orElseThrow(() -> new EntityNotFoundException("Holiday package not found with packageId: " + packageId));

        holiday.setName(updatedPackage.getName());
        holiday.setIncludes(updatedPackage.getIncludes());
        holiday.setPrice(updatedPackage.getPrice());
        holiday.setAvailability(updatedPackage.getAvailability());
        holiday.setDestination(updatedPackage.getDestination());
        holiday.setStartDate(updatedPackage.getStartDate());
        holiday.setEndDate(updatedPackage.getEndDate());
        holiday.setDescription(updatedPackage.getDescription());

        return holidayRepository.save(holiday);
    }

    
    
    
    public String deleteHoliday(Long id) throws InvalidInputException {
    	
    	
    	Optional<Holiday> holiday = holidayRepository.findById(id);

        if (holiday.isEmpty()) {
            // If the holiday package is not found, throw an exception with a message
            throw new InvalidInputException("Holiday package with ID " + id + " does not exist.");
        }
        // Check if there are any active bookings for this package
        List<HolidayBooking> activeBookings = bookingRepository.findByHolidayIdAndStatus(id, BookingStatus.ACTIVE);

        if (!activeBookings.isEmpty()) {
            // Throw exception if there are active bookings
            throw new InvalidInputException("Cannot delete holiday package. Active bookings exist.");
        }

        // Proceed with deletion if no active bookings
        holidayRepository.deleteById(id);
        
        return "Holiday package deleted successfully.";
    }


}
