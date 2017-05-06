package com.seeuaround.dao;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.UserContacts;

public interface ContactsDAO
{
	UserContacts getUserContact(Integer userId, String phoneNumber)
			throws PersistenceException;

	boolean saveUserContact(UserContacts userContact)
			throws PersistenceException;

	boolean updateUserContact(UserContacts userContact)
			throws PersistenceException;

	public boolean deleteContact(Integer userId, String phoneNumber)
			throws PersistenceException;
}
