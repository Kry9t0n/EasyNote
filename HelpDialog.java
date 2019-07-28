package editor;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/*
 * This class is used to give some help and documentation for the user how to use the program and how the shortcuts work.
 */
public class HelpDialog extends JDialog{
	private JButton ok;
	private JButton cancel;
	private JScrollPane jsp;
	private JTextArea text;
	
	public HelpDialog() {
		/*all objects*/
		ok = new JButton("OK");
		cancel = new JButton("Abbrechen");
		text = new JTextArea(); // the content will be setted later on
		
		cancel.setLocation(20, 310);
		cancel.setSize(90, 30);
		cancel.addActionListener((ActionEvent e) -> {this.discard()});
		
		ok.setLocation(110, 310);
		ok.setSize(90, 30);
		ok.addActionListener((ActionEvent e) -> {this.discard();});
		
		text.setColumns(20);
		text.setRows(5);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setSize(250, 250);
		
		jsp = new JScrollPane(text);
		
		add(ok);
		add(cancel);
		add(jsp);
		
		setHelpText();
		
		setSize(400, 400);
		setTitle("Hilfe");
		setLocationRelativeTo(null); //centers the dialog on the screen
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	private void setHelpText() {
		String text = "SHORTCUTS:\n Ctrl + s: opens the save dialog\n Ctrl + o: open the dialog to choose a later note to open\n Ctrl + p: opens the printer dialog\n Ctrl + m: opens the color chooser dialog"
				+ "MNEMONICS:\n Datei -> s: opens the save dialog\n Hilfe -> h: opens the help dialog (this)\n";
		this.text.setText(text);
	}
	
}
