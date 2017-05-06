package com.seeuaround.dao.impl;

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

import com.seeuaround.dao.ContactsDAO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.model.UserContacts;

@Repository("contactsDAO")
public class ContactsDAOImpl implements ContactsDAO
{
	private static final Logger log = Logger
			.getLogger(ContactsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public UserContacts getUserContact(Integer userId, String phoneNumber)
			throws PersistenceException
	{
		log.debug("In Method getUserContact()");
		
		UserContacts userContacts = null;

		try
		{
			Criteria cr = getCurrentSession().createCriteria(UserContacts.class);
			cr.add(Restrictions.eq("userId", userId));
			cr.add(Restrictions.eq("phoneNumber", phoneNumber));

			List object = cr.list();

			if (null != object && object.size()>0)
			{
				userContacts = (UserContacts)object.get(0);
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
		return userContacts;
	}
	
	public boolean saveUserContact(UserContacts userContacts)
			throws PersistenceException
	{
		log.debug("In Method saveUserContacts()");

		try
		{
			getCurrentSession().save(userContacts);
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
	
	public boolean updateUserContact(UserContacts userContacts)
			throws PersistenceException
	{
		log.debug("In Method saveUserContacts()");

		try
		{
			getCurrentSession().update(userContacts);
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
	
	public boolean deleteContact(Integer userId, String phoneNumber) throws PersistenceException
	{
		log.debug("In Method deleteContact()");

		try
		{
			Query query = getCurrentSession().createQuery(
					"delete UserContacts where userId = :userId AND phoneNumber = :phoneNumber");
			query.setParameter("userId", userId);
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
}