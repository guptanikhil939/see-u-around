package com.seeuaround.service;

import javax.servlet.http.HttpServletRequest;

import com.seeuaround.dto.mobile.MobileResultDTO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;

public interface RegistrationService
{
	public String registerUserFromMobile(HttpServletRequest request) throws ServiceException, PersistenceException;

	public String registeredUserForMobile(HttpServletRequest request) throws ServiceException, PersistenceException;

	public String confirmOTPFromMobile(HttpServletRequest request) throws ServiceException, PersistenceException;
	
	public String updateGcmId(HttpServletRequest request) throws ServiceException, PersistenceException;

	public String updateFaceBookId(HttpServletRequest request) throws ServiceException, PersistenceException;
}
