package me.lowen.collectionpanels;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SingleCollectionPanel extends JPanel{

	private static final long serialVersionUID = -8829978811099782009L;
	
	private JTextField field;
	private String key;
	public SingleCollectionPanel(String what, String key) {
		this.key = key;
		FlowLayout out = new FlowLayout(FlowLayout.LEFT);
//		out.setHgap(10);
		this.setLayout(out);
		this.add(new JLabel(what));

		field = new JTextField();
		field.setColumns(20);
//		field.addKeyListener(CardAddingFrame.sillyThing());
		this.add(field);
	}
	
	
	public String getText() {
		return field.getText();
	}
	public void setText(String text) {
		field.setText(text);
	}
	
	public JTextField getField() {
		return field;
	}
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	
	
}
