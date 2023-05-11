package me.lowen;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 5023538511922582993L;

	public MainFrame() {
		JTabbedPane pane = new JTabbedPane();
		CardAddingFrame character = new CardAddingFrame(CardType.CHARACTER);
		CardAddingFrame notCharacter = new CardAddingFrame(CardType.NOT_CHARACTER);
		pane.addTab("Character", character);
		pane.addTab("Other", notCharacter);
		
		this.add(pane);
	}

}
