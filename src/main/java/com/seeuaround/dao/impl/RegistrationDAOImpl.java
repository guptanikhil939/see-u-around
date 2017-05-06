package com.seeuaround.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.RegistrationDAO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.Otp;
import com.seeuaround.model.User;
import com.seeuaround.service.impl.RegistrationServiceImpl;

@Repository("registrationDAO")
public class RegistrationDAOImpl implements RegistrationDAO
{
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = Logger
			.getLogger(RegistrationDAOImpl.class);

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public boolean saveUser(User user) throws PersistenceException
	{
		log.debug("In Method saveUser()");

		try
		{
			getCurrentSession().saveOrUpdate(user);
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

	public boolean registeredUserForMobile(String phoneNumber)
			throws PersistenceException
	{
		log.debug("In Method registeredUserFromMobile()");

		try
		{
			Criteria cr = getCurrentSession().createCriteria(User.class);
			cr.add(Restrictions.eq("phoneNumber", phoneNumber));

			List result = cr.list();
			if (result.size() > 0)
			{
				return true;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return false;
	}

	@Override
	public boolean saveOTP(Otp otp) throws PersistenceException
	{
		log.debug("In Method saveOTP()");

		try
		{
			getCurrentSession().saveOrUpdate(otp);
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

	@Override
	public boolean confirmOTP(String phoneNumber, String otp)
			throws PersistenceException
	{
		log.debug("In Method confirmOTP()");

		try
		{
			Criteria cr = getCurrentSession().createCriteria(Otp.class);
			cr.add(Restrictions.eq("phoneNumber", phoneNumber));
			cr.add(Restrictions.eq("otp", otp));

			List result = cr.list();
			if (result.size() > 0)
			{
				return true;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return false;
	}

	public boolean deleteOTP(String phoneNumber) throws PersistenceException
	{
		log.debug("In Method deleteOTP()");

		try
		{
			Query query = getCurrentSession().createQuery(
					"delete Otp where phoneNumber = :phoneNumber");
			query.setParameter("phoneNumber", phoneNumber);

			int result = query.executeUpdate();

			if (result > 0)
			{
				return true;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return false;
	}

	public Otp getOtpInfo(String phoneNumber) throws PersistenceException
	{
		log.debug("In Method getUser()");

		List<Otp> result = Collections.EMPTY_LIST;
		Otp otp = null;
		
		try
		{
			Criteria cr = getCurrentSession().createCriteria(Otp.class);
			cr.add(Restrictions.eq("phoneNumber", phoneNumber));

			result = (List<Otp>) cr.list();
			
			if (null != result && result.size() > 0)
			{
				otp = result.get(0);
				return otp;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return otp;
	}
	
	public User getUserInfo(String phoneNumber) throws PersistenceException
	{
		log.debug("In Method getUserInfo()");

		List<User> result = Collections.EMPTY_LIST;
		User user = null;
		
		try
		{
			Criteria cr = getCurrentSession().createCriteria(User.class);
			cr.add(Restrictions.eq("phoneNumber", phoneNumber));

			result = (List<User>) cr.list();
			
			if (null != result && result.size() > 0)
			{
				user = result.get(0);
				return user;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return user;
	}
	
	public User getUserInfoByUserId(Integer userId) throws PersistenceException
	{
		log.debug("In Method getUserInfoByUserId()");

		List<User> result = Collections.EMPTY_LIST;
		User user = null;
		
		try
		{
			Criteria cr = getCurrentSession().createCriteria(User.class);
			cr.add(Restrictions.eq("userId", userId));

			result = (List<User>) cr.list();
			
			if (null != result && result.size() > 0)
			{
				user = result.get(0);
				return user;
			}
		}
		catch (HibernateException exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		catch (Exception exception)
		{
			throw new PersistenceException(exception.getMessage(), exception);
		}
		return user;
	}
}
