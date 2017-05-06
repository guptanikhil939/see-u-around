package com.seeuaround.service;

import javax.servlet.http.HttpServletRequest;

import com.seeuaround.dto.mobile.UserContactsDTO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;

public interface ContactsService
{
	public String syncPhoneBook(HttpServletRequest request)
			throws ServiceException, PersistenceException;

	public UserContactsDTO syncFaceBook(HttpServletRequest request)
			throws ServiceException, PersistenceException;

	public String deleteContact(HttpServletRequest request)
			throws ServiceException, PersistenceException;
}