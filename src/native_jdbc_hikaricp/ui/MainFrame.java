package native_jdbc_hikaricp.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				DepartmentMainPanel deptMainPanel = new DepartmentMainPanel();
				frame.add(deptMainPanel);
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				frame.setBounds(0, 0, 600, 600);
				frame.setVisible(true);
			}
		});
	}
}
