import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;

public class TermProject extends JFrame {
	
	JButton btn = new JButton("Add Tab Test");
	Container cp;
	JTabbedPane pane;
	JTextArea ew;
	JTextArea ja;

	
	
	class NPanel extends JPanel{
		public NPanel() {
			setVisible(true);
			setSize(600,250);
			setLayout(new BorderLayout());
			ew = new JTextArea(15,40);
			add(ew, BorderLayout.CENTER);
			add(new JScrollPane(ew));
			setBackground(Color.YELLOW);
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
			setBackground(Color.LIGHT_GRAY);
		}
	}
	class MPanel extends JPanel{
		public MPanel() {
			setVisible(true);
			setSize(600,500);
			setLayout(new BorderLayout());
			NPanel n = new NPanel();
			BPanel b = new BPanel();
			add(n,BorderLayout.NORTH);
			add(b,BorderLayout.CENTER);
			
		}
		
	}
	JTabbedPane createTabbedPane() {
		pane = new JTabbedPane(JTabbedPane.TOP);
		return pane;
	}
	JTabbedPane addTabbedPane() {
		pane.addTab("test",new MPanel());
		repaint();
		return pane;
	}
	
	public TermProject() {
		setLayout(new BorderLayout());
		cp = getContentPane();
		JTabbedPane pane = createTabbedPane();
		cp.add(pane,BorderLayout.CENTER);
		setVisible(true);
		setSize(608,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenu();
		setResizable(false);
		add(btn,BorderLayout.SOUTH);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTabbedPane();
			}
		});
	}
	
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem[] menuItem = new JMenuItem [5];
		String[] itemTitle = {"Open", "Close", "Save", "Save As", "Quit"};
		JMenu runMenu = new JMenu("Run");
		JMenuItem compile = new JMenuItem("Compile");
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			fileMenu.add(menuItem[i]);
		}
		
		runMenu.add(compile);
		mb.add(fileMenu);
		mb.add(runMenu);
		setJMenuBar(mb);
	}
	public static void main(String[] args) {
		new TermProject();
	}
}
