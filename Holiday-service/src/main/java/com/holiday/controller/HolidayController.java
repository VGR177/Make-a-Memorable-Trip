package com.holiday.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


import com.holiday.model.Holiday;
import com.holiday.model.HolidayBooking;
import com.holiday.service.HolidayService;

@RestController
@RequestMapping("/api")
public class HolidayController {
	
	 	@Autowired
	    private HolidayService holidayService;

//	    @GetMapping("/packages")
//	    public List<Holiday> getPackages() {
//	        return holidayService.getAllPackages();
//	    }
//	    
//	    
	    @GetMapping("/packages")
	    public ResponseEntity<List<Holiday>> getPackages() {
	        List<Holiday> packages = holidayService.getAllPackages();
	        if (packages.isEmpty()) {
	            // Return 404 Not Found if no packages are available
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        // Return 200 OK with the list of packages
	        return new ResponseEntity<>(packages, HttpStatus.OK);
	    }
	    
	    
	    @GetMapping("/packageid/{packageId}")
	    public ResponseEntity<Holiday> getHolidayByPackageId(@PathVariable String packageId) {
	        Holiday holiday = holidayService.getHolidayByPackageId(packageId);

	        // If the holiday is present, return it, otherwise return a 404 Not Found
	        return ResponseEntity.ok(holiday);
	                      
	    }
	    
	    
	    
	    @GetMapping("/booking/{bookId}")
	    public ResponseEntity<HolidayBooking> getBookingByBookId(@PathVariable Long bookId) {
	        HolidayBooking booking = holidayService.getBookingByBookId(bookId);

	        // If the booking is present, return it, otherwise return a 404 Not Found
	        return ResponseEntity.ok(booking);
	    }


	    
	    @PostMapping("/bookings")
	    public ResponseEntity<String> bookPackage(@RequestParam String packageId, @RequestParam String userEmail) throws Exception {
	        return new ResponseEntity<>(holidayService.bookPackage(packageId, userEmail), HttpStatus.OK);
	    }



	    @PostMapping("/packages")
	    public ResponseEntity<Holiday> addPackage(@RequestBody Holiday holiday) {
	        return new ResponseEntity<>(holidayService.addPackage(holiday), HttpStatus.CREATED);
	    }

	    @PutMapping("/packages/{packageId}")
	    public Holiday updatePackage(@PathVariable String packageId, @RequestBody Holiday updatedPackage) throws Exception {
	        return holidayService.updatePackage(packageId, updatedPackage);
	    }


	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteHoliday(@PathVariable Long id) {
	       
	            
	            return ResponseEntity.ok(holidayService.deleteHoliday(id));
	    }

}


//http://localhost:8082/api/bookings?packageId=HOLIDAY3123&userEmail=zxc@example.com
