package native_jdbc_hikaricp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	
	private EmployeeDaoImpl() {}
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmpolyeeByDno(Connection con, Department dept) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		String sql = "select empno,empname,title,manager,salary,dno from employee";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
		}
		return list;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(Integer.parseInt(rs.getString("manager_name").substring(rs.getString("manager_name").indexOf("(")+1,rs.getString("manager_name").length()-1)));
		manager.setEmpName(rs.getString("manager_name").substring(0,3));
		int salary = rs.getInt("salary");
		Department dept = new Department(Integer.parseInt(rs.getString("deptname").substring(rs.getString("deptname").indexOf("(")+1, rs.getString("deptname").length()-1)));
		dept.setDeptName(rs.getString("deptname").substring(0, rs.getString("deptname").indexOf("(")));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

	@Override
	public List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException {
		String sql = "select e.empno,e.empname,e.title,(select concat(empname,'(',e.manager,')') from employee where empno = e.manager) as 'manager_name',salary,(select concat(deptname,'(',deptno,')') from department where department.deptno = e.dno) as 'deptname' from employee e where e.dno = ?";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getEmployee(rs));
				}
			}
		}
		return list;
	}

}
