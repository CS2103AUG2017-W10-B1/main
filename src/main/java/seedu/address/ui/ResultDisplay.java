package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private static final int DEFAULT_FONT_SIZE = 17;
    private static int fontSizeChange = 0;

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

    /**
     * Changes the font size of all text inside this class by the amount of {@code change}.
     * Note that existing cards will not have its font size changed. Call {@code refreshFontSizes}
     * on existing cards to update their fonts.
     */
    public static void changeFontSize(int change) {
        fontSizeChange = change;
    }

    /**
     * Resets the font size of all text inside this class into its defaults.
     * Note that existing cards will not have its font size changed. Call {@code refreshFontSizes}
     * on existing cards to update their fonts.
     */
    public static void resetFontSize() {
        fontSizeChange = 0;
    }

    /**
     * Updates the font size of this card.
     */
    public void refreshFontSizes() {
        resultDisplay.setStyle("-fx-font-size: " + (DEFAULT_FONT_SIZE + fontSizeChange));
    }
}
