package pimp.logic.parser;

import static pimp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pimp.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pimp.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import pimp.logic.commands.RedoCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class RedoCommandParserTest {

    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() {
        assertParseSuccess(parser, "12", new RedoCommand(12));
    }

    @Test
    public void parse_emptyArgs_returnsRedoCommand() {
        assertParseSuccess(parser, "", new RedoCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}
