package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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

        assertEquals(statisticsPanel.getTotalNumberOfPeople().intValue(), 0);
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
