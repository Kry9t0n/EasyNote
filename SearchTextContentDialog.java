package editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

/**
 * 
 * @author Kry9t0n This program represents the Dialog to search and highlight
 *         searched text content in the jtextarea if found
 *
 */

public class SearchTextContentDialog extends JDialog implements WindowListener {
	private JTextArea jtxt;
	private JLabel searchString;
	private JLabel res;
	private JTextField input;
	private JButton search;
	private final Color ERROR;
	private final Color HIGHLIGHT;
	private Highlighter highligther;
	private HighlightPainter painter;
	private Object HighlightTag;

	public SearchTextContentDialog(JTextArea jtxt) {
		this.jtxt = jtxt;

		// setting the layout
		SpringLayout spring = new SpringLayout();
		getContentPane().setLayout(spring);

		ERROR = Color.RED;
		HIGHLIGHT = Color.lightGray;
		highligther = jtxt.getHighlighter();
		painter = new DefaultHighlighter.DefaultHighlightPainter(HIGHLIGHT);

		search = new JButton("Suchen");
		input = new JTextField("Eingabe");
		searchString = new JLabel("Text eingeben:");
		res = new JLabel();

		input.setColumns(15);
		// input.setLocation(70, 30);
		input.setEditable(true);
		add(input);

		// search button
		search.setSize(90, 30);
		search.addActionListener((ActionEvent e) -> {
			search(input.getText());
		});
		search.setLocation(400, 50);
		add(search);

		searchString.setLocation(10, 30);
		searchString.setSize(120, 30);
		add(searchString);

		res.setLocation(10, 380);
		res.setText("No job executed");
		add(res);

		// adjust constrains
		spring.putConstraint(SpringLayout.WEST, searchString, 5, SpringLayout.WEST, getContentPane());
		spring.putConstraint(SpringLayout.NORTH, searchString, 5, SpringLayout.NORTH, getContentPane());

		spring.putConstraint(SpringLayout.WEST, input, 5, SpringLayout.EAST, searchString);
		spring.putConstraint(SpringLayout.NORTH, input, 5, SpringLayout.NORTH, getContentPane());

		spring.putConstraint(SpringLayout.EAST, getContentPane(), 5, SpringLayout.EAST, input);
		spring.putConstraint(SpringLayout.SOUTH, getContentPane(), 50, SpringLayout.SOUTH, input);

		spring.putConstraint(SpringLayout.WEST, search, 15, SpringLayout.WEST, getContentPane());
		spring.putConstraint(SpringLayout.NORTH, search, 10, SpringLayout.NORTH, getContentPane());

		spring.putConstraint(SpringLayout.NORTH, search, 10, SpringLayout.SOUTH, searchString);
		spring.putConstraint(SpringLayout.SOUTH, getContentPane(), 40, SpringLayout.SOUTH, search);

		spring.putConstraint(SpringLayout.WEST, res, 50, SpringLayout.WEST, getContentPane());
		spring.putConstraint(SpringLayout.NORTH, res, 10, SpringLayout.SOUTH, search);
		spring.putConstraint(SpringLayout.SOUTH, getContentPane(), 25, SpringLayout.SOUTH, res);
		addWindowListener(this);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Suchen");
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);

	}

	private void search(String exp) {
		if (exp == null || exp.length() == 0) {
			res.setText("Nothing to search");
			res.setForeground(ERROR);
			return;
		} else {
			String s = jtxt.getText();
			String key = input.getText();
			int begin = s.indexOf(key);
			if (begin >= 0) {
				int end = begin + key.length();
				try {
					HighlightTag = highligther.addHighlight(begin, end, painter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				res.setText("Wort gefunden!");
			} else {
				res.setText("Word nicht enthalten");
				res.setForeground(ERROR);
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (this.HighlightTag != null) {
			highligther.removeHighlight(HighlightTag);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
