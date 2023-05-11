package me.lowen;


import java.awt.Component;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.JSONObject;

import me.lowen.collectionpanels.BooleanCollectionPanel;
import me.lowen.collectionpanels.MultiCollectionPanel;
import me.lowen.collectionpanels.MultiKeyPairCollectionPanel;
import me.lowen.collectionpanels.MultiStringSelectionPanel;
import me.lowen.collectionpanels.NumberCollectionPanel;
import me.lowen.collectionpanels.SingleCollectionPanel;

public class CardAddingFrame extends JFrame {

	private static final long serialVersionUID = -7060100486068295831L;
	
	SingleCollectionPanel nameC;
	JPanel mainPanel;
	public CardAddingFrame(CardType type) {
		super("Add a new card: ");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardAddingFrame.this.saveTo(new File("C:\\Users\\jnlowen_wccnet\\Documents\\Typed Lorcana Cards\\CardTest.txt"));
			}
			
		});
		JButton saveAndClearButton = new JButton("Save and clear");
		saveAndClearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardAddingFrame.this.saveTo(new File("C:\\Users\\jnlowen_wccnet\\Documents\\Typed Lorcana Cards\\CardTest.txt"));
				CardAddingFrame.this.clearAllFields();
			}
			
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAndClearButton);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	String[] colors = {"Amber", "Amethyst", "Emerald", "Ruby", "Sapphire", "Steel"};
	String[] rarities = {"Common", "Uncommon", "Rare", "Super Rare", "Legendary"};
	
	// I was consider making one different class for each, but I think this should work just fine
	private void assembleForCharacter() {
		this.addComponent(new NumberCollectionPanel("Ink Cost", "ink-cost"));
		this.addComponent(new BooleanCollectionPanel("Is Inkable", "inkable"));
		this.addComponent(new MultiStringSelectionPanel(colors, "Color", "color"));
		this.addComponent(new NumberCollectionPanel("Lore Amount", "lore-value"));
		SingleCollectionPanel typePanel = new SingleCollectionPanel("Type", "type");
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
		this.addComponent(new BooleanCollectionPanel("Is Inkable", "inkable"));
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
				CardAddingFrame.this.setTitle(CardAddingFrame.this.getTitle() + e.getKeyChar());
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				CardAddingFrame.this.setTitle("Add a new card: " + nameC.getText());
			}
			
		};
		return hi;
		
	}
	
	public void saveTo(File file) {
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
		jo.put("image-urls", CardAdderUtils.getImagesURLs(jo.getString("name").toLowerCase().replace(" ", "_")));
		BufferedWriter br;
		try {
			br = new BufferedWriter(new FileWriter(file));
			jo.write(br, 2, 2);
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
