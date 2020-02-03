package native_jdbc_hikaricp.ds;

import java.sql.*;
import com.zaxxer.hikari.*;

public class DataSourceForHikari {
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	
	static {
		config.setJdbcUrl("jdbc:mysql://localhost/mysql_study?useSSL=false");
		config.setUsername("user_mysql_study");
		config.setPassword("rootroot");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSqllimit", "2048");
		ds = new HikariDataSource(config);
	}
	private DataSourceForHikari() {};
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}