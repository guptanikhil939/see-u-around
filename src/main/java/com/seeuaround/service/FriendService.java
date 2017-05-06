package com.seeuaround.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.seeuaround.exception.ServiceException;
import com.seeuaround.model.User;
import com.seeuaround.model.UserNetwork;

public interface FriendService
{
	public List<UserNetwork> getFriends(Integer userId) throws ServiceException;

	public String getFriends(HttpServletRequest request) throws ServiceException;
}
