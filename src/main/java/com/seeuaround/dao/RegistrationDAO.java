package com.seeuaround.dao;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.Otp;
import com.seeuaround.model.User;

public interface RegistrationDAO
{
	public boolean saveUser(User user) throws PersistenceException;

	public boolean registeredUserForMobile(String phoneNumber)
			throws PersistenceException;

	public boolean saveOTP(Otp otp) throws PersistenceException;

	public boolean confirmOTP(String phoneNumber, String otp)
			throws PersistenceException;

	public boolean deleteOTP(String phoneNumber) throws PersistenceException;

	public Otp getOtpInfo(String phoneNumber) throws PersistenceException;
	
	public User getUserInfo(String phoneNumber) throws PersistenceException;

	public User getUserInfoByUserId(Integer userId) throws PersistenceException;
}
