package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_hikaricp.dao.impl.DepartmentDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static Logger logger = LogManager.getLogger();
	private static Connection con;
	private static DepartmentDao dao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass()");
		dao = DepartmentDaoImpl.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug("tearDownAfterClass()");
		dao = null;
		con.close();
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("setUp()");
		con = MySqlDataSource.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("tearDown()");
	}

	@Test
	public void test01SelectDepartmentByAll() {
		logger.debug("test01SelectDepartmentByAll()");
		try {
			List<Department> list = dao.selectDepartmentByAll(con);
			Assert.assertNotEquals(-1, list.size());
			for(Department d : list) {
				logger.trace(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test02InsertDepartment() throws SQLException {
		logger.debug("test02InsertDepartment()");
		Department dept = new Department(5,"마케팅",10);
		int res = dao.insertDepartment(con, dept);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test03UpdateDepartment() throws SQLException {
		logger.debug("test03UpdateDepartment()");
		Department dept = new Department(5,"마케팅3",41);
		int res = dao.updateDepartment(con, dept);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test04DeleteDepartment() throws SQLException {
		logger.debug("test04DeleteDepartment()");
		Department dept = new Department(5);
		int res = dao.deleteDepartment(con, dept);
		Assert.assertEquals(1, res);
	}
	@Test
	public void test05selectDepartmentByDeptNo() throws SQLException {
		logger.debug("test05selectDepartmentByDeptNo()");
		Department dept = dao.selectDepartmentByDeptNo(con, 1);
		Assert.assertNotNull(dept);
		logger.trace(dept);
	}

}
