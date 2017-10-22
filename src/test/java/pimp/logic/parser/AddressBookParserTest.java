package pimp.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static pimp.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static pimp.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pimp.commons.core.Messages;
import pimp.logic.commands.AddCommand;
import pimp.logic.commands.FindCommand;
import pimp.logic.commands.FindRegexCommand;
import pimp.logic.commands.HelpCommand;
import pimp.logic.commands.HistoryCommand;
import pimp.logic.commands.RemarkCommand;
import pimp.logic.commands.SelectCommand;
import pimp.logic.commands.UndoCommand;
import pimp.logic.parser.exceptions.ParseException;
import pimp.model.person.NameMatchesRegexPredicate;
import pimp.model.person.Person;
import pimp.testutil.EditPersonDescriptorBuilder;
import pimp.testutil.PersonBuilder;
import pimp.testutil.PersonUtil;
import pimp.testutil.TypicalIndexes;
import pimp.logic.commands.AddRemoveTagsCommand;
import pimp.logic.commands.ClearCommand;
import pimp.logic.commands.DeleteCommand;
import pimp.logic.commands.EditCommand;
import pimp.logic.commands.EditCommand.EditPersonDescriptor;
import pimp.logic.commands.ExitCommand;
import pimp.logic.commands.ListCommand;
import pimp.logic.commands.RedoCommand;

import pimp.model.person.NameContainsKeywordsPredicate;
import pimp.model.person.Remark;
import pimp.model.tag.Tag;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommandAlias(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_edit_alias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addtags() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_WORD + " add " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_addtags_alias() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_ALIAS + " add " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_removetags() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_WORD + " remove " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(false, TypicalIndexes.INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_removetags_alias() throws Exception {
        Person person = new PersonBuilder().build();
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        AddRemoveTagsCommand command = (AddRemoveTagsCommand) parser.parseCommand(
                AddRemoveTagsCommand.COMMAND_ALIAS + " remove " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " "
                        + VALID_TAG_HUSBAND + " " + VALID_TAG_FRIEND);
        assertEquals(new AddRemoveTagsCommand(false, TypicalIndexes.INDEX_FIRST_PERSON, tags), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exit_alias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findregex() throws Exception {
        FindRegexCommand command = (FindRegexCommand) parser.parseCommand(FindRegexCommand.COMMAND_WORD + " asdf");
        assertEquals(new FindRegexCommand(new NameMatchesRegexPredicate("asdf")), command);
    }

    @Test
    public void parseCommand_findregex_alias() throws Exception {
        FindRegexCommand command = (FindRegexCommand) parser.parseCommand(FindRegexCommand.COMMAND_ALIAS + " asdf");
        assertEquals(new FindRegexCommand(new NameMatchesRegexPredicate("asdf")), command);
    }

    @Test
    public void parseCommand_find_alias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_help_alias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_history_alias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(Messages.MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_list_alias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final String remark = "This is a remark";
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD
                + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased() + " " + CliSyntax.PREFIX_REMARK + " "
                + remark);

        assertEquals(new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON, new Remark(remark)), command);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_select_alias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + TypicalIndexes.INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(TypicalIndexes.INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 1") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 1") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
