package com.seeuaround.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeuaround.dao.LocationDAO;
import com.seeuaround.dto.mobile.FriendLocationListDTO;
import com.seeuaround.dto.mobile.LocationDTO;
import com.seeuaround.dto.mobile.MobileResultDTO;
import com.seeuaround.exception.PersistenceException;
import com.seeuaround.exception.ServiceException;
import com.seeuaround.model.Location;
import com.seeuaround.model.UserNetwork;
import com.seeuaround.service.FriendService;
import com.seeuaround.service.LocationService;
import com.seeuaround.util.CommonUtil;
import com.seeuaround.util.Constants;

@Transactional
@Service("locationService")
public class LocationServiceImpl implements LocationService
{
	@Autowired
	LocationDAO locationDAO;

	@Autowired
	FriendService friendService;

	private static final Logger log = Logger
			.getLogger(LocationServiceImpl.class);

	public String updateLocation(HttpServletRequest request)
			throws ServiceException, PersistenceException
	{
		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String userId = (String) (jsonObj.get("userId") == null ? "0"
					: jsonObj.get("userId"));
			String longitude = (String) (jsonObj.get("longitude") == null ? ""
					: jsonObj.get("longitude"));
			String latitude = (String) (jsonObj.get("latitude") == null ? ""
					: jsonObj.get("latitude"));
			String provider = (String) (jsonObj.get("provider") == null ? ""
					: jsonObj.get("provider"));
			String accuracy = (String) (jsonObj.get("accuracy") == null ? ""
					: jsonObj.get("accuracy"));

			LocationDTO locationDTO = new LocationDTO();
			locationDTO.setLatitude(latitude);
			locationDTO.setLongitude(longitude);
			locationDTO.setUserId(Integer.parseInt(userId));
			locationDTO.setProvider(provider);
			locationDTO.setAccuracy(accuracy);
			
			Location location = locationDAO.getLocation(Integer.parseInt(userId));
			
			if(location==null)
			{
				location = new Location();
				location.setUserId(locationDTO.getUserId());
			}
			
			location.setLatitude(locationDTO.getLatitude());
			location.setLongitude(locationDTO.getLongitude());
			location.setProvider(locationDTO.getProvider());
			location.setAccuracy(locationDTO.getAccuracy());

			boolean locationUpdated = locationDAO.updateLocation(location);

			if (locationUpdated)
			{
				mobileResultDTO.setResponse(Constants.SUCCESS);
				mobileResultDTO.setMessage(Constants.LOCATION_UPDATED);
			}
			else
			{
				mobileResultDTO.setResponse(Constants.FAILURE);
				mobileResultDTO.setMessage(Constants.LOCATION_NOT_UPDATED);
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

	public String aroundMe(HttpServletRequest request) throws ServiceException,
			PersistenceException
	{
		MobileResultDTO mobileResultDTO = new MobileResultDTO();

		try
		{
			JSONObject jsonObj = CommonUtil.readJSON(request);
			String userId = (String) (jsonObj.get("userId") == null ? "0"
					: jsonObj.get("userId"));

			Location userLocation = locationDAO.getLocation(Integer
					.parseInt(userId));
			LocationDTO friendLocationDTO = null;
			List<LocationDTO> locationListDTO = new ArrayList<LocationDTO>();

			if (null != userLocation)
			{
				List<UserNetwork> friends = new ArrayList<UserNetwork>();
				friends = friendService.getFriends(Integer.parseInt(userId));
				
				FriendLocationListDTO friendLocationListDTO = new FriendLocationListDTO();

				for (UserNetwork friend : friends)
				{
					Location friendLocation = locationDAO.getLocation(friend
							.getFriendId());

					String distance = String.valueOf(distance(new Double(
							userLocation.getLatitude()), new Double(
							userLocation.getLongitude()), new Double(
							friendLocation.getLatitude()), new Double(
							friendLocation.getLongitude())));

					if (null != distance
							&& distance != Constants.COULD_NOT_FETCH_DISTANCE && null != friendLocation)
					{
						friendLocationDTO = new LocationDTO();
						friendLocationDTO.setUserId(friendLocation.getUserId());
						friendLocationDTO.setLatitude(friendLocation
								.getLatitude());
						friendLocationDTO.setLongitude(friendLocation
								.getLongitude());
						friendLocationDTO.setProvider(friendLocation
								.getProvider());
						friendLocationDTO.setAccuracy(friendLocation
								.getAccuracy());
						friendLocationDTO
								.setLastSeen(getLastUpdated(friendLocation
										.getUpdatedOn()));
						friendLocationDTO
						.setDistance(getDistance(distance));
						locationListDTO.add(friendLocationDTO);
					}
				}
				
				friendLocationListDTO.setLocationDTO(locationListDTO);

				if (null != friendLocationListDTO && friendLocationListDTO.getLocationDTO().size()>0)
				{
					mobileResultDTO.setResponse(Constants.SUCCESS);
					mobileResultDTO.setMessage(friendLocationListDTO.getJsonString());
				}
				else
				{
					mobileResultDTO.setResponse(Constants.FAILURE);
					mobileResultDTO.setMessage(Constants.NO_FRIEND_NEARBY);
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

	private double distance(double lat1, double lon1, double lat2, double lon2)
	{
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return dist;
	}

	private double deg2rad(double deg)
	{
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad)
	{
		return (rad * 180 / Math.PI);
	}

	private String getLastUpdated(Date updatedOn)
	{
		String lastSeen = "";

		if (null != updatedOn)
		{
			DateTime updatedOnDateTime = new DateTime(updatedOn);
			Interval interval = new Interval(updatedOnDateTime, new Instant());
			Period period = interval.toPeriod();
			if (period.getYears() > 0)
			{
				lastSeen = lastSeen + period.getYears() + " Years ";
			}

			if (period.getMonths() > 0)
			{
				lastSeen = lastSeen + period.getMonths() + " Months ";
			}

			if (period.getDays() > 0)
			{
				lastSeen = lastSeen + period.getDays() + " Days ";
			}

			if (period.getHours() > 0)
			{
				lastSeen = lastSeen + period.getHours() + " Hours ";
			}

			if (period.getMinutes() > 0)
			{
				lastSeen = lastSeen + period.getMinutes() + " Minutes ";
			}

			if (period.getSeconds() > 0)
			{
				lastSeen = lastSeen + period.getSeconds() + " Seconds ";
			}
		}

		return Constants.LAST_SEEN + lastSeen + "ago";
	}
	
	private String getDistance(String distance)
	{
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		double number = Double.valueOf(distance);
		distance = df.format(number);

		return distance + " Km Away";
	}
}