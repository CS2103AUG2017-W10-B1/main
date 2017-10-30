package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class StatisticsPanelTest extends GuiUnitTest {
    private Model model;
    private Logic logic;

    @Test
    public void getTotalNumberOfPeopleTest() {
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());

        StatisticsPanel statisticsPanel = new StatisticsPanel(expectedAddressBook.getPersonList());

        assertEquals(statisticsPanel.getTotalNumberOfPeople().intValue(), 9);
    }

    @Test
    public void tabulateTotalNumberOfPeopleTest() {
        this.model = new ModelManager();
        this.logic = new LogicManager(this.model);

        StatisticsPanel statisticsPanel = new StatisticsPanel(logic.getAllPersonList());

        assertEquals(logic.getAllPersonList().size(), 0);
    }

    @Test
    public void calculateCountByMonthOffsetTest() {

        this.model = new ModelManager();
        this.logic = new LogicManager(this.model);

        StatisticsPanel statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 12, 2015);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(12, 2015), 0);

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 12, 2016);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(12, 2015), 12);

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 12, 2017);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(1, 2015), 35);

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 1, 2016);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(12, 2015), 1);

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 12, 2018);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(12, 2015), 36);

        statisticsPanel = new StatisticsPanel(logic.getAllPersonList(), 3, 2017);
        assertEquals(statisticsPanel.calculateCountByMonthOffset(5, 2016), 10);

    }
}
