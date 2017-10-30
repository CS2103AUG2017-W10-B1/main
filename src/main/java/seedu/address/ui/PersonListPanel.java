package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.AccessCountDisplayToggleEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    private ObservableList<PersonCard> mappedListWithAccessCount;
    private ObservableList<PersonCard> mappedListWithoutAccessCount;

    public PersonListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        mappedListWithAccessCount = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1,
                        "PersonListCard.fxml", true));
        mappedListWithoutAccessCount = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1,
                        "PersonListCardAccess.fxml", false));
        personListView.setItems(mappedListWithAccessCount);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                        if (oldValue == null || oldValue.person.getName() != newValue.person.getName()) {
                            newValue.person.incrementAccess();
                        }
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Resets the font sizes of this class.
     */
    public void resetFontSize() {
        PersonCard.resetFontSize();
        for (PersonCard pc : personListView.getItems()) {
            pc.refreshFontSizes();
        }
        personListView.refresh();
    }

    /**
     * Changes the font sizes of this class by {@code change}.
     */
    public void changeFontSize(int change) {
        PersonCard.changeFontSize(change);
        for (PersonCard pc : personListView.getItems()) {
            pc.refreshFontSizes();
        }
        personListView.refresh();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleAccessCountDisplayToggleEvent(AccessCountDisplayToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + event.isDisplayed());
        if (event.isDisplayed()) {
            personListView.setItems(mappedListWithAccessCount);
        } else {
            personListView.setItems(mappedListWithoutAccessCount);
        }
        personListView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
