package com.seeuaround.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.LocationDAO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.Location;
import com.seeuaround.model.User;
import com.seeuaround.service.impl.LocationServiceImpl;

@Repository("locationDAO")
public class LocationDAOImpl implements LocationDAO
{
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = Logger.getLogger(LocationDAOImpl.class);

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public boolean updateLocation(Location location)
			throws PersistenceException
	{

		try
		{
			getCurrentSession().saveOrUpdate(location);
			return true;
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
	}

	public Location getLocation(Integer userId) throws PersistenceException
	{
		{
			log.debug("In Method getLocation()");

			List<Location> result = Collections.EMPTY_LIST;
			Location location = null;

			try
			{
				Criteria cr = getCurrentSession()
						.createCriteria(Location.class);
				cr.add(Restrictions.eq("userId", userId));

				result = (List<Location>) cr.list();

				if (null != result && result.size() > 0)
				{
					location = result.get(0);
					return location;
				}
			}
			catch (HibernateException exception)
			{
				throw new PersistenceException(exception.getMessage(),
						exception);
			}
			catch (Exception exception)
			{
				throw new PersistenceException(exception.getMessage(),
						exception);
			}
			return location;
		}
	}
}