package pimp.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pimp.commons.core.Messages;
import pimp.commons.core.index.Index;
import pimp.logic.CommandHistory;
import pimp.logic.UndoRedoStack;
import pimp.model.AddressBook;
import pimp.model.Model;
import pimp.model.ModelManager;
import pimp.model.person.Person;
import pimp.testutil.PersonBuilder;
import pimp.testutil.TypicalIndexes;
import pimp.testutil.TypicalPersons;
import pimp.model.UserPrefs;
import pimp.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Hihi").build();

        RemarkCommand remarkCommand = prepareCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(TypicalIndexes.INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, CommandTestUtil.VALID_REMARK_BOB);

        CommandTestUtil.assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        CommandTestUtil.showFirstPersonOnly(model);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, CommandTestUtil.VALID_REMARK_BOB);

        CommandTestUtil.assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new Remark(CommandTestUtil.VALID_REMARK_AMY));

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        RemarkCommand commandWithSameValues = new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new Remark(CommandTestUtil.VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        RemarkCommand commandWithDifferentIndex = new RemarkCommand(TypicalIndexes.INDEX_SECOND_PERSON,
                new Remark(CommandTestUtil.VALID_REMARK_AMY));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different remarks
        RemarkCommand commandWithDifferentRemarks = new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new Remark(CommandTestUtil.VALID_REMARK_BOB));
        assertFalse(standardCommand.equals(commandWithDifferentRemarks));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
