package com.seeuaround.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeuaround.dao.FriendDAO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.UserNetwork;

@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO
{
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = Logger
			.getLogger(FriendDAOImpl.class);

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public List<UserNetwork> getFriends(Integer userId)
			throws PersistenceException
	{
		log.debug("In Method getFriends()");
		
		List<UserNetwork> userNetwork = null;

		try
		{
			Criteria cr = getCurrentSession().createCriteria(UserNetwork.class);
			cr.add(Restrictions.eq("userId", userId));

			userNetwork = (List<UserNetwork>) cr.list();
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return userNetwork;
	}
}