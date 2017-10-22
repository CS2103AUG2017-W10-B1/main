package pimp.logic.commands;

import static pimp.logic.commands.CommandTestUtil.assertCommandSuccess;
import static pimp.logic.commands.CommandTestUtil.showFirstPersonOnly;

import org.junit.Before;
import org.junit.Test;

import pimp.logic.CommandHistory;
import pimp.model.ModelManager;
import pimp.testutil.TypicalPersons;
import pimp.logic.UndoRedoStack;
import pimp.model.Model;
import pimp.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
