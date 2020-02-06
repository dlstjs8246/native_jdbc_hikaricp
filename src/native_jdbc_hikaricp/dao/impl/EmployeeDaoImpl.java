package native_jdbc_hikaricp.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	private static Logger logger = LogManager.getLogger();
	private EmployeeDaoImpl() {}
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmpolyeeByEmpno(Connection con, Employee emp) throws SQLException {
		String sql = "select * from employee where empno = ?";
		Employee selEmp = new Employee();
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					selEmp = getEmployeeAll(rs);
				}
				return selEmp;
			}
		}
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		String sql = "select empno,empname,title,manager,salary,dno,pic from employee";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			logger.trace(pstmt);
			while(rs.next()) {
				list.add(getEmployeeAll(rs));
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
	private Employee getEmployeeAll(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department(rs.getInt("dno"));
		byte[] pic = rs.getBytes("pic");
		return new Employee(empNo, empName, title, manager, salary, dept, pic);
		
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

	@Override
	public int insertEmployee(Connection con, Employee emp) {
		String sql = null;
		if(emp.getPic()==null) {
			sql = "insert into employee(empno,empname,title,manager,salary,dno) values(?,?,?,?,?,?)";
		}
		else {
			sql = "insert into employee values(?,?,?,?,?,?,?)";
		}
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getTitle());
			pstmt.setInt(4, emp.getManager().getEmpNo());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			if(emp.getPic()!=null) {
				pstmt.setBytes(7, emp.getPic());
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public int updateEmployee(Connection con, Employee emp) {
		String sql = "update employee set empname = ?, title = ?, manager = ?, salary = ?, dno = ?, pic= ? where empno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getTitle());
			pstmt.setInt(3, emp.getManager().getEmpNo());
			pstmt.setInt(4, emp.getSalary());
			pstmt.setInt(5, emp.getDept().getDeptNo());
			pstmt.setBytes(6, emp.getPic());
			pstmt.setInt(7, emp.getEmpNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteEmployee(Connection con, Employee emp) {
		String sql = "delete from employee where empno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getEmpNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Employee> procedureEmployeeByDeptNo(Connection con, int deptNo) {
		List<Employee> list = new ArrayList<Employee>();
		String sql = "{call procedure_01(?)}";
		try(CallableStatement cs = con.prepareCall(sql)) {
			cs.setInt(1, deptNo);
			cs.executeUpdate();
			try(ResultSet rs = cs.executeQuery()) {
				while(rs.next()) {
					list.add(getEmployeeAll(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
