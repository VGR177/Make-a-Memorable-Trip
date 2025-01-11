package com.holiday.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
	
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true)
	    @NotBlank(message = "Package ID is required")
	    private String packageId;

	    @NotBlank(message = "Package name is required")
	    private String name;

	    @ElementCollection
	    @NotEmpty(message = "Includes list cannot be empty")
	    private List<String> includes;

	    @Positive(message = "Price must be greater than zero")
	    @NotNull(message = "Price is required")
	    private Double price;

	    @PositiveOrZero(message = "Availability cannot be negative")
	    @NotNull(message = "Availability is required")
	    private Integer availability;

	    @NotBlank(message = "Destination is required")
	    private String destination;

	    @FutureOrPresent(message = "Start date must be today or in the future")
	    @NotNull(message = "Start date is required")
	    private LocalDate startDate;

	    @Future(message = "End date must be in the future")
	    @NotNull(message = "End date is required")
	    private LocalDate endDate;

	    @Size(max = 500, message = "Description cannot exceed 500 characters")
	    private String description;
	    
//	    @OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<HolidayBooking> bookings;

	    // other fields and methods

}
