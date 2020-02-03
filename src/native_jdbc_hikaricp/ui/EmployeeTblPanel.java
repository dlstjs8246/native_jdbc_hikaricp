package native_jdbc_hikaricp.ui;

import javax.swing.SwingConstants;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.ui.panel.AbsTblPanel;

@SuppressWarnings("serial")
public class EmployeeTblPanel extends AbsTblPanel<Employee> {

	@Override
	protected void setTblWidthAlign() {
		tableSetWidth(100,150,50,150,150,100);
		tableCellAlign(SwingConstants.CENTER,0,1,2,3,5);
		tableCellAlign(SwingConstants.RIGHT,4);
		
	}

	@Override
	protected String[] getColumns() {
		return new String[] {"사원번호","사원명","직책","직속상사","급여","부서"};
	}

	@Override
	protected Object[] toArray(Employee item) {
		String manager = String.format("%s(%d)", item.getManager().getEmpName(),item.getManager().getEmpNo());
		return new Object[] {item.getEmpNo(),item.getEmpName(),item.getTitle(),manager,String.format("%,d", item.getSalary()),String.format("%s(%d)",item.getDept().getDeptName(),item.getDept().getDeptNo())};
	}

	@Override
	public void updateRow(Employee item, int updateIdx) {
		model.setValueAt(item.getEmpNo(),updateIdx,0);
		model.setValueAt(item.getEmpName(),updateIdx,1);
		model.setValueAt(item.getTitle(),updateIdx,2);
		model.setValueAt(item.getManager(),updateIdx, 3);
		model.setValueAt(item.getSalary(), updateIdx, 4);
		model.setValueAt(item.getDept(), updateIdx, 5);
	}

	@Override
	public Employee getSelectedItem() {
		int selectedIdx = getSelectedRowIdx();
		int empNo = (int)model.getValueAt(selectedIdx, 0);
		String empName = (String)model.getValueAt(selectedIdx, 1);
		String title = (String)model.getValueAt(selectedIdx, 2);
		Employee manager = (Employee)model.getValueAt(selectedIdx, 3);
		int salary = (int)model.getValueAt(selectedIdx, 4);
		Department dept = (Department)model.getValueAt(selectedIdx, 5);
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

}
