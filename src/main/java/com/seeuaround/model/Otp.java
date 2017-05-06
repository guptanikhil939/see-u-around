package com.seeuaround.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otp")
public class Otp
{	
	@Id
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "gcm_id")
	private String gcmId;
	
	@Column(name = "otp")
	private String otp;

	@Column(name = "generation_date")
	private Date generationDate;

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

	public String getOtp()
	{
		return otp;
	}

	public void setOtp(String otp)
	{
		this.otp = otp;
	}

	public Date getGenerationDate()
	{
		return generationDate;
	}

	public void setGenerationDate(Date generationDate)
	{
		this.generationDate = generationDate;
	}
}