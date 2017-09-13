package SwingComponents;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import main.Player;

public class ChecklistPanel extends JPanel {

	private ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
	private JCheckBox scarlet = new JCheckBox("Miss Scarlet", false);
	private JCheckBox mustard = new JCheckBox("Colonel Mustard", false);
	private JCheckBox white = new JCheckBox("Mrs White", false);
	private JCheckBox green = new JCheckBox("Rev Green", false);
	private JCheckBox peacock = new JCheckBox("Mrs Peacock", false);
	private JCheckBox plum = new JCheckBox("Professor Plum", false);
	private JCheckBox lounge = new JCheckBox("Lounge", false);
	private JCheckBox conservatory = new JCheckBox("Conservatory", false);
	private JCheckBox dining = new JCheckBox("Dining Room", false);
	private JCheckBox hall = new JCheckBox("Hall", false);
	private JCheckBox library = new JCheckBox("Library", false);
	private JCheckBox billiard = new JCheckBox("Billiard Room", false);
	private JCheckBox ballroom = new JCheckBox("Ball Room", false);
	private JCheckBox study = new JCheckBox("Study", false);
	private JCheckBox kitchen = new JCheckBox("Kitchen", false);
	private JCheckBox spanner = new JCheckBox("Spanner", false);
	private JCheckBox candlestick = new JCheckBox("Candlestick", false);
	private JCheckBox dagger = new JCheckBox("Dagger", false);
	private JCheckBox leadpipe = new JCheckBox("Lead Pipe", false);
	private JCheckBox revolver = new JCheckBox("Revolver", false);
	private JCheckBox rope = new JCheckBox("Rope", false);

	private Player currentPlayer;

	public ChecklistPanel() {
		initializeList();
	}

	/**
	 * Adds check box objects unto the list and adds it to the panel
	 */

	public void initializeList() {
		checkboxList.add(scarlet);
		checkboxList.add(white);
		checkboxList.add(mustard);
		checkboxList.add(green);
		checkboxList.add(peacock);
		checkboxList.add(plum);
		checkboxList.add(lounge);
		checkboxList.add(conservatory);
		checkboxList.add(dining);
		checkboxList.add(hall);
		checkboxList.add(library);
		checkboxList.add(billiard);
		checkboxList.add(ballroom);
		checkboxList.add(kitchen);
		checkboxList.add(study);
		checkboxList.add(spanner);
		checkboxList.add(revolver);
		checkboxList.add(dagger);
		checkboxList.add(leadpipe);
		checkboxList.add(candlestick);
		checkboxList.add(rope);
		for (JCheckBox checkBox : checkboxList) {
			this.add(checkBox);
		}
	}

	/**
	 * Returns the list of check boxes
	 * @return
	 */

	public ArrayList<JCheckBox> getCheckboxList() {
		return checkboxList;
	}

	/**
	 * updates the check box of the current player
	 *
	 * @param player
	 */

	public void updateCheckbox(Player player) {
		this.currentPlayer = player;
		if (currentPlayer.getRemovedSuspects().size() > 0) {
			for (String name : currentPlayer.getRemovedSuspects()) {
				for (JCheckBox checkbox : checkboxList) {
					if (name.equals(checkbox.getText())) {
						checkbox.setSelected(true);
					}
				}
			}
		}
	}

	/**
	 * This resets all the ticked check boxes
	 */

	public void unCheckAllBoxes() {
		for (JCheckBox checkbox : checkboxList) {
			checkbox.setSelected(false);
		}
	}

	/**
	 * This allows to hide/show the check list
	 *
	 * @param b
	 */

	public void setVisibility(boolean b) {
		for (JCheckBox checkbox : checkboxList) {
			checkbox.setVisible(b);
		}
	}

}
