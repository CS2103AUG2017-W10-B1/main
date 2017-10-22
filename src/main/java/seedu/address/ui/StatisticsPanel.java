package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class StatisticsPanel extends UiPart<Region>{
    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private PieChart pieChart;

    public StatisticsPanel() {
        super(FXML);
    }
}
