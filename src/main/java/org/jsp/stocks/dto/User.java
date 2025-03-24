package org.jsp.stocks.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3, max = 15, message = "* It Should be between 3~15 charecters")
	private String name;
	@Email(message = "* It Should be Valid Email")
	@NotEmpty(message = "* It is Required Field")
	private String email;
	@DecimalMin(value = "6000000000", message = "* It should be Proper Mobile Number")
	@DecimalMax(value = "9999999999", message = "* It should be Proper Mobile Number")
	private long mobile;
	@Past(message = "* Enter Proper DOB")
	@NotNull(message = "* It is Required Field")
	private LocalDate dob;
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "* It should be One Uppercase, One Lowercase, One Number, One Special Chrecter,Minium 8 charecters")
	private String password;
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "* It should be One Uppercase, One Lowercase, One Number, One Special Chrecter,Minium 8 charecters")
	private String confirmPassword;
	private int otp;
	private boolean verified;
}
