package pimp.logic.parser;

import java.util.regex.PatternSyntaxException;

import pimp.commons.core.Messages;
import pimp.logic.commands.FindRegexCommand;
import pimp.logic.parser.exceptions.ParseException;
import pimp.model.person.NameMatchesRegexPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindRegexCommandParser implements Parser<FindRegexCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRegexCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        try {
            return new FindRegexCommand(new NameMatchesRegexPredicate(trimmed));
        } catch (PatternSyntaxException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
        }
    }

}
