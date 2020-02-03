package native_jdbc_hikaricp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javax.swing.JOptionPane;

import native_jdbc_hikaricp.ds.DataSourceForC3P0;
import native_jdbc_hikaricp.ds.DataSourceForDBCP;
import native_jdbc_hikaricp.ds.DataSourceForHikari2;
import native_jdbc_hikaricp.dto.Department;

public class DepartmentMain {

	private static ArrayList<Department> deptArr;

	public static void main(String[] args) {
		deptArr = new ArrayList<>();
//		hikaryConnect();
//		dbcpConnect();
//		c3p0Connect();
	}

	private static void c3p0Connect() {
		try(Connection con = DataSourceForC3P0.getConnetion()) {
			sqlSelect(con);
			sqlInsert(con);
			sqlUpdate(con);
			sqlDelete(con);
			sqlSelect(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void dbcpConnect() {
		try(Connection con = DataSourceForDBCP.getConnection()) {
			sqlSelect(con);
			sqlInsert(con);
			sqlUpdate(con);
			sqlDelete(con);
			sqlSelect(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void hikaryConnect() {
		try(Connection con = DataSourceForHikari2.getConnection()) {
			sqlSelect(con);
			sqlInsert(con);
			sqlUpdate(con);
			sqlDelete(con);
			sqlSelect(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	private static void sqlDelete(Connection con) {
		String sql = "delete from department where deptno = ?";
		try(PreparedStatement pStmt = con.prepareStatement(sql)) {
			 pStmt.setInt(1,5);
			 pStmt.executeUpdate();
		} catch (SQLException e) {
			if(e.getErrorCode()==1064) {
				JOptionPane.showMessageDialog(null, "해당 부서가 이미 존재함");
			}
			System.out.println(e.getMessage());
		}
		
	}

	private static void sqlUpdate(Connection con) {
		String sql = "update department set deptname = ? , floor = ? where deptno = ?";
		try(PreparedStatement pStmt = con.prepareStatement(sql)) {
			 pStmt.setString(1,"마케팅");
			 pStmt.setInt(2,10);
			 pStmt.setInt(3,5);
			 pStmt.executeUpdate();
		} catch (SQLException e) {
			if(e.getErrorCode()==1064) {
				JOptionPane.showMessageDialog(null, "해당 부서가 이미 존재함");
			}
			System.out.println(e.getMessage());
		}
		
	}

	private static void sqlInsert(Connection con) {
		String sql = "insert into department(deptno,deptname,floor) values(?,?,?)";
		try(PreparedStatement pStmt = con.prepareStatement(sql)) {
			 pStmt.setInt(1,5);
			 pStmt.setString(2, "솔루션");
			 pStmt.setInt(3, 30);
			 pStmt.executeUpdate();
		} catch (SQLException e) {
			if(e.getErrorCode()==1064) {
				JOptionPane.showMessageDialog(null, "해당 부서가 이미 존재함");
			}
			System.out.println(e.getMessage());
		}
	}

	private static void sqlSelect(Connection con) {
		String sql = "select deptno,deptname,floor from department";
		try(Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while(rs.next()) {
				deptArr.add(getDepartment(rs));
			}
			for(Department d : deptArr) {
				System.out.println(d);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo,deptName,floor);
	}
}
