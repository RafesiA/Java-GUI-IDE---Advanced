import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

public class TermProject extends JFrame {
	String fileName;
	File E_file = new File("C:\\Temp\\Error_File.txt");
	Container cp;
	JTabbedPane pane;
	JTextArea ew;
	JTextArea ja;
	static int index;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	static int compileDisable = 1;

	
	void run() {
		if(!E_file.exists()) {
			File runJavaFile = new File(fileName);
			String fileParent = runJavaFile.getParent();
			String fileName = runJavaFile.getName();
			int pos = fileName.lastIndexOf(".");
			if(pos > 0) {
				fileName = fileName.substring(0, pos);
			}
			try {
				String st;
				Process p = new ProcessBuilder("cmd", "/c", "cd", fileParent, "&&", "java", fileName).start();
				BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while((st = stdOut.readLine()) != null) {
					ja.append(st);
					ja.append("\n");
				}
				while((st = stdError.readLine()) != null) {
					ja.append(st);
					ja.append("\n");
				}
				stdOut.close();
				stdError.close();
			} catch(IOException re) {
				ja.append("ERROR");
				System.out.println(re);
			}
		}
	}
	
	
	void compile() {
		String s = null;
		if(E_file.exists()) {
			E_file.delete();
		}
		if(compileDisable != 1) {
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
				stdError.close();
			} catch(IOException e1) {
				System.out.println(e1);
			}
			compileMessage();
		} else {
			JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		run();
	}
	

	void save() {
		if(fileName != null) {
			int saveConfirm = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?", "Overwrite?",
		JOptionPane.YES_NO_OPTION);
			if(saveConfirm == JOptionPane.YES_OPTION) {
			try {
				String overWrite = ew.getText();
				PrintWriter pw = new PrintWriter(new File(fileName));
				pw.print(overWrite);
				pw.close();
				JOptionPane.showMessageDialog(null, "저장했습니다.", "Saved!", JOptionPane.PLAIN_MESSAGE);
				ja.append("over write complete\n");
				return;
			} catch(IOException save) {
				ja.append("Oveer Writing Error");
				String saveError = save.getMessage();
				ja.append(saveError);
				return;
			}
		}
			else if(saveConfirm == JOptionPane.NO_OPTION) {
				return;
			}
			else if(saveConfirm != JOptionPane.YES_NO_OPTION) {
				return;
			}
				
			}
		else {
			ja.append("다른 이름으로 파일을 저장합니다.\n");
			saveAs();
		}
	}
	void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		int retval = fileChooser.showSaveDialog(null);
		if(retval == fileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if(!file.getName().toLowerCase().endsWith(".java")) {
				file = new File(file.getParentFile(), file.getName() + ".java");
			} else {
				JOptionPane.showMessageDialog(null, "이미 파일이 존재합니다.", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			try {
				ew.write(new OutputStreamWriter(new FileOutputStream(file),
			"euc-kr"));
				
			} catch(IOException we) {
				String we_error = we.getMessage();
				ja.append(we_error);
			}
		}
		
	}
	void compileMessage() {
		if(E_file.exists()) {
			JOptionPane.showMessageDialog(null, "컴파일 실패", "Compile Error",
		JOptionPane.WARNING_MESSAGE);
			try {
				BufferedReader br = new BufferedReader(new FileReader(E_file));
				ja.read(br, E_file);
				ja.append("\n");
				br.close();
			} catch(IOException e) {
				ew.append("Message Error");
				}
			}
		else {
			JOptionPane.showMessageDialog(null, "컴파일 성공", "Compile Successful",
					JOptionPane.INFORMATION_MESSAGE);
			ja.append("컴파일 완료\n");
		}
	}
	
	
	class NPanel extends JPanel{
		public NPanel() {
			MyActionListener listener = new MyActionListener();
			setVisible(true);
			setSize(600,250);
			setLayout(new BorderLayout());
			ew = new JTextArea(15,40);
			add(ew, BorderLayout.CENTER);
			add(new JScrollPane(ew));
			setBackground(Color.YELLOW);
			ew.setRequestFocusEnabled(true);
			ew.addFocusListener(listener);
			ew.addKeyListener(listener);
		}
	}
	class BPanel extends JPanel{
		public BPanel() {
			setVisible(true);
			setSize(600,250);
			setLayout(new BorderLayout());
			ja = new JTextArea(10,40);
			add(ja, BorderLayout.CENTER);
			add(new JScrollPane(ja));
			ja.setEditable(false);
			setBackground(Color.LIGHT_GRAY);
		}
	}
	class MPanel extends JPanel{
		public MPanel() {
			setVisible(true);
			setSize(600,250);
			setLayout(new BorderLayout());
			NPanel n = new NPanel();
			add(n,BorderLayout.CENTER);
			
		}
		
	}
	JTabbedPane createTabbedPane() {
		pane = new JTabbedPane(JTabbedPane.TOP);
		return pane;
	}
	JTabbedPane addTabbedPane(String str,String str2) {
		MyActionListener listener = new MyActionListener();
		pane.addTab(str, null, new MPanel(), str2);
		repaint();
		pane.addChangeListener(listener);
		return pane;
	}

	
	public TermProject() {
		BPanel b = new BPanel();
		setLayout(new BorderLayout());
		cp = getContentPane();
		JTabbedPane pane = createTabbedPane();
		cp.add(pane,BorderLayout.CENTER);
		setVisible(true);
		setSize(608,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenu();
		setResizable(false);
		addTabbedPane("Default Tab","");
		add(b,BorderLayout.SOUTH);
		setTitle("Java IDE");
	}
	
	private void createMenu() {
		MyActionListener listener = new MyActionListener();
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem[] menuItem = new JMenuItem [5];
		String[] itemTitle = {"Open", "Close", "Save", "Save As", "Quit"};
		JMenu runMenu = new JMenu("Run");
		JMenuItem compile = new JMenuItem("Compile");
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			fileMenu.add(menuItem[i]);
			menuItem[i].addActionListener(listener);
		}
		
		runMenu.add(compile);
		mb.add(fileMenu);
		mb.add(runMenu);
		compile.addActionListener(listener);
		setJMenuBar(mb);
	}
	

	class MyActionListener implements ActionListener, FocusListener, KeyListener, ChangeListener{
		private JFileChooser chooser;
		boolean controlPressed, RPressed, SPressed, shiftPressed;
	
		public void stateChanged(ChangeEvent c) {
			MyActionListener listener = new MyActionListener();
			JTabbedPane sourceTabbedPane = (JTabbedPane)c.getSource();
			index = sourceTabbedPane.getSelectedIndex();
			fileName = sourceTabbedPane.getToolTipTextAt(index);
		}
		
		
		public void focusGained(FocusEvent f) {
		}
		public void focusLost(FocusEvent f)	{
		}
		
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
							return;
						}
						else {
							fileName = chooser.getSelectedFile().getAbsolutePath();
							File javaFile = new File(fileName);
							addTabbedPane(chooser.getSelectedFile().getName(),chooser.getSelectedFile().getPath());
							BufferedReader br = new BufferedReader(new FileReader(javaFile));
							ew.read(br, javaFile);
							if(fileName != null) {
								compileDisable = 0;
								return;
								}
							}
						} catch(IOException err) {
						String er = err.getMessage();
						ew.append(er);
					}
					
				case "Close":
					if(index != 0) {
						pane.removeTabAt(index);
					}
					else {
						ja.append("Default Tab은 닫을 수 없습니다\n");
					}
					return;
					//Close Function
					
				case "Save":
					save();
					return;
					//Save Function
					
				case "Save As":
					saveAs();
					return;
					//Save As Function
					
				case "Quit":
					int quitIDE = JOptionPane.showConfirmDialog(null, "Java IDE를 종료합니까?",
				"Are you sure?", JOptionPane.YES_NO_OPTION);
					if(quitIDE == JOptionPane.YES_OPTION) {
						if(E_file.exists()) {
							E_file.delete();
						}
						System.exit(0);
					} if(quitIDE == JOptionPane.NO_OPTION) {
						return;
					} if(quitIDE != JOptionPane.YES_NO_OPTION) {
						return;
					}
					//Quit Function
					
				case "Compile":
					compile();
			}
		
		}

		public void keyPressed(KeyEvent k) {
			switch(k.getKeyCode()) {
				case KeyEvent.VK_CONTROL:
					controlPressed = true;
					return;
				case 'R':
					RPressed = true;
					return;
				case 'S':
					SPressed = true;
					return;
				case KeyEvent.VK_SHIFT:
					shiftPressed = true;
					return;
			}
			
		}
		public void keyReleased(KeyEvent k) {
			switch(k.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				controlPressed = false;
				
			case 'R':
				RPressed = false;
				
			case 'S':
				SPressed = false;
				
			case KeyEvent.VK_SHIFT:
				shiftPressed = false;
				
			}
		}
		public void keyTyped(KeyEvent k) {
			if(controlPressed == true && RPressed == true && shiftPressed != true) {
				if(E_file.exists()) {
					E_file.delete();
				}
				compile();
				controlPressed = false;
				RPressed = false;
				shiftPressed = false;
			}
			
			else if(controlPressed == true && shiftPressed == true && RPressed == true && !E_file.exists() && fileName != null) {
				run();
				shiftPressed = false;
			}
			else if(controlPressed == true && RPressed == true && fileName == null) {
				ja.append("파일을 업로드해주세요.\n");
			}
			else if(controlPressed == true && SPressed == true && shiftPressed == false) {
				save();
				controlPressed = false;
				SPressed = false;
			}
			else if(controlPressed == true && shiftPressed == true && SPressed == true) {
				saveAs();
				shiftPressed = false;
			}
		}
	}
	
	public static void main(String[] args) {
		new TermProject();
	}
}
