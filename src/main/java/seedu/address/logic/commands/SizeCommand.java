package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FontSizeOutOfBoundsException;

/**
 * Changes the font size.
 */
public class SizeCommand extends Command {

    public static final String COMMAND_WORD = "size";
    public static final String COMMAND_ALIAS = "si";
    public static final String MESSAGE_RESET_FONT_SUCCESS = "Font size successfully reset!";
    public static final String MESSAGE_CHANGE_FONT_SUCCESS = "Font size %1$s by %2$s!";
    public static final String MESSAGE_FAILURE = "New font size out of bounds!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the font size. If no arguments are supplied, resets the font size to its default.\n"
            + "Parameters: [SIZE_CHANGE] (must be an integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final boolean isReset;
    private final int sizeChange;

    public SizeCommand() {
        isReset = true;
        sizeChange = 0;
    }

    public SizeCommand(int sizeChange) {
        isReset = false;
        this.sizeChange = sizeChange;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isReset) {
            model.resetFontSize();
            return new CommandResult(MESSAGE_RESET_FONT_SUCCESS);
        } else {
            try {
                model.updateFontSize(sizeChange);

                if (sizeChange >= 0) {
                    return new CommandResult(String.format(MESSAGE_CHANGE_FONT_SUCCESS, "increased", sizeChange));
                } else {
                    return new CommandResult(String.format(MESSAGE_CHANGE_FONT_SUCCESS, "decreased", -1 * sizeChange));
                }
            } catch (FontSizeOutOfBoundsException e) {
                throw new CommandException(MESSAGE_FAILURE);
            }
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SizeCommand // instanceof handles nulls
                && this.sizeChange == ((SizeCommand) other).sizeChange
                && this.isReset == ((SizeCommand) other).isReset); // state check
    }
}
