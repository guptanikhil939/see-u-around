package com.seeuaround.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.ContactsDAO;
import com.seeuaround.dto.mobile.MobileResultDTO;
import com.seeuaround.dto.mobile.UserContactsDTO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;
import com.seeuaround.model.UserContacts;
import com.seeuaround.service.ContactsService;
import com.seeuaround.util.CommonUtil;
import com.seeuaround.util.Constants;

@Transactional
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService
{
	private static final Logger log = Logger
			.getLogger(ContactsServiceImpl.class);

	@Autowired
	ContactsDAO contactsDAO;

	public String syncPhoneBook(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method syncPhoneBook(request)");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject json = CommonUtil.readJSON(request);
			Integer userId = Integer
					.parseInt((String) (json.get("userId") == null ? "0" : json
							.get("userId")));

			if (!"0".equals(userId))
			{
				JSONArray phoneBookArray = new JSONArray(
						(json.get("phoneNumbers") == null ? ""
								: json.get("phoneNumbers")).toString());

				for (int i = 0; i < phoneBookArray.length(); i++)
				{
					JSONObject obj = phoneBookArray.getJSONObject(i);

					UserContacts userContact = null;
					String phoneNumber = (String) (obj.get("phoneNumber") == null ? ""
							: obj.get("phoneNumber"));
					phoneNumber = phoneNumber.replaceAll("\\D+", "");

					if (CommonUtil.validPhoneNumber(phoneNumber))
					{
						phoneNumber = phoneNumber
								.substring(phoneNumber.length() - 10,
										phoneNumber.length());

						userContact = contactsDAO.getUserContact(userId,
								phoneNumber);

						if (null != userContact
								&& null != userContact.getPhoneNumber())
						{
							userContact.setName(obj.getString("name"));
							contactsDAO.updateUserContact(userContact);
						}
						else
						{
							userContact = new UserContacts();
							userContact.setUserId(userId);
							userContact.setPhoneNumber(phoneNumber);
							userContact.setName(obj.getString("name"));
							contactsDAO.saveUserContact(userContact);
						}
					}
				}
				mobileResultDTO.setResponse(Constants.SUCCESS);
				mobileResultDTO.setMessage(Constants.PHONEBOOK_SYNCHRONIZED);
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.PHONEBOOK_NOT_SYNCHRONIZED);
			}
		}
		catch (PersistenceException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return mobileResultDTO.getJsonString();
	}

	public UserContactsDTO syncFaceBook(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String deleteContact(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method deleteContact(request)");

		String result = Constants.FAILURE;

		try
		{
			JSONObject json = CommonUtil.readJSON(request);
			Integer userId = Integer
					.parseInt((String) (json.get("userId") == null ? "0" : json
							.get("userId")));

			String phoneNumber = (String) (json.get("phoneNumber") == null ? ""
					: json.get("phoneNumber"));

			if (!"0".equals(userId) && !"".equals(phoneNumber))
			{
				boolean contactDeleted = contactsDAO.deleteContact(userId, phoneNumber);

				if (contactDeleted)
				{
					result = Constants.SUCCESS;
				}
				else
				{
					result = Constants.FAILURE;
				}
			}
		}
		catch (PersistenceException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return result;
	}
}