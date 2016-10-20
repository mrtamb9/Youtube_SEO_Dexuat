package parameter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import database.mysql.ConnectionPool;
import utils.Utils;

public class Parameters {
	public static String file_link = "links.txt";
	public static String file_info = "info.txt";
	public static String file_hours = "hours.txt";
	public static String file_driver = "geckodriver/chromedriver.exe";

	public static DateFormat dfinput = new SimpleDateFormat("dd-MM-yyyy");
	public static DateFormat dfoutput = new SimpleDateFormat("MMM dd, yyyy");
	public static String username = "";
	public static String password = "";
	
	public static void getAccountFromMySQL() throws Exception
	{
		String myIp = Utils.getIp();
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
				if(status.compareTo("1")==0)
				{
					String value = resultset.getString("value");
					if(value.split(" ").length==2)
					{
						username = value.split(" ")[0];
						password = value.split(" ")[1];
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionPool.closeConnection(resultset, statement, connect);
	}
	
	public static void main(String [] args) throws Exception
	{
		getAccountFromMySQL();
		System.out.println("username: " + username);
		System.out.println("password: " + password);
	}
}
