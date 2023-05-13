package me.lowen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 5023538511922582993L;

	CardAddingFrame character;
	CardAddingFrame notCharacter;
	JTabbedPane pane;
	public MainFrame() {
		super ("Add a new card: ");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		pane = new JTabbedPane();
		pane.addChangeListener(packListener());
		character = new CardAddingFrame(CardType.CHARACTER);
		notCharacter = new CardAddingFrame(CardType.NOT_CHARACTER);
		pane.addTab("Character", character);
		pane.addTab("Other", notCharacter);
		
		this.add(pane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private ChangeListener packListener() {
		ChangeListener jim = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				
				
				MainFrame.this.setTitle("Add a new card: " + ((CardAddingFrame)pane.getSelectedComponent()).getCardName());
				
			
			}
			
		};
		return jim;
	}
	

}

class ChooseOnePanel extends JPanel {

	private static final long serialVersionUID = -5267966816819754555L;
	
}