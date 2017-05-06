package com.seeuaround.dto.mobile;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class FriendLocationListDTO
{
	@SerializedName("aroundMe")
	private List<LocationDTO> locationDTO;

	public List<LocationDTO> getLocationDTO()
	{
		return locationDTO;
	}

	public void setLocationDTO(List<LocationDTO> locationDTO)
	{
		this.locationDTO = locationDTO;
	}

	public String getJsonString()
	{
		return new Gson().toJson(this);
	}
}