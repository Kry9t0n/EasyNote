package editor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.Keymap;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sound.*;

public class editor extends JFrame implements ActionListener {
	private JMenuBar bar;
	private JMenu menu1;
	private JMenu menu2;
	private JTextArea area;
	private JLabel jlabel1;
	private JScrollPane jsp;
	private JFileChooser chooser;
	private JMenuItem save;
	private JMenuItem close;
	private JMenuItem search;
	private JMenuItem mark;
	private JMenuItem open;
	private JMenuItem info;
	private JMenuItem help;
	private JMenuItem print;
	private JMenu edit;
	private HelpDialog hlpd;
	private JMenu date;//submenu
	private JMenuItem time, localdate;
	//private JMenuItem font;
	private String cpy; // copy string
	private Color MARK;
	private Highlighter highlighter;
	private HighlightPainter painter;
	private MarkColorChooserDialog c_dialog;

	public editor() {
		super("EasyNote");//setting the title
		
		highlighter = new DefaultHighlighter();
		
		bar = new JMenuBar();
		area = new JTextArea(20, 20);
		menu1 = new JMenu("Datei...");
		menu2 = new JMenu("Hilfe");
		jlabel1 = new JLabel();
		jsp = new JScrollPane(area);
		chooser = new JFileChooser();
		search = new JMenuItem("Suchen");
		mark = new JMenuItem("Markieren");
		save = new JMenuItem("Speichern", KeyEvent.VK_S);
		open = new JMenuItem("Öffnen", KeyEvent.VK_O);
		close = new JMenuItem("Beenden");
		info = new JMenuItem("Informationen");
		help = new JMenuItem("Hilfe", KeyEvent.VK_H);
		print = new JMenuItem("Drucken");
		edit = new JMenu("Bearbeiten");
		date = new JMenu("Datum und Zeit");
		time = new JMenuItem("Zeit");
		localdate = new JMenuItem("Datum");
		//font = new JMenuItem("Schrifteinstellungen");
		
		area.setHighlighter(highlighter);
		
		//setting the shortcuts
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		mark.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 600);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setResizable(true);
		date.add(time);
		date.add(localdate);
		edit.add(date);
		edit.add(search);
		edit.add(mark);
		//edit.add(font);
		save.addActionListener(this);
		open.addActionListener(this);
		close.addActionListener(this);
		help.addActionListener(this);
		info.addActionListener(this);
		localdate.addActionListener(this);
		time.addActionListener(this);
		print.addActionListener(this);
		mark.addActionListener(this);
		search.addActionListener(this);
		menu2.add(info);
		menu2.add(help);
		//menu1
		menu1.add(save);
		menu1.add(close);
		menu1.add(open);
		menu1.add(print);
		bar.add(menu1);
		bar.add(edit);
		bar.add(menu2);
		add(bar);
		
		jsp.setSize(450, 500);
		jsp.setLocation(200, 5);
		add(jsp);
		setJMenuBar(bar);
		

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		switch(cmd) {
		case "Beenden":
			System.exit(0);
		case "Speichern":
			saveFile();
			break;
		case "Öffnen":
			openFile();
			break;
		case "Informationen":
			javax.swing.JOptionPane.showMessageDialog(this, "This program is developed by Kry9t0n\n (c) 2019 \n Version: 0.9.1");
			break;
		case "Hilfe":
			hlpd = new HelpDialog();
			break;
		case "Datum":
			//area.setText(getDate());
			area.append(getDate());
			break;
		case "Zeit":
			//area.setText(getTime());
			area.append(getTime());
			break;
		case "Markieren":
			if(area.getSelectedText() == null) {
				javax.swing.JOptionPane.showMessageDialog(null, "Text muss erst ausgewählt werden");
				break;
			}else {
				c_dialog = new MarkColorChooserDialog(this);
			}
			break;
		case "Drucken":
			/*
			npj = new NetPrintJobs(this.area);
			try {
				npj.print();
			}catch (PrinterException e) {
				Toolkit.getDefaultToolkit().beep();
				javax.swing.JOptionPane.showMessageDialog(this, "Unable to print this text!", "Fehler", JOptionPane.ERROR_MESSAGE);
			}*/
			try {
				area.print();
			} catch (PrinterException e) {
				javax.swing.JOptionPane.showMessageDialog(this, "Unable to print this text!", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public void openFile() {
		String text = "";
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		if (f != null) {
			try {
				updateTitle(f.getName());
				Scanner scan = new Scanner(f);
				scan.useLocale(Locale.GERMAN);
				while (scan.hasNext()) {
					text = text.concat(scan.nextLine()+"\n");
				}
				area.setText(text);
			} catch (FileNotFoundException e) {
				Toolkit.getDefaultToolkit().beep();
				javax.swing.JOptionPane.showMessageDialog(null, "Fehler! Datei konnte nicht gefunden werden", "Fehler",JOptionPane.ERROR_MESSAGE);
				jlabel1.setText("Fehler! Datei konnte nicht geöffnet werden!");

			}
		}
	}

	public void saveFile() {
		chooser.showSaveDialog(null);
		File f = chooser.getSelectedFile();
		if(f != null) {
			try {
				PrintWriter write = new PrintWriter(f);
				write.println(area.getText());
				write.close();
			}catch (Exception e) {
				Toolkit.getDefaultToolkit().beep();
				javax.swing.JOptionPane.showMessageDialog(null, "Datei konnte nicht gespeichert werden", "Fehler", JOptionPane.ERROR_MESSAGE);
				jlabel1.setText("Datei konnte nicht gespeichert werden.");
			}
		}

	}
	
	public String getDate() {
		SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy");
		return form.format(new Date());
	}
	
	public String getTime() {
		SimpleDateFormat form = new SimpleDateFormat("HH:MM:ss");
		return form.format(new Date().getTime());
	}
	
	public void updateTitle(String filename) {
		setTitle("EasyNote --- " + " " + filename);
	}
	
	public void mark() {
			painter = new DefaultHighlighter.DefaultHighlightPainter(MARK);
			int start_pos = area.getText().indexOf(area.getSelectedText());
			int end = start_pos + area.getSelectedText().length();
			try {
				highlighter.addHighlight(start_pos, end, painter);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setMarkColor(Color c) {
		this.MARK = c;
	}

	public static void main(String[] args) {
		editor e = new editor();
		System.out.println(e.getDate());
		System.out.println(e.getTime());
	}

}
