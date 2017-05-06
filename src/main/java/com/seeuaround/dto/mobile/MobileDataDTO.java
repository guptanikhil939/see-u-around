package com.seeuaround.dto.mobile;

public class MobileDataDTO
{
	private String phoneNumber;
	private String gcmId;
	private String firstName;
	private String lastName;
	private String email;

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getGcmId()
	{
		return gcmId;
	}

	public void setGcmId(String gcmId)
	{
		this.gcmId = gcmId;
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
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}