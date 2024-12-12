package vttp.ssf.assessment.eventmanagement.models;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Registration {

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 5, max = 25, message = "Name must be between 5 to 25 characters long")
    private String fullName; 

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth Date must be in the past")
    private LocalDate birthDate; 

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email must be in valid format")
    @Size(max = 50, message = "Email must be less than 50 charcters long")
    private String email; 

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Phone number must be valid, starting with 8 or 9, followed by 7 digits")
    private String mobileNumber;

    @Min(value = 1,message = "Minimum value of 1 ticket")
    @Max(value = 3, message = "Maximum value of 3 tickets")
    private Integer numTicketsRequested;

    // constructors
    public Registration() {
    }

    public Registration(String fullName, LocalDate birthDate, String email, String mobileNumber,
            Integer numTicketsRequested) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    // getter setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getNumTicketsRequested() {
        return numTicketsRequested;
    }

    public void setNumTicketsRequested(Integer numTicketsRequested) {
        this.numTicketsRequested = numTicketsRequested;
    } 

    
    
}
