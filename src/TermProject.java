import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

public class TermProject extends JFrame {
	JTextArea ew = new JTextArea();
	
	class EPanel extends JPanel{
		EPanel(){
			setVisible(true);
			setSize(10, 10);
			setLayout(new BorderLayout());
			ew.setSize(10, 10);
			add(ew,BorderLayout.CENTER);
			add(new JScrollPane(ew));
		}
	}
	
	public TermProject() {
		EPanel e = new EPanel();
		e.setLocation(0, 10);
		setTitle("Java IDE");
		createMenu();
		setSize(600,600);
		setVisible(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c;
		c = getContentPane();
		c.add(e);
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
					try {
						int ret = chooser.showOpenDialog(null);
						if(ret != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "Warning", JOptionPane.WARNING_MESSAGE);
						}
						fileName = chooser.getSelectedFile().getAbsolutePath();
						File javaFile = new File(fileName);
						BufferedReader br = new BufferedReader(new FileReader(javaFile));
						ew.read(br, javaFile);
						br.close();
					} catch(IOException err) {
						String er = err.getMessage();
						ew.append(er);
					}
						break;
				case "Close":
					//Close Function
					
				case "Save":
					//Save Function
					
				case "Save As":
					//Save As Function
					
				case "Quit":
					System.exit(0);
					//Quit Function
			
			}
		}
	}
	
	public static void main(String[] args) {
		new TermProject();
	}

}
