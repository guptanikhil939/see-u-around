package com.seeuaround.dto.mobile;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UserNetworkListDTO
{
	@SerializedName("friends")
	private List<UserNetworkDTO> userNetworkDTO;
	
    public List<UserNetworkDTO> getUserNetworkDTO()
	{
		return userNetworkDTO;
	}

	public void setUserNetworkDTO(List<UserNetworkDTO> userNetworkDTO)
	{
		this.userNetworkDTO = userNetworkDTO;
	}

	public String getJsonString() {
        return new Gson().toJson(this);
    }
}