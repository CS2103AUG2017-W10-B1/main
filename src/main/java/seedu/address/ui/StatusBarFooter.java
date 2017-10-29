package seedu.address.ui;

import java.time.Clock;
import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar totalPersons;
    @FXML
    private StatusBar newPersons;

    public StatusBarFooter(String saveLocation, int totalPersons, int newPersons) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        setTotalPersons(totalPersons);
        setNewPersons(newPersons);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    private void setTotalPersons(int totalPersons) {
        String totalPersonsString;
        if (totalPersons <= 1) {
            totalPersonsString = "Total: " + totalPersons + " person";
        } else {
            totalPersonsString = "Total: " + totalPersons + " persons";
        }
        this.totalPersons.setText(totalPersonsString);
    }

    private void setNewPersons(int newPersons) {
        String newPersonsString;
        if (newPersons <= 0) {
            newPersonsString = "New: " + newPersons + " person";
        } else {
            newPersonsString = "New: " + newPersons + " persons";
        }
        this.newPersons.setText(newPersonsString);
    }

    /**
     * Resets the font sizes of this class.
     */
    public void resetFontSize() {
        // syncStatus.setStyle("-fx-font-size: 12pt");
        // saveLocationStatus.setStyle("-fx-font-size: 12pt");
        // totalPersons.setStyle("-fx-font-size: 12pt");
        // newPersons.setStyle("-fx-font-size: 12pt");
    }

    /**
     * Changes the font sizes of this class by {@code change}.
     */
    public void changeFontSize(int change) {
        // syncStatus.setStyle("-fx-font-size: 20pt");
        // saveLocationStatus.setStyle("-fx-font-size: 20pt");
        // totalPersons.setStyle("-fx-font-size: 20pt");
        // newPersons.setStyle("-fx-font-size: 20pt");
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(abce.data.getPersonList().size());
        setNewPersons(abce.data.getPersonList().filtered(t-> {
            Date givenDate = t.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));
            ZonedDateTime ref = Instant.now().atZone(ZoneId.of("UTC"));
            return Month.from(given) == Month.from(ref) && Year.from(given).equals(Year.from(ref));
        }).size());
    }
}
