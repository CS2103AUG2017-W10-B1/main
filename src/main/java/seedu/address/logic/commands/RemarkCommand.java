package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a remark to a person
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of a person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_REMARK
            + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to eat duck";

    public static final String UNIMPLEMENTED_MESSAGE = "Remark feature not implemented yet";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(UNIMPLEMENTED_MESSAGE);
    }
}
