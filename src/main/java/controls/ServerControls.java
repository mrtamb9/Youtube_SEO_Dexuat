package controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.mysql.ConnectionPool;

public class ServerControls 
{
	public ArrayList<String> listIps;
	public ArrayList<String> listUserNames;
	public ArrayList<String> listPassword;
	
	public void setStatus(String ip, int running) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "UPDATE account_dexuat SET running = "
					+ running
					+ " WHERE ip = '"
					+ ip
					+ "';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public void insertAccount(String ip, String username, String password) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "INSERT INTO account_dexuat (ip, username, password, running, mylog) VALUES ('"
					+ ip
					+ "', '"
					+ username
					+ "', '"
					+ password
					+ "', 0, 'no log') ON DUPLICATE KEY UPDATE username='"
					+ username
					+ "', password='"
					+ password
					+ "', running=0, mylog='log';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public void getAllAccountFromMySQL() throws Exception
	{
		listIps = new ArrayList<>();
		listUserNames = new ArrayList<>();
		listPassword = new ArrayList<>();
		
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "SELECT * FROM account_dexuat;";
			resultset = statement.executeQuery(query);
			while(resultset.next())
			{
				String ip = resultset.getString("ip");
				String username = resultset.getString("username").trim();
				String password = resultset.getString("password").trim();
				listIps.add(ip);
				listUserNames.add(username);
				listPassword.add(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public void deleteAccountInMySQL(String ip) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "DELETE FROM account_dexuat WHERE ip = '"
					+ ip
					+ "';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public static void main(String [] args) throws Exception
	{
		String ip = "1.1.1.1";
		String username = "mrtamb9";
		String password = "Tambk1209";
		// insertAccount(ip, username, password);
		System.out.println("All done!");
	}
}
