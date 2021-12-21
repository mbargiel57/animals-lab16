package gui;

import simulation.SimulationParams;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class CustomTextField extends JFormattedTextField {
	private static final int DEFAULT_WIDTH = 1;
	private static final int DEFAULT_HEIGHT = 1;
	private static final CustomNumberFormatter formatter = new CustomNumberFormatter();

	private static class CustomNumberFormatter extends NumberFormatter {
		CustomNumberFormatter() {
			super(NumberFormat.getIntegerInstance());
			setValueClass(Integer.class);
			setAllowsInvalid(false);
			setMinimum(0);
		}
	}

	public CustomTextField(
		  String fieldName,
		  String value,
		  int row,
		  int col,
		  GridBagConstraints constraints,
		  JPanel panel
	) {
		super(new DefaultFormatterFactory(formatter), Integer.valueOf(value));

		// add label
		JLabel label = new JLabel(fieldName + ":");
		constraints.gridwidth = DEFAULT_WIDTH;
		constraints.gridheight = DEFAULT_HEIGHT;
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(label, constraints);

		// add text field
		initListener(fieldName);
		setFont(new Font("Dialog", Font.BOLD, 12));
		setBackground(CustomColors.TEXT_BACKGROUND_COLOR);
		Border margins = BorderFactory.createEmptyBorder(5, 5, 5, 0);
		Border border = BorderFactory.createLineBorder(CustomColors.BORDER_COLOR, 2);
		setBorder(new CompoundBorder(border, margins));
		constraints.gridx = col + 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		panel.add(this, constraints);
	}

	private void initListener(String fieldName) {
		getDocument().addDocumentListener((IDocumentListener) e -> {
			String value = getText();
			if (value != null && !value.equals("")) {
				SimulationParams.setField(fieldName, Integer.parseInt(value));
			}
		});
	}
}
