package com.seeuaround.service;

import javax.servlet.http.HttpServletRequest;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;

public interface LocationService
{
	public String updateLocation(HttpServletRequest request) throws ServiceException, PersistenceException;

	public String aroundMe(HttpServletRequest request) throws ServiceException, PersistenceException;
}
