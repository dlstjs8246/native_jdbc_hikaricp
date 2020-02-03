package native_jdbc_hikaricp;

import java.sql.Connection;
import java.sql.SQLException;

import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dao.impl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.DataSourceForC3P0;
import native_jdbc_hikaricp.ds.DataSourceForDBCP;
import native_jdbc_hikaricp.ds.DataSourceForHikari;
import native_jdbc_hikaricp.ds.MySqlDataSource;

public class HiKariCP_Main {
	public static void main(String[] args) {
		//sington 쓰는 이유
		/*
		 * for(int i=0;i<100;i++) { EmployeeDao dao1 = new EmployeeDaoImpl();
		 * System.out.println(dao1); }
		 */
//		connectionToHikary();
//		connectionToDBCP();
//		connectionToC3P0();
	}

	private static void connectionToC3P0() {
		try(Connection con = DataSourceForC3P0.getConnetion()) {
			System.out.println(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void connectionToDBCP() {
		try(Connection con = DataSourceForDBCP.getConnection()) {
			System.out.println(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void connectionToHikary() {
		try(Connection con = DataSourceForHikari.getConnection(); Connection con2 = MySqlDataSource.getConnection();) {
			System.out.println(con);
			System.out.println(con2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
