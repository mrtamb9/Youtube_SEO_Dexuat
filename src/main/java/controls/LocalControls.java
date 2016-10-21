package controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.mysql.ConnectionPool;
import utils.Utils;

public class LocalControls {
	
	String myIp;
	
	public LocalControls() {
		try {
			myIp = Utils.getIp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LocalControls(String myIp) {
		this.myIp = myIp;
	}
	
	public void saveLog(String log) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "UPDATE account_dexuat SET mylog = '"
					+ log
					+ "' WHERE ip = '"
					+ myIp
					+ "';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public void setStatus(int running) throws Exception
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
					+ myIp
					+ "';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public boolean checkStop() throws Exception
	{
		boolean check = true;
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "SELECT * FROM account_dexuat WHERE ip = \'" + myIp + "\';";
			resultset = statement.executeQuery(query);
			if(resultset.next())
			{
				String running = resultset.getString("running");
				if(running!=null)
				{
					if(running.compareTo("1")==0)
					{
						check = false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
		
		return check;
	}
	
	public static void main(String [] args) throws Exception
	{
		LocalControls control = new LocalControls("0.0.0.0");
		control.setStatus(0);
	}
}
