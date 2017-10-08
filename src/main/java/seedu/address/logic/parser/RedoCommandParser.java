package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns an RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        try {
            int amount = ParserUtil.parsePositiveInteger(args);
            return new RedoCommand(amount);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
    }
}
