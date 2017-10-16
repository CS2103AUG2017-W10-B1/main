package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindRegexCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameMatchesRegexPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class FindRegexCommandParserTest {

    private FindRegexCommandParser parser = new FindRegexCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRegexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        String[] regexesToTest = new String[] { "abcd", "a   b", "^ab$", "(3[]4)" };
        for (String regex : regexesToTest) {
            FindRegexCommand expectedCommand = new FindRegexCommand(new NameMatchesRegexPredicate(regex));
            assertParseSuccess(parser, regex, expectedCommand);
        }
    }

}
