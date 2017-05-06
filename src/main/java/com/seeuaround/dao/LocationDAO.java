package com.seeuaround.dao;

import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.Location;

public interface LocationDAO
{
	public boolean updateLocation(Location location) throws PersistenceException;

	public Location getLocation(Integer userId) throws PersistenceException;
}