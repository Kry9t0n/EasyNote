package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

public class MarkColorChooserDialog extends JDialog implements ChangeListener{
	private JColorChooser jc;
	private editor parent;
	
	public MarkColorChooserDialog(editor parent) {
		this.parent = parent;
		BorderLayout b_layout = new BorderLayout();
		getContentPane().setLayout(b_layout);
		
		jc = new JColorChooser(Color.RED);
		jc.getSelectionModel().addChangeListener(this);
		jc.setBorder(BorderFactory.createTitledBorder("Bitte Farbe auswählen"));
		
		add(jc, BorderLayout.PAGE_END);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("FarbenDialog");
		pack();
		setVisible(true);
		parent.setMarkColor(jc.getColor());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Color choosen = jc.getColor();
		parent.setMarkColor(choosen);
		
	}
}
