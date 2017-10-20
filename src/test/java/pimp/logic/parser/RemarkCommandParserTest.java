package pimp.logic.parser;

import static pimp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pimp.logic.parser.CliSyntax.PREFIX_REMARK;
import static pimp.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pimp.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static pimp.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import pimp.commons.core.index.Index;
import pimp.logic.commands.RemarkCommand;
import pimp.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String remark = "Some remark.";

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString()
                + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(remark));

        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
