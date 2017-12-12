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
		
		mb.add(fileMenu);
		mb.add(runMenu);
		
		setJMenuBar(mb);
	}
	
	
	class MyActionListener implements ActionListener, FocusListener, KeyListener{
		private JFileChooser chooser;
		boolean controlPressed, RPressed;
		
		public void keyPressed(KeyEvent k) {
			switch(k.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				controlPressed = true;
				
			case 'R':
				 RPressed = true;
			}
			
		}
		public void keyReleased(KeyEvent k) {
			switch(k.getKeyCode()) {
			case KeyEvent.VK_CONTROL:
				controlPressed = false;
				
			case 'R':
				RPressed = false;
			}
		}
		@Override
		public void keyTyped(KeyEvent k) {
			if(controlPressed == true && RPressed == true) {
				System.out.println("Ctrl + R");
			}
			
		}
		
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
						fileName = chooser.getSelectedFile().getAbsolutePath();
						File javaFile = new File(fileName);
						BufferedReader br = new BufferedReader(new FileReader(javaFile));
						ew.read(br, javaFile);
						addTabbedPane(chooser.getSelectedFile().getName());
						if(fileName != null) {
							compileDisable = 0;
						}
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
	}
	
	public static void main(String[] args) {
		new TermProject();
	}

}
