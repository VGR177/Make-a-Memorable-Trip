package com.holiday;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import com.holiday.exception.InvalidInputException;
import com.holiday.model.Holiday;
import com.holiday.model.HolidayBooking;
import com.holiday.repository.HolidayBookingRepository;
import com.holiday.repository.HolidayRepository;
import com.holiday.service.HolidayService;


@SpringBootTest
class HolidayServiceApplicationTests {
	
	
	
	
	@Test
	void contextLoads() {
	}

}
