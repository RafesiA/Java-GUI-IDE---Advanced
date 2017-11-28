import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;

public class TermProject extends JFrame {
	public TermProject() {
		setTitle("Java IDE");
		createMenu();
		setSize(600,600);
		setVisible(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c;
		c = getContentPane();
		JTextArea ew = new JTextArea();
		c.add(ew, BorderLayout.CENTER);
	}
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem[] menuItem = new JMenuItem [5];
		String[] itemTitle = {"Open", "Close", "Save", "Save As", "Quit"};
		JMenu runMenu = new JMenu("Run");
		
		MyActionListener listener = new MyActionListener();
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			fileMenu.add(menuItem[i]);
		}
		
		runMenu.add(new JMenuItem("Compile"));
		
		mb.add(fileMenu);
		mb.add(runMenu);
		
		setJMenuBar(mb);
	}
	
	class MyActionListener implements ActionListener{
		private JFileChooser chooser;
		
		public MyActionListener(){
			chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("java File", "java");
			chooser.setFileFilter(filter);
		}
		
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			String fileName = null;
			switch(command) {
				case "Open":
					int ret = chooser.showOpenDialog(null);
					if(ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					fileName = chooser.getSelectedFile().getAbsolutePath();
					System.out.println(fileName);
			
			}
		}
	}
	
	public static void main(String[] args) {
		new TermProject();
	}

}
