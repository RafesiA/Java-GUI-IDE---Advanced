import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

public class TermProject extends JFrame {
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
		JMenuItem compile = new JMenuItem("Compile");
		
		MyActionListener listener = new MyActionListener();
		compile.addActionListener(listener);
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			fileMenu.add(menuItem[i]);
		}
		
		runMenu.add(compile);
		
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
						break;
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
