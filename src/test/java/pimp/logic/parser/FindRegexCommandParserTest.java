package pimp.logic.parser;

import static pimp.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pimp.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import pimp.commons.core.Messages;
import pimp.logic.commands.FindRegexCommand;
import pimp.model.person.NameMatchesRegexPredicate;

public class FindRegexCommandParserTest {

    private FindRegexCommandParser parser = new FindRegexCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "(", String.format(Messages.MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindRegexCommand() {
        String[] regexesToTest = new String[] { "abcd", "a   b", "^ab$", "23(x)\\1" };
        for (String regex : regexesToTest) {
            FindRegexCommand expectedCommand = new FindRegexCommand(new NameMatchesRegexPredicate(regex));
            assertParseSuccess(parser, regex, expectedCommand);
        }
    }

}
