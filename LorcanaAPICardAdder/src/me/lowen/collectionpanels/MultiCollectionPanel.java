package me.lowen.collectionpanels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MultiCollectionPanel extends JPanel {

	private static final long serialVersionUID = -8829978099782009L;
	
	private ArrayList<JTextField> fields;
	private String key;
	public MultiCollectionPanel(String what, int numOfBoxes, String key) {
		this.key = key;
		FlowLayout out = new FlowLayout(FlowLayout.LEFT);
		fields = new ArrayList<>();
//		out.setHgap(10);
		JButton plus = new JButton("+");
		plus.setFocusPainted(false);
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onPlusPress();
				
			}
			
		});
		JButton minus = new JButton("-");
		minus.setFocusPainted(false);
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onMinusPress();
				
				
			}
			
		});
		this.setLayout(out);
		this.add(new JLabel(what));

		this.add(plus);
		this.add(minus);
		
		for (int i = 0; i < numOfBoxes; i++) {
			onPlusPress();
		}
		
	
	}
	private void onPlusPress() {
		JTextField smallField = new JTextField();
		smallField.setColumns(5);
		fields.add(smallField);
		MultiCollectionPanel.this.add(smallField);
		if (SwingUtilities.getWindowAncestor(MultiCollectionPanel.this) != null) {
		SwingUtilities.getWindowAncestor(MultiCollectionPanel.this).setVisible(true);
		SwingUtilities.getWindowAncestor(MultiCollectionPanel.this).pack();
		}
	}
	private void onMinusPress() {
		if (fields.size() < 1) 
			return;
		MultiCollectionPanel.this.remove(fields.get(fields.size() - 1));
		fields.remove(fields.size() - 1);
		SwingUtilities.getWindowAncestor(MultiCollectionPanel.this).repaint();
		SwingUtilities.getWindowAncestor(MultiCollectionPanel.this).pack();
	}
	public ArrayList<String> getAllTextAsArrayList() {
		ArrayList<String> texts = new ArrayList<String>();
		for (JTextField field: fields) {
			if (field.getText() == null || field.getText().isBlank())
				continue;
			texts.add(field.getText());
		}
		return texts;
	}
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public void clearFields() {
		for (JTextField field: fields) {
			field.setText("");
		}
	}
	
	
}
