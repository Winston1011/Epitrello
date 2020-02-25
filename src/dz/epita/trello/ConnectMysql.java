package dz.epita.trello;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectMysql {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/epitrello?useSSL=false&serverTimezone=UTC";
 
    static final String USER = "root";
    static final String PASSWORD = "123456";

    public Connection getConn() throws Exception {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
		return conn;
	}
      
    public void close(Connection conn) throws Exception{
    	         if(conn!=null){
    	             conn.close();
    	         }    
    }
    
}
