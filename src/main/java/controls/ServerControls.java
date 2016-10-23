package controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.mysql.ConnectionPool;
import utils.Utils;

public class ServerControls {
	public ArrayList<String> listIps;
	public ArrayList<String> listUserNames;
	public ArrayList<String> listPassword;
	public ArrayList<String> listStatus;
	public ArrayList<String> listMyLogs;
	public ArrayList<String> listInfos;
	public ArrayList<Date> listUpdateTimes;
	public ArrayList<String> listWarning;
	DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setStatus(String ip, int running) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "";
			if (ip.compareTo("0.0.0.0") == 0 || ip.compareTo("0") == 0) {
				query = "UPDATE account_dexuat SET running = " + running + " WHERE ip LIKE '%';";
			} else {
				query = "UPDATE account_dexuat SET running = " + running + " WHERE ip = '" + ip + "';";
			}
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}

	public void insertAccount(String ip, String username, String password) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "INSERT INTO account_dexuat (ip, username, password, running, mylog) VALUES ('" + ip + "', '"
					+ username + "', '" + password + "', 0, 'no log') ON DUPLICATE KEY UPDATE username='" + username
					+ "', password='" + password + "', running=0, mylog='log';";
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}

	public void getAllAccountFromMySQL() throws Exception {
		Date outOfTime = Utils.getOutOfTimeFromNow();
		listIps = new ArrayList<>();
		listUserNames = new ArrayList<>();
		listPassword = new ArrayList<>();
		listStatus = new ArrayList<>();
		listMyLogs = new ArrayList<>();
		listInfos = new ArrayList<>();
		listUpdateTimes = new ArrayList<>();
		listWarning = new ArrayList<>();

		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "SELECT * FROM account_dexuat;";
			System.out.println(query);
			resultset = statement.executeQuery(query);
			while (resultset.next()) {
				String ip = resultset.getString("ip");
				String username = resultset.getString("username").trim();
				String password = resultset.getString("password").trim();
				String status = resultset.getString("running");
				String mylog = resultset.getString("mylog").trim();
				String updateTimeString = resultset.getString("update_time");
				Date updateTime = null;
				if (updateTimeString != null) {
					updateTime = simpleDateFormat.parse(updateTimeString);
				}

				listIps.add(ip);
				listUserNames.add(username);
				listPassword.add(password);
				listStatus.add(status);
				listMyLogs.add(mylog);
				listUpdateTimes.add(updateTime);

				String tempString = ip + "     " + username + "     " + status + "     " + mylog;
				listInfos.add(tempString);
				if (updateTime == null || updateTime.before(outOfTime)) {
					listWarning.add(tempString);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// sort list info
		listInfos = Utils.sortArrayString(listInfos);

		ConnectionPool.closeConnection(resultset, statement, connect);
	}

	public void deleteAccountInMySQL(String ip) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "DELETE FROM account_dexuat WHERE ip = '" + ip + "';";
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}

	public void updateParameters(String min_time_second, String max_time_second, String target_videos,
			String other_videos, String comments) throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "UPDATE parameters_dexuat SET value = CASE id WHEN 'min_time_second' THEN "
					+ Integer.parseInt(min_time_second.trim()) + " WHEN 'max_time_second' THEN "
					+ Integer.parseInt(max_time_second.trim()) + " WHEN 'other_videos' THEN '" + other_videos.trim()
					+ "' WHEN 'target_videos' THEN '" + target_videos.trim() + "' WHEN 'comments' THEN '"
					+ comments.trim()
					+ "' END WHERE id IN('max_time_second', 'min_time_second', 'other_videos', 'target_videos', 'comments');";
			System.out.println(query);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public String [] getParameterFromMySQL() throws Exception
	{
		ArrayList<String> listParameters = new ArrayList<>();
		
		int max_time_second = -1;
		int min_time_second = -1;
		ArrayList<String> listComments = new ArrayList<>();
		ArrayList<String> listTargetVideos = new ArrayList<>();
		ArrayList<String> listOtherVideos = new ArrayList<>();
		
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();
		
		try {
			String query = "SELECT * FROM parameters_dexuat;";
			System.out.println(query);
			resultset = statement.executeQuery(query);
			while(resultset.next())
			{
				String id = resultset.getString("id");
				String value = resultset.getString("value");
				if(id.compareTo("max_time_second")==0)
				{
					max_time_second = Integer.parseInt(value.trim());
				} else if(id.compareTo("min_time_second")==0) {
					min_time_second = Integer.parseInt(value.trim());
				} else if(id.compareTo("comments")==0) {
					String [] arrayComments = value.split(",");
					for(int i=0; i<arrayComments.length; i++)
					{
						listComments.add(arrayComments[i].trim());
					}
				} else if(id.compareTo("other_videos")==0) {
					String [] arrayVideos = value.split(",");
					for(int i=0; i<arrayVideos.length; i++)
					{
						listOtherVideos.add(arrayVideos[i].trim());
					}
				} else if(id.compareTo("target_videos")==0) {
					String [] arrayVideos = value.split(",");
					for(int i=0; i<arrayVideos.length; i++)
					{
						listTargetVideos.add(arrayVideos[i].trim());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
		
		listParameters.add("Time Watch Video (min) = " + min_time_second + " (s)");
		listParameters.add("");
		
		listParameters.add("Time Watch Video (max) = " + max_time_second + " (s)");
		listParameters.add("");
		
		listParameters.add("My Videos:");
		listParameters.add("");
		for(int i=0; i<listTargetVideos.size(); i++)
		{
			listParameters.add("     " + (i+1) + ". " + listTargetVideos.get(i));
		}
		
		listParameters.add("");
		listParameters.add("Other Videos:");
		for(int i=0; i<listOtherVideos.size(); i++)
		{
			listParameters.add("     " + (i+1) + ". " + listOtherVideos.get(i));
		}
		
		listParameters.add("");
		listParameters.add("Comments:");
		for(int i=0; i<listComments.size(); i++)
		{
			listParameters.add("     " + (i+1) + ". " + listComments.get(i));
		}
		
		// extract into array
		String [] arrayParameters = new String [listParameters.size()];
		for(int i=0; i<listParameters.size(); i++)
		{
			arrayParameters[i] = listParameters.get(i);
		}
		
		return arrayParameters;
	}

	public static void main(String[] args) throws Exception {
		ServerControls serverControls = new ServerControls();
		serverControls.getParameterFromMySQL();
	}
}
