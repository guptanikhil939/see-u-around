package com.seeuaround.dto.mobile;

import com.google.gson.Gson;


public class UserNetworkDTO
{
	private Integer userId;

	private Integer friendId;
	
	private String faceBookId;
	
	private String gcmId;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Integer getFriendId()
	{
		return friendId;
	}

	public void setFriendId(Integer friendId)
	{
		this.friendId = friendId;
	}
	
    public String getFaceBookId()
	{
		return faceBookId;
	}

	public void setFaceBookId(String faceBookId)
	{
		this.faceBookId = faceBookId;
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

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getJsonString() {
        return new Gson().toJson(this);
    }
}