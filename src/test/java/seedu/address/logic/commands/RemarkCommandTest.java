package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY),
                model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(),
                        new Remark(VALID_REMARK_AMY)));
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        RemarkCommand commandWithDifferentIndex = new RemarkCommand(INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different remarks
        RemarkCommand commandWithDifferentRemarks = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_BOB));
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
