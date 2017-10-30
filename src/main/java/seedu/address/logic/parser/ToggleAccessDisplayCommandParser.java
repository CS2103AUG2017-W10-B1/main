package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWITTER;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ToggleAccessDisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AccessCount;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ToggleAccessDisplayCommandParser implements Parser<ToggleAccessDisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleAccessDisplayCommand
     * and returns an ToggleAccessDisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleAccessDisplayCommand parse(String args) throws ParseException {
        if (args.trim().equalsIgnoreCase("on")) {
            return new ToggleAccessDisplayCommand(true);
        }
        else if (args.trim().equalsIgnoreCase("off")) {
            return new ToggleAccessDisplayCommand(false);
        }
        else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleAccessDisplayCommand.MESSAGE_USAGE));
        }
    }

}
