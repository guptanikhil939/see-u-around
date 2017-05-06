package com.seeuaround.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_contacts")
public class UserContacts
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "contact_id")
	private Integer contactId;
	
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "name")
	private String name;

	public Integer getContactId()
	{
		return contactId;
	}

	public void setContactId(Integer contactId)
	{
		this.contactId = contactId;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}