package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;

/**
 * Changes the font size.
 */
public class SizeCommand extends Command {

    public static final String COMMAND_WORD = "size";
    public static final String COMMAND_ALIAS = "si";
    public static final String MESSAGE_RESET_FONT_SUCCESS = "Font size successfully reset!";
    public static final String MESSAGE_CHANGE_FONT_SUCCESS = "Font size %1$s by %2$s!";
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
    public CommandResult execute() {
        if (isReset) {
            EventsCenter.getInstance().post(new FontSizeChangeRequestEvent());
            return new CommandResult(MESSAGE_RESET_FONT_SUCCESS);
        } else {
            EventsCenter.getInstance().post(new FontSizeChangeRequestEvent(sizeChange));
            String changeText = sizeChange >= 0 ? "increased" : "decreased";
            return new CommandResult(String.format(MESSAGE_CHANGE_FONT_SUCCESS, changeText, sizeChange));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SizeCommand // instanceof handles nulls
                && this.sizeChange == ((SizeCommand) other).sizeChange); // state check
    }
}
