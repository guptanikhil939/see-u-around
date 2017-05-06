package com.seeuaround.dto.mobile;

public class UserContactsDTO
{
	private Integer userId;

	private Integer phoneNumber;

	private String firstName;

	private String LastName;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Integer getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return LastName;
	}

	public void setLastName(String lastName)
	{
		LastName = lastName;
	}
}