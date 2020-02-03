package native_jdbc_hikaricp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl();
	
	private DepartmentDaoImpl() {}
	
	
	public static DepartmentDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Department> selectDepartmentByAll(Connection con) throws SQLException {
		String sql = "select deptno,deptname,floor from department";
		List<Department> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()) {
				list.add(getDepartment(rs));
			}
		}
		return list;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}


	@Override
	public int insertDepartment(Connection con, Department dept) throws SQLException {
		String sql = "insert into department values(?,?,?)";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			return pstmt.executeUpdate();
		}
	}


	@Override
	public int updateDepartment(Connection con, Department dept) throws SQLException {
		String sql = "update department set deptname = ?, floor = ? where deptno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, dept.getDeptName());
			pstmt.setInt(2, dept.getFloor());
			pstmt.setInt(3, dept.getDeptNo());
			return pstmt.executeUpdate();
		}
	}


	@Override
	public int deleteDepartment(Connection con, Department dept) throws SQLException {
		String sql = "delete from department where deptno = ?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, dept.getDeptNo());
			return pstmt.executeUpdate();
		}
	}

}
