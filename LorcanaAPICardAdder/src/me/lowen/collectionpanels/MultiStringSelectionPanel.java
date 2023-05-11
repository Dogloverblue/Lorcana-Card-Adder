package me.lowen.collectionpanels;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class MultiStringSelectionPanel extends JPanel{

	private static final long serialVersionUID = -8773988057225878029L;
	JComboBox<String> colorsBox;
	
	private String key;
	public MultiStringSelectionPanel(String[] values, String what, String key) {
		this.key = key;
	this.setLayout(new FlowLayout(FlowLayout.LEFT));
	this.add(new JLabel(what));
	
	colorsBox = new JComboBox<String>(values);
	this.add(colorsBox);
	
	}
	public String getValue() {
		return (String) colorsBox.getSelectedItem();
	}
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setToDefaultValue() {
		colorsBox.setSelectedIndex(0);
	}
    
}
