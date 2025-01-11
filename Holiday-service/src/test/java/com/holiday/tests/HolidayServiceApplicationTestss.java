package com.holiday.tests;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.holiday.exception.InvalidInputException;
import com.holiday.model.Holiday;
import com.holiday.repository.HolidayRepository;
import com.holiday.service.HolidayService;

public class HolidayServiceApplicationTestss {

	   @Mock
	    private HolidayRepository holidayRepository;

	    @InjectMocks
	    private HolidayService holidayService;

	    private Holiday validHoliday;
	    private Holiday holidayWithNegativePrice;
	    private Holiday holidayWithInvalidDate;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);

	        // Valid holiday
	        validHoliday = new Holiday();
	        validHoliday.setPackageId("PKG001");
	        validHoliday.setName("Summer Vacation");
	        validHoliday.setPrice(500.0);
	        validHoliday.setAvailability(10);
	        validHoliday.setDestination("Hawaii");
	        validHoliday.setStartDate(LocalDate.of(2025, 6, 1));
	        validHoliday.setEndDate(LocalDate.of(2025, 6, 15));

	        // Holiday with negative price
	        holidayWithNegativePrice = new Holiday();
	        holidayWithNegativePrice.setPackageId("PKG002");
	        holidayWithNegativePrice.setName("Winter Wonderland");
	        holidayWithNegativePrice.setPrice(-100.0); // Invalid price (negative)
	        holidayWithNegativePrice.setAvailability(5);
	        holidayWithNegativePrice.setDestination("Alaska");
	        holidayWithNegativePrice.setStartDate(LocalDate.of(2025, 12, 1));
	        holidayWithNegativePrice.setEndDate(LocalDate.of(2025, 12, 15));

	        // Holiday with invalid date (end date earlier than start date)
	        holidayWithInvalidDate = new Holiday();
	        holidayWithInvalidDate.setPackageId("PKG003");
	        holidayWithInvalidDate.setName("Autumn Break");
	        holidayWithInvalidDate.setPrice(400.0);
	        holidayWithInvalidDate.setAvailability(5);
	        holidayWithInvalidDate.setDestination("France");
	        holidayWithInvalidDate.setStartDate(LocalDate.of(2025, 10, 10));
	        holidayWithInvalidDate.setEndDate(LocalDate.of(2025, 10, 5)); // Invalid: end date before start date
	    }

	    @Test
	    public void testAddPackage_Success() {
	        // Mock the save method of holidayRepository
	        when(holidayRepository.save(validHoliday)).thenReturn(validHoliday);

	        // Call the service method to add the holiday
	        Holiday savedHoliday = holidayService.addPackage(validHoliday);

	        // Verify if the repository save method was called
	        verify(holidayRepository, times(1)).save(validHoliday);

	        // Assert that the saved holiday is not null and has the correct packageId
	        assertNotNull(savedHoliday);
	        assertEquals("PKG001", savedHoliday.getPackageId());
	    }

	    @Test
	    public void testAddPackage_Failure_NegativePrice() {
	        // Mock the repository behavior
	        when(holidayRepository.save(holidayWithNegativePrice)).thenThrow(new InvalidInputException("Price must be greater than zero."));

	        // Assert that an InvalidInputException is thrown when trying to add a holiday with a negative price
	        assertThrows(InvalidInputException.class, () -> {
	            holidayService.addPackage(holidayWithNegativePrice);
	        });
	    }

	    @Test
	    public void testAddPackage_Failure_InvalidDate() {
	        // Ensure that start date is before end date
	        assertThrows(InvalidInputException.class, () -> {
	            holidayService.addPackage(holidayWithInvalidDate);
	        });
	    }

	    
	    
	    @Test
	    public void testAddPackage_Failure_LeapYearValidation() 
	    {
	        // Holiday during leap year (Feb 29)
	        Holiday holidayDuringLeapYear = new Holiday();
	        holidayDuringLeapYear.setPackageId("PKG004");
	        holidayDuringLeapYear.setName("Leap Year Holiday");
	        holidayDuringLeapYear.setPrice(700.0);
	        holidayDuringLeapYear.setAvailability(5);
	        holidayDuringLeapYear.setDestination("Venice");
	        holidayDuringLeapYear.setStartDate(LocalDate.of(2024, 2, 29)); // Valid leap year date
	        holidayDuringLeapYear.setEndDate(LocalDate.of(2024, 3, 5)); // Valid end date

	        when(holidayRepository.save(holidayDuringLeapYear)).thenReturn(holidayDuringLeapYear);

	        // Call the service method
	        Holiday savedHoliday = holidayService.addPackage(holidayDuringLeapYear);

	        verify(holidayRepository, times(1)).save(holidayDuringLeapYear);
	        assertNotNull(savedHoliday);
	    }
}
