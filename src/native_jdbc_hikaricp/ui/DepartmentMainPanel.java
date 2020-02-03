package native_jdbc_hikaricp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.ui.panel.AbsTblPanel;
import native_jdbc_hikaricp.ui.panel.DepartmentPanel;
import native_jdbc_hikaricp.ui.panel.DepartmentTblPanel;
import native_jdbc_hikaricp.ui.service.DepartmentUIService;

@SuppressWarnings("serial")
public class DepartmentMainPanel extends AbsMainPanel<Department> {
	private DepartmentPanel deptItemPanel;
	private DepartmentTblPanel deptTblPanel;
	private DepartmentUIService service;
	private List<Employee> list;
	private DlgEmployee dialog;
	/**
	 * Create the panel.
	 */
	public DepartmentMainPanel() {
		initialize();
	}
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		dialog = new DlgEmployee();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	@Override
	protected JPanel getItemPanel() {
		deptItemPanel = new DepartmentPanel();
		return deptItemPanel;
	}

	@Override
	protected AbsTblPanel<Department> getTblPanel() {
		deptTblPanel = new DepartmentTblPanel();
		initItemList();
		popMenu = new JPopupMenu();
		initPopMenu();
		
		deptTblPanel.setPopupMenu(popMenu);
		addBtnListener();
		return deptTblPanel;
	}

	@Override
	protected void initItemList() {
		try {
			service = new DepartmentUIService();
			itemList = service.showDepartments();
			list = service.showEmployees();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		deptTblPanel.loadData(itemList);
	}

	@Override
	protected void addBtnListener() {
		btnAdd.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("수정")) {
					try {
						deptTblPanel.updateRow(deptItemPanel.getItem(),deptTblPanel.getTable().getSelectedRow());
						try {
							service.updateEmployee(deptItemPanel.getItem());
						} catch (SQLException e1) {
							if(e1.getErrorCode()==1062) {
								JOptionPane.showMessageDialog(null, "부서 번호가 중복");
							}
							e1.printStackTrace();
						}
						deptItemPanel.tfClear();
						deptTblPanel.clearSection();
						btnAdd.setText("추가");
					}
					catch(RuntimeException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					
				}
				if(e.getActionCommand().equals("추가")) {
					itemList.add(deptItemPanel.getItem());
					deptTblPanel.loadData(itemList);
					try {
						service.insertEmployee(deptItemPanel.getItem());
					} catch (SQLException e1) {
						if(e1.getErrorCode()==1062) {
							JOptionPane.showMessageDialog(null, "부서 번호가 중복");
						}
						e1.printStackTrace();
					}
					deptItemPanel.tfClear();
					deptTblPanel.clearSection();
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deptItemPanel.tfClear();
			}
		});
	}

	@Override
	protected void initPopMenu() {
		JMenuItem updateItem = new JMenuItem("수정");
		JMenuItem deleteItem = new JMenuItem("삭제");
		JMenuItem selectEmp = new JMenuItem("전체 사원 보기");
		JMenuItem selectGroupByEmp = new JMenuItem("소속 사원 보기");
		ActionListener popListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삭제")) {
					int selIdx = deptTblPanel.getSelectedRowIdx();
					try {
						service.deleteEmployee(itemList.get(selIdx));
						deptTblPanel.removeRow();
						JOptionPane.showMessageDialog(null, "삭제되었습니다");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "문제가 있음" + " " + e1.getErrorCode());
						e1.printStackTrace();
					}
					
				}
				else if (e.getActionCommand().equals("수정")) {
					deptItemPanel.setItem(deptTblPanel.getSelectedItem());
					btnAdd.setText("수정");
				}
				else if (e.getActionCommand().equals("소속 사원 보기")) {
					try {
						dialog.setEmpList(service.showGroupByDnoEmployees(deptTblPanel.getSelectedItem().getDeptNo()));
						dialog.setTitle(deptTblPanel.getSelectedItem().getDeptName() + "부서");
					} catch (SQLException e2) {
						System.out.println(e2.getMessage());
					}
					dialog.setVisible(true);
				}
				
				else {
					JOptionPane.showMessageDialog(null, list);
				}
			}
		};
		deleteItem.addActionListener(popListener);
		updateItem.addActionListener(popListener);
		selectEmp.addActionListener(popListener);
		selectGroupByEmp.addActionListener(popListener);
		popMenu.add(updateItem);
		popMenu.add(deleteItem);
		popMenu.add(selectEmp);
		popMenu.add(selectGroupByEmp);
	}

}
