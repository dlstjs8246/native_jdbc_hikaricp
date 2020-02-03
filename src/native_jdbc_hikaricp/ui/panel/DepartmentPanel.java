package native_jdbc_hikaricp.ui.panel;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import native_jdbc_hikaricp.dto.Department;

@SuppressWarnings("serial")
public class DepartmentPanel extends AbsItemPanel<Department> {
	private JLabel lblDeptNo;
	private JTextField tfDeptNo;
	private JLabel lblDeptName;
	private JTextField tfDeptName;
	private JLabel lblFloor;
	private JSpinner sFloor;

	/**
	 * Create the panel.
	 */
	public DepartmentPanel() {
		
		initialize();
	}
	private void initialize() {
		setLayout(new GridLayout(0, 2, 0, 0));
		
		lblDeptNo = new JLabel("부서번호");
		lblDeptNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptNo);
		
		tfDeptNo = new JTextField();
		tfDeptNo.setColumns(10);
		add(tfDeptNo);
		
		lblDeptName = new JLabel("부서이름");
		lblDeptName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptName);
		
		tfDeptName = new JTextField();
		tfDeptName.setColumns(10);
		add(tfDeptName);
		
		lblFloor = new JLabel("층");
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblFloor);
		
		sFloor = new JSpinner();
		add(sFloor);
	}

	@Override
	public Department getItem() {
		int deptNo = Integer.parseInt(tfDeptNo.getText().trim());
		String deptName = tfDeptName.getText().trim();
		int floor = (int)sFloor.getValue();
		return new Department(deptNo, deptName, floor);
	}

	@Override
	public void setItem(Department item) {
		tfDeptNo.setText(item.getDeptNo() + " ");
		tfDeptName.setText(item.getDeptName());
		sFloor.setValue(item.getFloor());
	}

	@Override
	public void tfClear() {
		tfDeptNo.setText("");
		tfDeptName.setText("");
		sFloor.setValue(0);
	}

}
