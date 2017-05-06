package com.seeuaround.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpVersion;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.seeuaround.exception.ServiceException;

public class CommonUtil
{
	private static final Logger log = Logger.getLogger(CommonUtil.class);

	public static String convertToJSONMessage(String result)
			throws ServiceException
	{
		log.debug("In Method convertToJSONMessage()");

		JSONObject obj = new JSONObject();

		try
		{
			obj.put("result", result);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return obj.toString();
	}

	public static Date convertStringToDate(String dateString)
			throws ServiceException
	{
		log.debug("In Method convertStringToDate()");

		Date date = null;
		String finlaString = dateString;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		try
		{
			date = sdf.parse(finlaString);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return date;
	}
	
	public static String convertDateToString(Date date)
			throws ServiceException
	{
		log.debug("In Method formatDateToString()");

		String dateString = "";

		try
		{
			dateString = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(date);
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return dateString;
	}

	public static JSONObject readJSON(HttpServletRequest request)
			throws ServiceException
	{
		log.debug("In Method readJSON()");

		BufferedReader reader = null;
		JSONObject jsonObj = null;

		try
		{
			StringBuilder sb = new StringBuilder();
			reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line).append('\n');
			}

			jsonObj = new JSONObject(sb.toString());
		}
		catch (IOException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}
		catch (JSONException exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException exception)
			{
				throw new ServiceException(exception.getMessage(), exception);
			}
		}

		return jsonObj;
	}

	public static boolean validPhoneNumber(String phoneNumber)
			throws ServiceException
	{
		log.debug("In Method validPhoneNumber()");

		if (null != phoneNumber && !"".equals(phoneNumber)
				&& phoneNumber.length() == 10)
		{
			if (phoneNumber.matches("\\d{10}")) // to verify that characters
												// entered are only digits
			{
				return true;
			}
		}

		return false;
	}

	public static boolean callSMSServer(String smsMessage,
			List<String> phoneNumbers) throws ServiceException
	{
		log.debug("In Method callSMSServer()");

		DefaultHttpClient httpclient = null; 
		
		try
		{
			List<String> finalUrl = prepareSMSUrl(smsMessage, phoneNumbers);

			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
					HttpVersion.HTTP_1_1);
			httpclient = new DefaultHttpClient(params);

			for (String url : finalUrl)
			{
//				sendGet(url);
			}

			return true;
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}
		finally
		{
			httpclient.close();
		}
	}

	// HTTP GET request
	private static void sendGet(String url) throws Exception
	{
		try
		{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			// con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			log.info("Sending 'GET' request to URL : " + url);
			log.info("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();

			// print result
			log.info(response.toString());
		}
		catch (Exception exception)
		{
			log.info("Problem while sending SMS: " + exception);
			throw new ServiceException(exception.getMessage(), exception);
		}

	}

	private static List<String> prepareSMSUrl(String smsMessage,
			List phoneNumbers) throws ServiceException
	{
		log.debug("In Method prepareSMSUrl()");

		List<String> urls = new ArrayList<String>();

		try
		{
			String smsUrl = ConfigurationReader.configurationBean.getSmsURL();
			String smsUser = "&username="
					+ ConfigurationReader.configurationBean.getSmsUser();
			String smsPassword = "&password="
					+ ConfigurationReader.configurationBean.getSmsPassword();

			String tempStr = smsUrl + smsUser + smsPassword + "&message="
					+ URLEncoder.encode(smsMessage, "UTF-8");

			for (Object phoneNumber : phoneNumbers)
			{
				String finalUrl = tempStr + "&to=" + phoneNumber;

				urls.add(finalUrl);
			}
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return urls;
	}

	public static String generateFourDigitRandomNumber()
			throws ServiceException
	{
		log.debug("In Method generateFourDigitRandomNumber()");

		String randomNumber = "";

		try
		{
			List<Integer> numbers = new ArrayList();
			for (int i = 0; i < 10; i++)
			{
				numbers.add(i);
			}

			Collections.shuffle(numbers);

			for (int i = 0; i < 4; i++)
			{
				randomNumber += numbers.get(i).toString();
			}
		}
		catch (Exception exception)
		{
			throw new ServiceException(exception.getMessage(), exception);
		}

		return randomNumber;
	}
}
