package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomButton extends JButton {
	private static final int BTN_WIDTH = 2;
	private static final int BTN_HEIGHT = 1;

	public CustomButton(
			String text,
			JPanel panel,
			GridBagConstraints constraints,
			int row,
			int col,
			boolean enabled,
			ActionListener listener
	) {
		super(text);
		constraints.gridy = row;
		constraints.gridx = col;
		constraints.gridwidth = BTN_WIDTH;
		constraints.gridheight = BTN_HEIGHT;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		setEnabled(enabled);
		addActionListener(listener);
		panel.add(this, constraints);
	}
}
