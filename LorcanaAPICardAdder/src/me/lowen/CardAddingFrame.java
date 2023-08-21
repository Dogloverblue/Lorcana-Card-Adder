package me.lowen;


import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.JSONException;
import org.json.JSONObject;

import me.lowen.collectionpanels.BooleanCollectionPanel;
import me.lowen.collectionpanels.MultiCollectionPanel;
import me.lowen.collectionpanels.MultiKeyPairCollectionPanel;
import me.lowen.collectionpanels.MultiStringSelectionPanel;
import me.lowen.collectionpanels.NumberCollectionPanel;
import me.lowen.collectionpanels.SingleCollectionPanel;
import me.lowen.ezintegrate.EZIntegrater;

public class CardAddingFrame extends JPanel {

	private static final long serialVersionUID = -7060100486068295831L;
	
	SingleCollectionPanel nameC;
	JPanel mainPanel;
	File directory;
	EZIntegrater ezI;
	CardType type;
	SingleCollectionPanel typePanel = null;
	public CardAddingFrame(CardType type) {
//		super("Add a new card: ");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		try {
			directory = new File(SettingsManager.getSavePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.type = type;
//		ezI = new EZIntegrater(directory);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		nameC = new SingleCollectionPanel("Name", "name");
		nameC.getField().addKeyListener(this.sillyThing());
		
		mainPanel.add(nameC);
		
		if (type == CardType.CHARACTER) {
			assembleForCharacter();
		} else {
			assembleForNotCharacter();
		}
		JPanel directoryChooserPanel = new JPanel();
		directoryChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JTextField fileBox = new JTextField();
		fileBox.setText(SettingsManager.getSavePath());
		JButton directoryButton = new JButton("Choose Path");
		directoryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int fileCode = jfc.showSaveDialog(null);
				if (fileCode == JFileChooser.APPROVE_OPTION) {
					SettingsManager.setSavePath(jfc.getSelectedFile().getAbsolutePath());
					fileBox.setText(jfc.getSelectedFile().getAbsolutePath());
					directory = jfc.getSelectedFile();
					SwingUtilities.getWindowAncestor(mainPanel).pack();
					}
				
			}
		});
		directoryChooserPanel.add(fileBox);
		directoryChooserPanel.add(directoryButton);
		mainPanel.add(directoryChooserPanel);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				directory = new File(fileBox.getText());
				CardAddingFrame.this.saveTo(directory);
			}
			
		});
		JButton saveAndClearButton = new JButton("Save and clear");
		saveAndClearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardAddingFrame.this.saveTo(directory);
				CardAddingFrame.this.clearAllFields();
				inkable.setSelected(true);
				if (typePanel != null) {
					typePanel.setText("Character");
				}
			}
			
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAndClearButton);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
//		this.pack();
//		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	public String getCardName() {
		return nameC.getText();
	}
	
	String[] colors = {"Amber", "Amethyst", "Emerald", "Ruby", "Sapphire", "Steel"};
	String[] rarities = {"Common", "Uncommon", "Rare", "Super Rare", "Legendary"};
	
	BooleanCollectionPanel inkable = new BooleanCollectionPanel("Is Inkable", "inkable");
	// I was consider making one different class for each, but I think this should work just fine
	private void assembleForCharacter() {
		this.addComponent(new SingleCollectionPanel("Subtitle", "subtitle"));
		this.addComponent(new NumberCollectionPanel("Ink Cost", "ink-cost"));
		inkable.setSelected(true);
		this.addComponent(inkable);
		this.addComponent(new MultiStringSelectionPanel(colors, "Color", "color"));
		this.addComponent(new NumberCollectionPanel("Lore Amount", "lore-value"));
		typePanel = new SingleCollectionPanel("Type", "type");
		typePanel.setText("Character");
		this.addComponent(typePanel);
		this.addComponent(new MultiCollectionPanel("Subtypes", 3, "subtypes"));
		this.addComponent(new NumberCollectionPanel("Strength", "strength"));
		this.addComponent(new NumberCollectionPanel("Willpower", "willpower"));
		this.addComponent(new MultiKeyPairCollectionPanel("Abilities", "name", "effect", 0, "abilities"));
		this.addComponent(new MultiCollectionPanel("Traits", 0, "traits"));
		this.addComponent(new SingleCollectionPanel("Flavor Text", "flavor-text"));
		this.addComponent(new MultiStringSelectionPanel(rarities, "Rarity", "rarity"));
		this.addComponent(new SingleCollectionPanel("Artist", "artist"));
		this.addComponent(new SingleCollectionPanel("Set", "set"));
		this.addComponent(new SingleCollectionPanel("Set Code", "set-code"));
		

	}
	private void assembleForNotCharacter() {
		this.addComponent(new NumberCollectionPanel("Ink Cost", "ink-cost"));
		inkable.setSelected(true);
		this.addComponent(inkable);
		this.addComponent(new MultiStringSelectionPanel(colors, "Color", "color"));
		this.addComponent(new SingleCollectionPanel("Type", "type"));
		this.addComponent(new SingleCollectionPanel("Effect", "effect"));
		this.addComponent(new MultiKeyPairCollectionPanel("Abilities", "name", "effect", 0, "abilities"));
		this.addComponent(new SingleCollectionPanel("Flavor Text", "flavor-text"));
		this.addComponent(new MultiStringSelectionPanel(rarities, "Rarity", "rarity"));
		this.addComponent(new SingleCollectionPanel("Artist", "artist"));
		this.addComponent(new SingleCollectionPanel("Set", "set"));
		this.addComponent(new SingleCollectionPanel("Set Code", "set-code"));
	}
	
	private void addComponent(Component c) {
		mainPanel.add(c);
	}

	public KeyListener sillyThing() {
		KeyListener hi = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// makes the updating slightly snappier for single digit entries. Instead of waiting for the key to be released,
				// it will be updated quicker if it is a-z, 0-9. doesn't work for deletions or pasting
				if (Character.isAlphabetic(e.getKeyChar()) || Character.isDigit(e.getKeyChar())) {
					((JFrame)SwingUtilities.getWindowAncestor(CardAddingFrame.this)).setTitle(((JFrame)SwingUtilities.getWindowAncestor(CardAddingFrame.this)).getTitle() + e.getKeyChar());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			((JFrame)SwingUtilities.getWindowAncestor(CardAddingFrame.this)).setTitle("Add a new card: " + nameC.getText());
				
			}
			
		};
		return hi;
		
	}
	
	public void saveTo(File directoryPath) {
		ezI = new EZIntegrater(directoryPath);
		JSONObject jo = new JSONObject();
		for (Component e : mainPanel.getComponents()) {
			if (e instanceof SingleCollectionPanel) {
				jo.put(((SingleCollectionPanel)e).getKey(), ((SingleCollectionPanel)e).getText());
			}
			if (e instanceof MultiCollectionPanel) {
				MultiCollectionPanel ea = (MultiCollectionPanel) e;
				jo.put(ea.getKey(), ea.getAllTextAsArrayList());
			}
			if (e instanceof BooleanCollectionPanel) {
				jo.put(((BooleanCollectionPanel)e).getKey(), ((BooleanCollectionPanel)e).getValue());
			}
			if (e instanceof MultiKeyPairCollectionPanel) {
				MultiKeyPairCollectionPanel ea = (MultiKeyPairCollectionPanel) e;
				jo.put(ea.getKey(), ea.getValuesAsHashMap());
			}
			if (e instanceof MultiStringSelectionPanel) {
				jo.put(((MultiStringSelectionPanel)e).getKey(), ((MultiStringSelectionPanel)e).getValue());
			}
			if (e instanceof NumberCollectionPanel) {
				jo.put(((NumberCollectionPanel)e).getKey(), ((NumberCollectionPanel)e).getValue());
			}
		}
		
		BufferedWriter br;
		try {
			try {
			br = new BufferedWriter(new FileWriter(directoryPath + System.getProperty("file.separator") + jo.getString("name").toLowerCase().replace(" ", "_") 
					+ "-" + jo.getString("subtitle").toLowerCase().replace(" ", "_") +".txt"));
			jo.put("image-urls", CardAdderUtils.getImagesURLsForCharacter(jo.getString("name").toLowerCase().replace(" ", "_"), jo.getString("subtitle").toLowerCase().replace(" ", "_")));
			jo.write(br, 2, 2);
			br.close();
			ezI.integrateJSONObject(jo);
			} catch (JSONException e) {
//				e.printStackTrace();
				br = new BufferedWriter(new FileWriter(directoryPath + System.getProperty("file.separator") + jo.getString("name").toLowerCase().replace(" ", "_") + ".txt"));
				jo.put("image-urls", CardAdderUtils.getImagesURLsForNonCharacter(jo.getString("name").toLowerCase().replace(" ", "_")));
				jo.write(br, 2, 2);
				br.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
	}
	
	public void clearAllFields() {
		for (Component e : mainPanel.getComponents()) {
			if (e instanceof SingleCollectionPanel) {
				((SingleCollectionPanel)e).setText("");
			}
			if (e instanceof MultiCollectionPanel) {
				((MultiCollectionPanel)e).clearFields();
			}
			if (e instanceof BooleanCollectionPanel) {
				((BooleanCollectionPanel)e).setSelected(false);
			}
			if (e instanceof MultiKeyPairCollectionPanel) {
				((MultiKeyPairCollectionPanel)e).removeAllFields();
			}
			if (e instanceof MultiStringSelectionPanel) {
				((MultiStringSelectionPanel)e).setToDefaultValue();
			}
			if (e instanceof NumberCollectionPanel) {
				((NumberCollectionPanel)e).setValue(0);
			}
		}
		
	}
	
	

}
