package com.seeuaround.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.RegistrationDAO;
import com.seeuaround.dto.mobile.MobileDataDTO;
import com.seeuaround.dto.mobile.MobileResultDTO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;
import com.seeuaround.model.Otp;
import com.seeuaround.model.User;
import com.seeuaround.service.RegistrationService;
import com.seeuaround.util.CommonUtil;
import com.seeuaround.util.ConfigurationReader;
import com.seeuaround.util.Constants;

@Transactional
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService
{

	@Autowired
	RegistrationDAO registrationDAO;

	MobileDataDTO mobileDataDTO = null;

	private static final Logger log = Logger
			.getLogger(RegistrationServiceImpl.class);

	public String registeredUserForMobile(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method registeredUserForMobile(request)");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String phoneNumber = (String) jsonObj.get("phoneNumber");

			mobileDataDTO = new MobileDataDTO();
			mobileDataDTO.setPhoneNumber(phoneNumber);

			if (CommonUtil.validPhoneNumber(phoneNumber))
			{
				boolean registeredUser = false;

				registeredUser = registrationDAO
						.registeredUserForMobile(phoneNumber);

				if (registeredUser)
				{
					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO
							.setResponse(Constants.USER_ALREADY_REGISTERED);
				}
				else
				{
					String gcmId = (String) (jsonObj.get("gcmId") == null ? ""
							: jsonObj.get("gcmId"));

					mobileDataDTO.setGcmId(gcmId);

					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO.setMessage(Constants.USER_NOT_REGISTERED);
				}
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.INVALID_PHONE_NUMBER);
			}
		}
		catch (NumberFormatException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
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

	public String registerUserFromMobile(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method registerUserFromMobile()");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			String result = registeredUserForMobile(request);

			if (null != result
					&& result.contains(Constants.USER_NOT_REGISTERED))
			{
				boolean otpSent = sendOTP(mobileDataDTO);

				if (otpSent)
				{
					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO.setMessage(Constants.OTP_SENT);
				}
				else
				{
					mobileResultDTO.setResponse(Constants.FAILURE);
					mobileResultDTO
							.setMessage(Constants.TECHNICAL_PROBLEM_OCCURED);
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

		return mobileResultDTO.getJsonString();
	}

	private boolean sendOTP(MobileDataDTO mobileDataDTO)
			throws PersistenceException, ServiceException
	{
		log.debug("In Method sendOTP()");

		try
		{
			String randomNumber = CommonUtil.generateFourDigitRandomNumber();

			Otp otp = new Otp();
			boolean otpSaved;

			otp.setPhoneNumber(mobileDataDTO.getPhoneNumber());
			otp.setGcmId(mobileDataDTO.getGcmId());
			otp.setOtp(randomNumber);
			otp.setGenerationDate(new Date());

			otpSaved = registrationDAO.saveOTP(otp);

			if (otpSaved)
			{
				String smsMessage = ConfigurationReader.configurationBean
						.getSmsOTPMessage1()
						+ otp.getOtp()
						+ ConfigurationReader.configurationBean
								.getSmsOTPMessage2();

				List<String> phoneNumbers = new ArrayList<String>();
				phoneNumbers.add(mobileDataDTO.getPhoneNumber());

				boolean messageSent = CommonUtil.callSMSServer(smsMessage,
						phoneNumbers);

				if (messageSent)
				{
					return true;
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

		return false;
	}

	public String confirmOTPFromMobile(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method confirmOTPFromMobile()");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String phoneNumber = (String) (jsonObj.get("phoneNumber") == null ? ""
					: jsonObj.get("phoneNumber"));
			String otp = (String) (jsonObj.get("otp") == null ? "" : jsonObj
					.get("otp"));

			if (CommonUtil.validPhoneNumber(phoneNumber))
			{
				boolean otpConfirmed = false;

				otpConfirmed = registrationDAO.confirmOTP(phoneNumber, otp);

				if (otpConfirmed)
				{
					User user = null;
					Otp otpInfo = new Otp();

					boolean userSaved = false;

					otpInfo = registrationDAO.getOtpInfo(phoneNumber);

					if (null != otpInfo && null != otpInfo.getPhoneNumber())
					{
						user = new User();
						user.setPhoneNumber(otpInfo.getPhoneNumber());
						user.setGcmId(otpInfo.getGcmId());
						user.setCreatedDate(new Date());
						user.setStatus(true);
					}

					userSaved = registrationDAO.saveUser(user);
					boolean otpDeleted = registrationDAO.deleteOTP(phoneNumber);

					if (userSaved && otpDeleted)
					{
						mobileResultDTO.setResponse(Constants.SUCCESS);
						mobileResultDTO.setMessage(Constants.ACCOUNT_CREATED);
					}
					else
					{
						mobileResultDTO.setResponse(Constants.FAILURE);
						mobileResultDTO
								.setMessage(Constants.TECHNICAL_PROBLEM_OCCURED);
					}
				}
				else
				{
					mobileResultDTO.setResponse(Constants.FAILURE);
					mobileResultDTO.setMessage(Constants.OTP_NOT_VALID);
				}
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.INVALID_PHONE_NUMBER);
			}
		}
		catch (NumberFormatException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
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

	public String updateGcmId(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method updateGcmId(request)");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String userId = (String) (jsonObj.get("userId") == null ? "0"
					: jsonObj.get("userId"));
			String gcmId = (String) (jsonObj.get("gcmId") == null ? ""
					: jsonObj.get("gcmId"));

			User user = registrationDAO.getUserInfoByUserId(Integer
					.parseInt(userId));

			if (null != user && null != user.getPhoneNumber())
			{
				user.setGcmId(gcmId);

				boolean userSaved = registrationDAO.saveUser(user);

				if (userSaved)
				{
					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO
							.setMessage(Constants.ACCOUNT_MAPPED_FOR_GOOGLE_CLOUD_MESSAGING);
				}
				else
				{
					mobileResultDTO.setResponse(Constants.FAILURE);
					mobileResultDTO
							.setMessage(Constants.TECHNICAL_PROBLEM_OCCURED);
				}
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.USER_NOT_REGISTERED);
			}
		}
		catch (NumberFormatException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
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

	public String updateFaceBookId(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		log.debug("In Method updateFaceBookId(request)");

		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String userId = (String) (jsonObj.get("userId") == null ? "0"
					: jsonObj.get("userId"));
			String faceBookId = (String) (jsonObj.get("faceBookId") == null ? ""
					: jsonObj.get("faceBookId"));

			User user = registrationDAO.getUserInfoByUserId(Integer
					.parseInt(userId));

			if (null != user)
			{
				user.setFaceBookId(faceBookId);

				boolean userSaved = registrationDAO.saveUser(user);

				if (userSaved)
				{
					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO
							.setMessage(Constants.FACEBOOK_ACCOUNT_MAPPED);
				}
				else
				{
					mobileResultDTO.setResponse(Constants.FAILURE);
					mobileResultDTO
							.setMessage(Constants.TECHNICAL_PROBLEM_OCCURED);
				}
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.USER_NOT_REGISTERED);
			}
		}
		catch (NumberFormatException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
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
}