import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

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
		JMenu runMenu = new JMenu("Run");
		
		fileMenu.add(new JMenuItem("Open"));
		fileMenu.add(new JMenuItem("Close"));
		fileMenu.add(new JMenuItem("Save"));
		fileMenu.add(new JMenuItem("Save as"));
		fileMenu.add(new JMenuItem("Quit"));
		
		runMenu.add(new JMenuItem("Compile"));
		
		mb.add(fileMenu);
		mb.add(runMenu);
		
		setJMenuBar(mb);
		
	}
	
	public static void main(String[] args) {
		new TermProject();
	}

}
