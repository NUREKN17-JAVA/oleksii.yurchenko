package ua.nure.cs.yurchenko.usermanagement.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 709875642944423295L;
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFullName() {
		return getLastName() + ", " + getFirstName();
	}
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		calendar.setTime(getDateOfBirth());
		int birthYear = calendar.get(Calendar.YEAR);
		return currentYear - birthYear;
	}
	
	
}