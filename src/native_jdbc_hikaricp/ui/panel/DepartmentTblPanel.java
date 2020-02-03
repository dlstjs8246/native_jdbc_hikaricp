package native_jdbc_hikaricp.ui.panel;

import javax.swing.SwingConstants;

import native_jdbc_hikaricp.dto.Department;

@SuppressWarnings("serial")
public class DepartmentTblPanel extends AbsTblPanel<Department> {

	/**
	 * Create the panel.
	 */
	public DepartmentTblPanel() {

	}

	@Override
	protected void setTblWidthAlign() {
		tableSetWidth(150,100,100);
		tableCellAlign(SwingConstants.CENTER,0,1,2);
	}

	@Override
	protected String[] getColumns() {
		return new String[] {"부서번호","부서이름","층"};
	}

	@Override
	protected Object[] toArray(Department item) {
		return new Object[] {item.getDeptNo(),item.getDeptName(),item.getFloor()};
	}

	@Override
	public void updateRow(Department item, int updateIdx) {
		model.setValueAt(item.getDeptNo(),updateIdx, 0);
		model.setValueAt(item.getDeptName(),updateIdx, 1);
		model.setValueAt(item.getFloor(),updateIdx, 2);
	}

	@Override
	public Department getSelectedItem() {
		int selectedIndex = getSelectedRowIdx();
		int deptNo = (int)model.getValueAt(selectedIndex, 0);
		String deptName = (String)model.getValueAt(selectedIndex, 1);
		int floor = (int)model.getValueAt(selectedIndex, 2);
		return new Department(deptNo, deptName, floor);
	}

}
