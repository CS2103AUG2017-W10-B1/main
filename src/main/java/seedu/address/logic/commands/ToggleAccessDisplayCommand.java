package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.AccessCountDisplayToggleEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Toggles the display of the access count.
 */
public class ToggleAccessDisplayCommand extends Command {
    public static final String COMMAND_WORD = "accessdisplay";
    public static final String COMMAND_ALIAS = "ad";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles the display of the access count.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Display toggled";

    boolean isDisplayed;

    public ToggleAccessDisplayCommand (boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new AccessCountDisplayToggleEvent(isDisplayed));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
