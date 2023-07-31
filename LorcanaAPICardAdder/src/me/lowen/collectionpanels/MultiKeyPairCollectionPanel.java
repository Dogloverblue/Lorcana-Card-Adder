package me.lowen.collectionpanels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MultiKeyPairCollectionPanel extends JPanel{

	private static final long serialVersionUID = 7295713605549639796L;

	private ArrayList<KeyPairCollectionPanel> fields;
	
	private String whatKey;
	private String whatValue;
	private String key;
	public MultiKeyPairCollectionPanel(String what, String whatKey, String whatValues, int numOfDefaultBoxes, String key) {
		this.key = key;
		fields = new ArrayList<KeyPairCollectionPanel>();
		this.whatKey = whatKey;
		this.whatValue = whatValues;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		firstPanel.add(new JLabel(what));
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
		
		firstPanel.add(plus);
		firstPanel.add(minus);
		this.add(firstPanel);
		for (int i = 0; i < numOfDefaultBoxes; i++) {
			onPlusPress();
		}
		
		
	}
	
	private void onPlusPress() {
		KeyPairCollectionPanel smallField = new KeyPairCollectionPanel(whatKey, whatValue);
		fields.add(smallField);
		MultiKeyPairCollectionPanel.this.add(smallField);
		if (SwingUtilities.getWindowAncestor(MultiKeyPairCollectionPanel.this) != null) {
		SwingUtilities.getWindowAncestor(MultiKeyPairCollectionPanel.this).setVisible(true);
		SwingUtilities.getWindowAncestor(MultiKeyPairCollectionPanel.this).pack();
		}
	}
	private void onMinusPress() {
		if (fields.size() < 1) 
			return;
		MultiKeyPairCollectionPanel.this.remove(fields.get(fields.size() - 1));
		fields.remove(fields.size() - 1);
		SwingUtilities.getWindowAncestor(MultiKeyPairCollectionPanel.this).repaint();
		SwingUtilities.getWindowAncestor(MultiKeyPairCollectionPanel.this).pack();
	}
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public HashMap<String, String> getValuesAsHashMap() {
		HashMap<String, String> mapToReturn = new HashMap<>();
		for (KeyPairCollectionPanel kpcp: fields) {
			if (kpcp.getValue() == null || kpcp.getValue().isBlank())
				continue;
			mapToReturn.put(kpcp.getKeyForKeyValuePair(), kpcp.getValue());
		}
		return mapToReturn;
	}
	
	public void removeAllFields() {
		// no increment for statement, not something you see every day
		for (int i = 0; i < fields.size();) {
			onMinusPress();
		}
	}

}

class KeyPairCollectionPanel extends JPanel {

	private static final long serialVersionUID = 6607689557993141878L;
	
	JTextField keyField;
	JTextField valueField;
	
	public KeyPairCollectionPanel(String whatKey, String whatValue) {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		this.setLayout(layout);
		
		keyField = new JTextField();
		
		valueField = new JTextField();
		 keyField.setColumns(7);
		 valueField.setColumns(7);
		 
		 this.add(new JLabel("   " + whatKey));
		 this.add(keyField);
		 this.add(new JLabel(whatValue));
		 this.add(valueField);
	}
	public String getKeyForKeyValuePair() {
		return keyField.getText();
	}
	public String getValue() {
		return valueField.getText();
	}
	
	
	
}
