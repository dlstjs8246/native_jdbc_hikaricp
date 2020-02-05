package native_jdbc_hikaricp.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;

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

import native_jdbc_hikaricp.dao.impl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private static Logger logger = LogManager.getLogger();
	private static Connection con;
	private static EmployeeDao dao;
	private static File imagesDir;
	private static File picsDir;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass()");
		dao = EmployeeDaoImpl.getInstance();
		imagesDir = new File(System.getProperty("user.dir")+ File.separator + "images" + File.separator);
		picsDir = new File(System.getProperty("user.dir") + File.separator + "pics" + File.separator);
		if(!picsDir.exists()) {
			picsDir.mkdir();
		}
		
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
	public void test01SelectEmpolyeeByEmpNo() throws SQLException {
		logger.debug("test01SelectEmpolyeeByEmpNo()");
		Employee emp = new Employee(1004);
		Employee selEmp = dao.selectEmpolyeeByEmpno(con, emp);
		if(selEmp.getPic()!=null) {
			getImageToPic(selEmp.getPic(),selEmp.getEmpNo());
		}
		Assert.assertNotNull(selEmp);
		logger.trace(selEmp);
	}

	private void getImageToPic(byte[] pic, int empNo) {
		File file = new File(picsDir, empNo + ".jpg");
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(pic);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test02SelectEmployeeByAll() throws SQLException {
		logger.debug("test02SelectEmployeeByAll()");
		List<Employee> lists = dao.selectEmployeeByAll(con);
		Assert.assertNotEquals(0, lists.size());
		logger.trace(lists);
	}

	@Test
	public void test03SelectEmployeeGroupByDno() {
		logger.debug("test03SelectEmployeeGroupByDno()");
		Department dept = new Department();
		dept.setDeptNo(3);
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, lists.size());
			for(Employee e : lists) {
				logger.trace(e);
			}
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
	}

	@Test
	public void test04InsertEmployee() {
		logger.debug("test04InsertEmployee()");
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, new Department(1), getImage("seohyunjin.jpg"));
		logger.debug(emp);
		int res = dao.insertEmployee(con, emp);
		Assert.assertEquals(1, res);
		Employee emp2 = new Employee(1005,"수지","사원",new Employee(4377),1500000,new Department(2),getImage("suji.jpg"));
		res = dao.insertEmployee(con, emp2);
		Assert.assertEquals(1, res);
	}

	private byte[] getImage(String imgName) {
		File file = new File(imagesDir,imgName);
//		logger.debug(file.getAbsolutePath());
		try(InputStream is = new FileInputStream(file)) {
			byte[] pic = new byte[is.available()];
			is.read(pic);
			return pic;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test05UpdateEmployee() {
		logger.debug("test05UpdateEmployee()");
		Employee emp = new Employee(1004,"이유영","대리",new Employee(3426),3500000,new Department(1));
		emp.setPic(getImage("lyy.jpg"));
		int res = dao.updateEmployee(con, emp);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test06DeleteEmployee() {
		logger.debug("test06DeleteEmployee()");
		Employee emp = new Employee(1004);
		int res = dao.deleteEmployee(con, emp);
		Assert.assertEquals(1, res);
	}

}
