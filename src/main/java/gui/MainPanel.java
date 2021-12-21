package gui;

import simulation.JsonParser;
import simulation.Simulation;
import simulation.SimulationParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
	private final MapPanel mapPanel;
	private Timer timer;

	private final GridBagConstraints constraints = new GridBagConstraints();
	private final CustomTextArea statsInput;

	private int nextRow = 0;

	private final static int DEFAULT_COL = 0;
	private final static int MAP_ROW = 0;
	private final static int MAP_COL = 2;
	private final static String STATS_FILE = "stats.json";

	public MainPanel() {
		initLayout();

		// Create buttons
		CustomButton startBtn = createButton("Start new game", e -> startApp());
		CustomButton stopBtn = createButton("Stop game", e -> stopApp());
		CustomButton continueBtn = createButton("Continue game", e -> continueApp());
		CustomButton dumpToJsonBtn = createButton("Dump to JSON", e -> dumpStatistics());

		// add text fields
		SimulationParams.getParamsMap().forEach((fieldName, value) ->
				new CustomTextField(fieldName, Integer.toString(value), nextRow++,
				DEFAULT_COL, constraints, this)
		);

		// add stats text area
		statsInput = new CustomTextArea(
				"Press 'start new game'\nto reset game",
				LayoutConstants.TEXT_AREA_HEIGHT,
				LayoutConstants.TEXT_AREA_WIDTH,
				nextRow++,
				DEFAULT_COL,
				this,
				constraints
		);

		// add map panel
		mapPanel = new MapPanel(Simulation.getWorldMap());
		addMapPanel();
	}

	private void initLayout() {
		setLayout(new GridBagLayout());
		setBackground(CustomColors.BACKGROUND_COLOR);
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.insets = new Insets(
				LayoutConstants.TOP_MARGIN,
				LayoutConstants.LEFT_MARGIN,
				LayoutConstants.BOTTOM_MARGIN,
				LayoutConstants.RIGHT_MARGIN
		);
	}

	private CustomButton createButton(String text, ActionListener listener) {
		return new CustomButton(text, this, constraints,
			nextRow++, DEFAULT_COL, true, listener);
	}

	private void addMapPanel() {
		constraints.gridx = MAP_COL;
		constraints.gridy = MAP_ROW;
		constraints.gridwidth = 1;
		constraints.gridheight = nextRow + 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 1;
		mapPanel.setPreferredSize(new Dimension(
			LayoutConstants.MAP_SCALE * Simulation.getWorldMap().getWidth(),
			LayoutConstants.MAP_SCALE * Simulation.getWorldMap().getHeight()));
		add(mapPanel, constraints);
	}

	private void renderMap() {
		if (Simulation.getWorldMap().getAnimalsPositions().isEmpty()) {
			timer.stop();
			return;
		}
		Simulation.simulateDay();
		mapPanel.repaint();
		statsInput.setText(Simulation.getWorldMap().getStatistics().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		renderMap();
	}

	private void startApp() {
		if (timer != null && timer.isRunning()) timer.stop();
		Simulation.setSimulation();
		timer = new Timer(1000 / SimulationParams.getField("speed"), this);
		timer.start();
	}

	private void stopApp() {
		if (timer != null && timer.isRunning()) timer.stop();
	}

	private void continueApp() {
		if (timer != null && !timer.isRunning()) timer.start();
	}

	private void dumpStatistics() {
		JsonParser.dumpStatisticsToJsonFile(STATS_FILE,
			Simulation.getWorldMap().getStatistics());
	}
}
