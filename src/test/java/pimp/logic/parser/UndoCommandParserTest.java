package pimp.logic.parser;

import static pimp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pimp.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pimp.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import pimp.logic.commands.UndoCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        assertParseSuccess(parser, "12", new UndoCommand(12));
    }

    @Test
    public void parse_emptyArgs_returnsUndoCommand() {
        assertParseSuccess(parser, "", new UndoCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
