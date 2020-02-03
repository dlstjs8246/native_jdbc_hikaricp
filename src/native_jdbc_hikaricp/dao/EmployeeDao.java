package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public interface EmployeeDao {
	Employee selectEmpolyeeByDno(Connection con, Department dept) throws SQLException;
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
	List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException;
}
