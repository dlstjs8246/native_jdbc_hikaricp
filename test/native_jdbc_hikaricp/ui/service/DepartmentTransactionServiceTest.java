package native_jdbc_hikaricp.ui.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_hikaricp.LogUtil;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentTransactionServiceTest {
	private static DepartmentTransactionService service;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = new DepartmentTransactionService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = null;
	}

	@Before
	public void setUp() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
	}

	@After
	public void tearDown() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
	}

	@Test
	public void test01TransAddEmpAndDept_DeptFail() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(1,"마케팅",8);
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, dept);
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test02TransAddEmpAndDept_EmpFail() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5,"마케팅",8);
		Employee emp = new Employee(4377, "서현진", "사원", new Employee(1003), 1500000, dept);
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test03TransAddEmpAndDept_Success() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5,"마케팅",8);
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, dept);
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	@Test
	public void test04TransRemoveEmpAndDept_EmpFail() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(0000);
		Department dept = new Department(5);
		res = service.transRemoveAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test05TransRemoveEmpAndDept_DeptFail() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1004);
		Department dept = new Department(0);
		res = service.transRemoveAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test06TransRemoveEmpAndDept_Success() {
		int res = 0;
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1004);
		Department dept = new Department(5);
		res = service.transRemoveAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	@Test
	public void test07TransAddEmpDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(1,"마케팅2",8);
		Employee emp = new Employee(1006, "이유영", "사원", new Employee(1003), 1500000, dept);
		service.transAddEmpDeptWithConnection(emp, dept);
	}
	@Test
	public void test08TransAddEmpDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(6,"마케팅2",8);
		Employee emp = new Employee(4377, "이유영", "사원", new Employee(1003), 1500000, dept);
		service.transAddEmpDeptWithConnection(emp, dept);
	}
	@Test
	public void test09TransAddEmpDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(6,"마케팅2",8);
		Employee emp = new Employee(1006, "이유영", "사원", new Employee(1003), 1500000, dept);
		service.transAddEmpDeptWithConnection(emp, dept);
	}
	@Test
	public void test10TransAddEmpDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(0000);
		Department dept = new Department(6);
		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}
	@Test
	public void test11TransAddEmpDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1006);
		Department dept = new Department(0);
		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}
	@Test
	public void test12TransAddEmpDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Employee emp = new Employee(1006);
		Department dept = new Department(6);
		service.transAddEmpDeptWithConnection(emp, dept);
	}
}
