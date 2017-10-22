package pimp.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pimp.commons.core.Messages;
import pimp.logic.commands.AddCommand;
import pimp.logic.commands.AddRemoveTagsCommand;
import pimp.logic.commands.ClearCommand;
import pimp.logic.commands.Command;
import pimp.logic.commands.DeleteCommand;
import pimp.logic.commands.EditCommand;
import pimp.logic.commands.ExitCommand;
import pimp.logic.commands.FindCommand;
import pimp.logic.commands.FindRegexCommand;
import pimp.logic.commands.FindTagCommand;
import pimp.logic.commands.HelpCommand;
import pimp.logic.commands.HistoryCommand;
import pimp.logic.commands.ListCommand;
import pimp.logic.commands.RedoCommand;
import pimp.logic.commands.RemarkCommand;
import pimp.logic.commands.SelectCommand;
import pimp.logic.commands.UndoCommand;
import pimp.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindRegexCommand.COMMAND_WORD:
        case FindRegexCommand.COMMAND_ALIAS:
            return new FindRegexCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
        case FindTagCommand.COMMAND_ALIAS:
            return new FindTagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case AddRemoveTagsCommand.COMMAND_WORD:
        case AddRemoveTagsCommand.COMMAND_ALIAS:
            return new AddRemoveTagsCommandParser().parse(arguments);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
