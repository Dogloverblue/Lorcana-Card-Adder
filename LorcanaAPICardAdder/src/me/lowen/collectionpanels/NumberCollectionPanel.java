package me.lowen.collectionpanels;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class NumberCollectionPanel extends JPanel{

	private static final long serialVersionUID = -4620077204812373829L;

	JSpinner box;
	private String key;
	public NumberCollectionPanel(String what, String key) {
		this.key = key;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel(what));
		
		box = new JSpinner();
		this.add(box);
	}
	
	public int getValue() {
		return Integer.parseInt(box.getValue().toString());
	}
	public void setValue(int value) {
		box.setValue(value);
		
	}
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
