package com.seeuaround.dto.mobile;

import com.google.gson.Gson;
import com.seeuaround.util.Constants;

public class MobileResultDTO
{
	private String response;
	private String message;
	
	public MobileResultDTO()
	{
		this.response = Constants.FAILURE;
		this.message = Constants.TECHNICAL_PROBLEM_OCCURED;
	}

	public String getResponse()
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getJsonString()
	{
		return new Gson().toJson(this);
	}
}