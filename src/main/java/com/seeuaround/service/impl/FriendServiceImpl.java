package com.seeuaround.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.FriendDAO;
import com.seeuaround.dao.RegistrationDAO;
import com.seeuaround.dto.mobile.MobileResultDTO;
import com.seeuaround.dto.mobile.UserNetworkDTO;
import com.seeuaround.dto.mobile.UserNetworkListDTO;
import com.seeuaround.exception.ServiceException;
import com.seeuaround.model.User;
import com.seeuaround.model.UserNetwork;
import com.seeuaround.service.FriendService;
import com.seeuaround.util.CommonUtil;
import com.seeuaround.util.Constants;

@Transactional
@Service("friendService")
public class FriendServiceImpl implements FriendService
{
	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	RegistrationDAO registrationDAO;

	private static final Logger log = Logger
			.getLogger(FriendServiceImpl.class);

	public List<UserNetwork> getFriends(Integer userId)
			throws ServiceException
	{
		log.info("Inside getFriends() method");
		
		List<UserNetwork> userNetwork = null;

		try
		{
			userNetwork = friendDAO.getFriends(userId);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}
		return userNetwork;
	}

	public String getFriends(HttpServletRequest request)
			throws ServiceException
	{
		MobileResultDTO mobileResultDTO = new MobileResultDTO();
		
		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String userId = (String) (jsonObj.get("userId") == null ? "0": jsonObj.get("userId"));
			
			List<UserNetwork> userNetwork = getFriends(Integer.parseInt(userId));
			List<UserNetworkDTO> userNetworkList = new ArrayList<UserNetworkDTO>();
			UserNetworkListDTO userNetworkListDTO = new UserNetworkListDTO();
			
			if(null!=userNetwork && userNetwork.size()>0)
			{
			 	for(UserNetwork network : userNetwork)
			 	{
			 		User user = registrationDAO.getUserInfoByUserId(network.getFriendId());
			 		
			 		UserNetworkDTO userNetworkDTO = new UserNetworkDTO();
			 		
			 		userNetworkDTO.setFriendId(network.getFriendId());
			 		userNetworkDTO.setFirstName(user.getFirstName());
			 		userNetworkDTO.setLastName(user.getLastName());
			 		userNetworkDTO.setFaceBookId(user.getFaceBookId());
			 		userNetworkDTO.setGcmId(user.getGcmId());
			 		
			 		userNetworkList.add(userNetworkDTO);
			 	}
			 	
			 	userNetworkListDTO.setUserNetworkDTO(userNetworkList);
			 	
				mobileResultDTO.setResponse(Constants.SUCCESS);
				mobileResultDTO.setMessage(userNetworkListDTO.getJsonString());
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.NO_FRIEND);
			}
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}
		
		return mobileResultDTO.getJsonString();
	}
}