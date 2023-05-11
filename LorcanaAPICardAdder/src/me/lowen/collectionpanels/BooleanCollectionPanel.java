package me.lowen.collectionpanels;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BooleanCollectionPanel extends JPanel{

	private static final long serialVersionUID = -3209272971662190667L;
	
	private JCheckBox box;
	private String key;
	public BooleanCollectionPanel(String what, String key) {
		this.key = key;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel(what);
		this.add(label);
		box = new JCheckBox();
		this.add(box);
	}
	public boolean getValue() {
		return box.isSelected();
	}
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setSelected(boolean selected) {
		box.setSelected(selected);
	}

}
