package com.seeuaround.dao;

import java.util.List;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.User;
import com.seeuaround.model.UserNetwork;

public interface FriendDAO
{	
	public List<UserNetwork> getFriends(Integer userId) throws PersistenceException;
}