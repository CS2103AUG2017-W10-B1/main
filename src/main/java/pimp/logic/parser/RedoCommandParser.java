package pimp.logic.parser;

import pimp.commons.core.Messages;
import pimp.commons.exceptions.IllegalValueException;
import pimp.logic.commands.RedoCommand;
import pimp.logic.parser.exceptions.ParseException;

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
        if (args.equals("")) {
            return new RedoCommand(1);
        }

        try {
            int amount = ParserUtil.parsePositiveInteger(args);
            return new RedoCommand(amount);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
    }
}
