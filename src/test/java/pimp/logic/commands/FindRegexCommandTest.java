package pimp.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pimp.commons.core.Messages;
import pimp.logic.CommandHistory;
import pimp.logic.UndoRedoStack;
import pimp.model.AddressBook;
import pimp.model.Model;
import pimp.model.ModelManager;
import pimp.model.person.NameMatchesRegexPredicate;
import pimp.model.person.ReadOnlyPerson;
import pimp.testutil.TypicalPersons;
import pimp.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindRegexCommand}.
 */
public class FindRegexCommandTest {
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameMatchesRegexPredicate firstPredicate = new NameMatchesRegexPredicate("first");
        NameMatchesRegexPredicate secondPredicate = new NameMatchesRegexPredicate("second");

        FindRegexCommand findFirstCommand = new FindRegexCommand(firstPredicate);
        FindRegexCommand findSecondCommand = new FindRegexCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindRegexCommand findFirstCommandCopy = new FindRegexCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_regex_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindRegexCommand command = prepareCommand("Ku[rn]z$");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(TypicalPersons.CARL, TypicalPersons.FIONA));
    }

    @Test
    public void execute_regex_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindRegexCommand command = prepareCommand("^x+$");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindRegexCommand}.
     */
    private FindRegexCommand prepareCommand(String userInput) {
        FindRegexCommand command = new FindRegexCommand(new NameMatchesRegexPredicate(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindRegexCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
