package com.seeuaround.dto.mobile;

import java.util.Date;

public class LocationDTO
{
	private Integer userId;
	private String latitude;
	private String longitude;
	private String provider;
	private String accuracy;
	private Date updatedOn;
	private String lastSeen;
	private String distance;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getProvider()
	{
		return provider;
	}

	public void setProvider(String provider)
	{
		this.provider = provider;
	}

	public String getAccuracy()
	{
		return accuracy;
	}

	public void setAccuracy(String accuracy)
	{
		this.accuracy = accuracy;
	}

	public Date getUpdatedOn()
	{
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn)
	{
		this.updatedOn = updatedOn;
	}

	public String getLastSeen()
	{
		return lastSeen;
	}

	public void setLastSeen(String lastSeen)
	{
		this.lastSeen = lastSeen;
	}

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}
}