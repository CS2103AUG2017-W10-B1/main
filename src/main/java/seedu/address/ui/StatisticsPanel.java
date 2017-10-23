package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Statistics Panel of the App.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";

    @FXML
    private PieChart pieChart;

    public StatisticsPanel(ObservableList<ReadOnlyPerson> list) {
        super(FXML);
    }
}
