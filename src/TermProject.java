import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;

public class TermProject extends JFrame {

	JTextArea ja = new JTextArea(10,50);

	String fileName;
	
	void compileMessage() {
		if(E_file.exists()) {
			JOptionPane.showMessageDialog(null, "컴파일 오류", "Compile Error",
		JOptionPane.WARNING_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "컴파일 완료", "Compile Successful",
		JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	File E_file = new File("C:\\Temp\\Error_File.txt");
	JTextArea ew = new JTextArea();
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	
	class BPanel extends JPanel{
		public BPanel() {
			ja.setEditable(false);
			setVisible(true);
			setSize(600,200);
			setBackground(Color.LIGHT_GRAY);
			add(ja,BorderLayout.CENTER);
			add(new JScrollPane(ja));
		}
	}
	JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
		return pane;
	}
	public TermProject() {
		BPanel b = new BPanel();
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
		c.add(b, BorderLayout.SOUTH);
		setResizable(false);
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
					fileName = chooser.getSelectedFile().getAbsolutePath();
					System.out.println(fileName);
					if(ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "Warning", JOptionPane.WARNING_MESSAGE);
						
					}

					return;
				case "Close":
					//Close Function
					
				case "Save":
					//Save Function
					
				case "Save As":
					//Save As Function
					
				case "Quit":
					E_file.delete();
					System.exit(0);
					//Quit Function
				case "Compile":
					String s = null;
					try {
						Process oProcess = new ProcessBuilder("javac", fileName).start();
						BufferedReader stdError = new BufferedReader(new InputStreamReader
					(oProcess.getErrorStream()));
						while ((s = stdError.readLine()) != null) {
							BufferedWriter fw = new BufferedWriter(new FileWriter(E_file, true));
							fw.write(s);
							fw.write(LINE_SEPARATOR);
							fw.flush();
							fw.close();
						}
					} catch(IOException e1) {
						System.out.println(e1);
					}
					compileMessage();
			}
		}
	}
	
	public static void main(String[] args) {
		new TermProject();
	}

}
