package controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.mysql.ConnectionPool;
import utils.Utils;

public class MyControls {
	
	String myIp;
	
	public MyControls() {
		try {
			myIp = Utils.getIp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MyControls(String myIp) {
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
					+ "' WHERE id = '"
					+ myIp
					+ "';";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public void setStatus(int status) throws Exception
	{
		Connection connect = null;
		Statement statement = null;
		ResultSet resultset = null;
		connect = ConnectionPool.getConnection();
		statement = connect.createStatement();

		try {
			String query = "UPDATE account_dexuat SET status = "
					+ status
					+ " WHERE id = '"
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
			String query = "SELECT * FROM account_dexuat WHERE id = \'" + myIp + "\';";
			resultset = statement.executeQuery(query);
			if(resultset.next())
			{
				String status = resultset.getString("status");
				if(status!=null)
				{
					if(status.compareTo("1")==0)
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
		// MyControls log = new MyControls("14.162.206.226");
		MyControls log = new MyControls("0.0.0.0");
		log.setStatus(0);
	}
}
