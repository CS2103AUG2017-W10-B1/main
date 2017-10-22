package pimp.logic.commands;

import static org.junit.Assert.assertEquals;

import static pimp.logic.UndoRedoStackUtil.prepareStack;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import pimp.logic.CommandHistory;
import pimp.logic.UndoRedoStack;
import pimp.logic.commands.exceptions.CommandException;
import pimp.model.Model;
import pimp.model.ModelManager;
import pimp.testutil.TypicalIndexes;
import pimp.testutil.TypicalPersons;
import pimp.model.UserPrefs;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);

    @Before
    public void setUp() throws CommandException {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);

        deleteCommandOne.execute();
        deleteCommandTwo.execute();
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(1);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        String successMessage = UndoCommand.getSuccessMessage(1);

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        CommandTestUtil.deleteFirstPerson(expectedModel);
        CommandTestUtil.assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        CommandTestUtil.assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        CommandTestUtil.assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(2);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        String successMessage = UndoCommand.getSuccessMessage(2);

        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        CommandTestUtil.assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        CommandTestUtil.assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoTooManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(3);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        String successMessage = UndoCommand.getSuccessMessage(2);

        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        CommandTestUtil.assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        CommandTestUtil.assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void getSuccessMessage() {
        assertEquals(UndoCommand.getSuccessMessage(1), "1 command undoed.");
        assertEquals(UndoCommand.getSuccessMessage(2), "2 commands undoed.");
        assertEquals(UndoCommand.getSuccessMessage(12), "12 commands undoed.");
    }
}
