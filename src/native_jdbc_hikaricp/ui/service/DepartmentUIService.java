package native_jdbc_hikaricp.ui.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dao.impl.DepartmentDaoImpl;
import native_jdbc_hikaricp.dao.impl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class DepartmentUIService {
	private Connection con;
	private DepartmentDao deptDao;
	private EmployeeDao empDao;
	public DepartmentUIService() {
		try {
			con = MySqlDataSource.getConnection();
			deptDao = DepartmentDaoImpl.getInstance();
			empDao = EmployeeDaoImpl.getInstance();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "접속 정보 확인");
		}
		
	}
	
	public List<Department> showDepartments() throws SQLException{
		return deptDao.selectDepartmentByAll(con);
	}
	public List<Employee> showEmployees() throws SQLException {
		return empDao.selectEmployeeByAll(con);
	}
	public List<Employee> showGroupByDnoEmployees(int deptNo) throws SQLException {
		Department dept = new Department();
		dept.setDeptNo(deptNo);
		return empDao.selectEmployeeGroupByDno(con, dept);
	}
	public void insertEmployee(Department dept) throws SQLException {
		deptDao.insertDepartment(con,dept);	
	}
	public void updateEmployee(Department dept) throws SQLException {
		deptDao.updateDepartment(con,dept);	
	}
	public void deleteEmployee(Department dept) throws SQLException {
		deptDao.deleteDepartment(con, dept);
	}
}
