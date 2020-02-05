package native_jdbc_hikaricp.ui.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import native_jdbc_hikaricp.LogUtil;
import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dao.impl.DepartmentDaoImpl;
import native_jdbc_hikaricp.dao.impl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentTransactionService {
	private String insertDeptSql = "insert into department values(?,?,?)";
	private String insertEmpSel = "insert into employee(empno,empname,title,manager,salary,dno) values(?,?,?,?,?,?)";
	private String deleteDeptSql = "delete from department where deptno = ?";
	private String deleteEmpSql = "delete from employee where empno = ?";
	public int transAddEmpAndDept(Employee emp, Department dept) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int res = 0;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insertDeptSql);
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate();
			pstmt = con.prepareStatement(insertEmpSel);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getTitle());
			pstmt.setInt(4, emp.getManager().getEmpNo());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
				LogUtil.prnLog("result " + res + " commit()");
			}
			else {
				con.rollback();
				LogUtil.prnLog(res);
				LogUtil.prnLog("result " + res + " rollback()");
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + " rollback()");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				con.setAutoCommit(true);
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	public void transAddEmpDeptWithConnection(Employee emp, Department dept){
		DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		Connection con = null;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			LogUtil.prnLog(deptDao.insertDepartment(con, dept));
			LogUtil.prnLog(empDao.insertEmployee(con, emp));
			con.commit();
			LogUtil.prnLog("result commit()");
			con.setAutoCommit(true);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}
			catch(Exception e1){};
			LogUtil.prnLog("result rollback()");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int transRemoveAndDept(Employee emp, Department dept) {
		int res = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(deleteEmpSql);
			pstmt.setInt(1, emp.getEmpNo());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate();
			LogUtil.prnLog(res);
			pstmt = con.prepareStatement(deleteDeptSql);
			pstmt.setInt(1, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate();
			LogUtil.prnLog(res);
			if(res==2) {
				LogUtil.prnLog("result " + res + " commit()");
				con.commit();
			}
			else {
				LogUtil.prnLog("result " + res + " rollback()");
				con.rollback();
			}
		} 
		catch (SQLException e) {
			try {
				LogUtil.prnLog("result " + res + " rollback()");
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				con.setAutoCommit(true);
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	public void transRemoveEmpAndDeptWithConnection(Employee emp, Department dept) {
		Connection con = null;
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		DepartmentDao deptDao = DepartmentDaoImpl.getInstance();	
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			empDao.deleteEmployee(con, emp);
			deptDao.deleteDepartment(con, dept);
			LogUtil.prnLog("result commit()");
			con.commit();
			con.setAutoCommit(true);
		} catch (RuntimeException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result rollback()");
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
