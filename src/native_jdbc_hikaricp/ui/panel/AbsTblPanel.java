package native_jdbc_hikaricp.ui.panel;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public abstract class AbsTblPanel<T> extends JPanel {
	private JScrollPane scrollPane;
	protected JTable table;
	protected NotEditableModel model;
	
	/**
	 * Create the panel.
	 */
	public AbsTblPanel() {

		initialize();
	}
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}
	
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public NotEditableModel getModel() {
		return model;
	}
	public void setModel(NotEditableModel model) {
		this.model = model;
	}
	public void setPopupMenu(JPopupMenu popupMenu) {
		scrollPane.setComponentPopupMenu(popupMenu);
		table.setComponentPopupMenu(popupMenu);
	}
	public void loadData(List<T> itemList) {
		model = new NotEditableModel(getRows(itemList),getColumns());
		table.setModel(model);
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
				
		setTblWidthAlign();
	}
	protected abstract void setTblWidthAlign();
	protected void tableCellAlign(int align, int...idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);
		
		TableColumnModel cModel = table.getColumnModel();
		for(int i=0;i<idx.length;i++) {
			cModel.getColumn(idx[i]).setCellRenderer(dtcr);
		}
		
	}
	protected void tableSetWidth(int...width) { //가변인수 형태
		TableColumnModel cModel = table.getColumnModel();
		for(int i=0;i<width.length;i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}
	protected abstract String[] getColumns();
	protected Object[][] getRows(List<T> itemList) {
		Object[][] rows = new Object[itemList.size()][];
		for(int i=0;i<rows.length;i++) {
			rows[i] = toArray(itemList.get(i));
		}
		return rows;
	}
	protected abstract Object[] toArray(T item);
	public void removeRow() {
		int selectedIdx = getSelectedRowIdx();
		model.removeRow(selectedIdx);
	}
	public abstract void updateRow(T item, int updateIdx);
	public void addItem(T item) {
		model.addRow(toArray(item));
	}
	
	public abstract T getSelectedItem();
	
	public int getSelectedRowIdx() {
		int selectedIdx = table.getSelectedRow();
		if(selectedIdx==-1) {
			throw new RuntimeException("선택부터 해주세요");
		}
		return selectedIdx;
	}
	
	protected class NotEditableModel extends DefaultTableModel {

		public NotEditableModel(Object[][] rows, String[] columns) {
			super(rows,columns);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
	}
	/* 개인적으로 따로 추가
	 * private class ReturnTableCellRenderer extends JLabel implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (value==null) return this;
			setText(value.toString());
			setOpaque(true);
			setHorizontalAlignment(JLabel.CENTER);
			
			if (Integer.parseInt(table.getValueAt(row, 2).toString())>=90) {
				setBackground(Color.CYAN);
			}else if(Integer.parseInt(table.getValueAt(row, 2).toString())>=80) {
				setBackground(Color.LIGHT_GRAY);
			}
			else {
				setBackground(Color.WHITE);
			}
			if (isSelected) {
				setBackground(Color.orange);
			}
			return this;
		}*/
	public void clearSection() {
		table.clearSelection();
	}
}
