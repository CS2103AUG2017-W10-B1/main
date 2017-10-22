package pimp.logic.parser;

import static pimp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pimp.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static pimp.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static pimp.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pimp.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import pimp.testutil.TypicalIndexes;
import pimp.logic.commands.AddRemoveTagsCommand;
import pimp.model.tag.Tag;

public class AddRemoveTagsCommandParserTest {
    private AddRemoveTagsCommandParser parser = new AddRemoveTagsCommandParser();

    @Test
    public void parse_argsSpecified_success() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);

        String userInputAdd = " add " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandAdd = new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputAdd, expectedCommandAdd);

        String userInputRemove = " remove " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + VALID_TAG_HUSBAND
                + " " + VALID_TAG_FRIEND;
        AddRemoveTagsCommand expectedCommandRemove = new AddRemoveTagsCommand(false, TypicalIndexes.INDEX_FIRST_PERSON, tags);

        assertParseSuccess(parser, userInputRemove, expectedCommandRemove);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemoveTagsCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AddRemoveTagsCommand.COMMAND_WORD, expectedMessage);
    }
}
