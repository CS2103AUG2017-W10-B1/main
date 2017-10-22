package pimp.logic.parser;

import java.util.Arrays;

import org.junit.Test;

import pimp.commons.core.Messages;
import pimp.logic.commands.FindCommand;
import pimp.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        CommandParserTestUtil.assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        CommandParserTestUtil.assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
