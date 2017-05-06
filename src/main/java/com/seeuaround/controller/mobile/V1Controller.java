package com.seeuaround.controller.mobile ;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;
import com.seeuaround.service.ContactsService;
import com.seeuaround.service.FriendService;
import com.seeuaround.service.LocationService;
import com.seeuaround.service.RegistrationService;
import com.seeuaround.util.CommonUtil;
import com.seeuaround.util.Constants;

@Controller
@RequestMapping(value = "/mobile/v1/*")
public class V1Controller
{
	@Autowired
	RegistrationService registrationService;

	@Autowired
	ContactsService contactsService;

	@Autowired
	LocationService locationService;
	
	@Autowired
	FriendService friendService;

	private static final Logger log = Logger.getLogger(V1Controller.class);

	@RequestMapping(value = "/register", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String register(HttpServletRequest request)
	{
		log.debug("In Method register(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = registrationService.registerUserFromMobile(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}

	@RequestMapping(value = "/confirmOTP", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String confirmOTP(HttpServletRequest request)
	{
		log.debug("In Method confirmOTP(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = registrationService.confirmOTPFromMobile(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}

	@RequestMapping(value = "/syncPhoneBook", method = { RequestMethod.POST })
	@ResponseBody
	public String syncPhoneBook(HttpServletRequest request)
	{
		log.debug("SyncController :: syncPhoneBook");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = contactsService.syncPhoneBook(request);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}

	@RequestMapping(value = "/updateGcmId", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ ResponseBody
	public String updateGcmId(HttpServletRequest request)
	{
		log.debug("In Method updateGcmId(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = registrationService.updateGcmId(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}

	@RequestMapping(value = "/updateLocation", method = { RequestMethod.POST })
	@ResponseBody
	public String updateLocation(HttpServletRequest request)
	{
		log.debug("LocationController :: updateLocation");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = locationService.updateLocation(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}

	@RequestMapping(value = "/aroundMe", method = { RequestMethod.POST })
	@ResponseBody
	public String aroundMe(HttpServletRequest request)
	{
		log.debug("LocationController :: aroundMe");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = locationService.aroundMe(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}
	
	@RequestMapping(value = "/updateFaceBookId", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ ResponseBody
	public String updateFaceBookId(HttpServletRequest request)
	{
		log.debug("In Method updateFaceBookId(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = registrationService.updateFaceBookId(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}
		
	@RequestMapping(value = "/deleteContact", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String deleteContact(HttpServletRequest request)
	{
		log.debug("In Method addContact(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = contactsService.deleteContact(request);
		}
		catch (PersistenceException exception)
		{
			log.error("Persistence Exception : " + exception);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}
	
	@RequestMapping(value = "/getFriends", method = { RequestMethod.POST }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getFriends(HttpServletRequest request)
	{
		log.debug("In Method getFriends(request)");

		String result = Constants.DEFAULT_RESULT;

		try
		{
			result = friendService.getFriends(request);
		}
		catch (ServiceException exception)
		{
			log.error("Service Exception : " + exception);
		}
		catch (Exception exception)
		{
			log.error("Controller Exception : " + exception);
		}

		return result;
	}
}