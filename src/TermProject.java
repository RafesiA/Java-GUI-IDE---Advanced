import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

public class TermProject extends JFrame {
	Container cp;
	JTabbedPane pane;
	String fileName;
	static int compileDisable = 1;
	File E_file = new File("C:\\Temp\\Error_File.txt");
	JTextArea ew = new JTextArea(18,50);
	JTextArea ja = new JTextArea(10, 50);
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	void save() {
		if(fileName != null) {
			try {
				String overWrite = ew.getText();
				PrintWriter pw = new PrintWriter(new File(fileName));
				pw.print(overWrite);
				pw.close();
				ja.append("over write complete\n");
				return;
			} catch(IOException save) {
				ja.append("Oveer Writing Error");
				String saveError = save.getMessage();
				ja.append(saveError);
				return;
			}
		} else {
			ja.append("fileName is null\n");
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
			"utf-8"));
				
			} catch(IOException we) {
				String we_error = we.getMessage();
				ja.append(we_error);
				
			}
		}
		
	}
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
		
	public JTabbedPane createTabbedPane(){
	    pane = new JTabbedPane(JTabbedPane.TOP);
		return pane;
	}
	public JTabbedPane DefaultTabbedPane() {
		pane.addTab("Default Tab", new MPanel());
		return pane;
	}
	public JTabbedPane addTabbedPane(String str) {
		pane.addTab(str, new MPanel());
		return pane;
	}
	
	class BPanel extends JPanel{
        public BPanel() {
            ja.setEditable(false);
            setVisible(true);
            setSize(600,200);
            setBackground(Color.yellow);
            add(ja,BorderLayout.CENTER);
            add(new JScrollPane(ja));


        }
    }
	class EPanel extends JPanel{
		public EPanel() {
		
			setVisible(true);
			setSize(600,400);
			setBackground(Color.LIGHT_GRAY);
			add(ew,BorderLayout.CENTER);
			add(new JScrollPane(ew));
		}
	}
	class MPanel extends JPanel{
		
		public MPanel() {
			BPanel b = new BPanel();
			EPanel e = new EPanel();
			setSize(600,600);
			setVisible(true);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container c;
			c = getContentPane();
		
			c.add(b,BorderLayout.SOUTH);
			c.add(e,BorderLayout.CENTER);
		}
	}
	
	public TermProject() {
		
		setTitle("Java IDE");
		createMenu();
		setSize(600,655);
		setVisible(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		JTabbedPane pane = createTabbedPane();
		cp.add(pane, BorderLayout.NORTH);
		setResizable(true);
		DefaultTabbedPane();
				
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
		ew.addFocusListener(listener);
		ew.addKeyListener(listener);
		ew.requestFocus();
		
		mb.add(fileMenu);
		mb.add(runMenu);
		
		setJMenuBar(mb);
	}

	class MyActionListener implements ActionListener, FocusListener, KeyListener{
		private JFileChooser chooser;
		boolean controlPressed, RPressed, SPressed, shiftPressed;
	
		
		public void focusGained(FocusEvent f) {
			if(f.getSource() == ew) {
				System.out.printf("Focus gained\n", f);
			}
		}
		public void focusLost(FocusEvent f)	{
			if(f.getSource() == ew) {
				System.out.printf("Focus lost\n", f);
			}
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
						}
						else {
							fileName = chooser.getSelectedFile().getAbsolutePath();
							File javaFile = new File(fileName);
							BufferedReader br = new BufferedReader(new FileReader(javaFile));
							ew.read(br, javaFile);
							addTabbedPane(chooser.getSelectedFile().getName());
							if(fileName != null) {
								compileDisable = 0;
								return;
								}
							}
						} catch(IOException err) {
						String er = err.getMessage();
						ew.append(er);
						break;
					}
					
				case "Close":
					fileName = null;
					ew.setText("");
					compileDisable = 1;
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
						E_file.delete();
						System.exit(0);
					} if(quitIDE == JOptionPane.NO_OPTION) {
						return;
					} if(quitIDE != JOptionPane.YES_NO_OPTION) {
						return;
					}
					//Quit Function
					
				case "Compile":
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
						} catch(IOException e1) {
							System.out.println(e1);
						}
						compileMessage();
					} else {
						JOptionPane.showMessageDialog(null, "파일은 선택하지 않았습니다.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
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
			if(controlPressed == true && RPressed == true && !E_file.exists() && fileName != null) {
				File runJavaFile = new File(fileName);
				String fileParent = runJavaFile.getParent();
				String fileName = runJavaFile.getName();
				int pos = fileName.lastIndexOf(".");
				if(pos > 0) {
					fileName = fileName.substring(0, pos);
				}
				try {
					String s;
					Process p = new ProcessBuilder("cmd", "/c", "cd", fileParent, "&&", "java", fileName).start();
					BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					while((s = stdOut.readLine()) != null) {
						ja.append(s);
						ja.append("\n");
					}
					while((s = stdError.readLine()) != null) {
						ja.append(s);
						ja.append("\n");
					}
				} catch(IOException re) {
					ja.append("ERROR");
					System.out.println(re);
				}
			}
			else if(controlPressed == true && RPressed == true && E_file.exists()) {
				try {
					FileReader reader = null;
					BufferedReader br = new BufferedReader(new FileReader(E_file));
					reader = new FileReader(E_file);
					ja.read(br, E_file);
					ja.append("\n");
					reader.close();
					br.close();
				} catch(IOException e) {
					ja.append("error");
				}
			}
			else if(controlPressed == true && RPressed == true) {
				ja.append("파일을 업로드해주세요.\n");
			}
			else if(controlPressed == true && SPressed == true && shiftPressed == false) {
				save();
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
