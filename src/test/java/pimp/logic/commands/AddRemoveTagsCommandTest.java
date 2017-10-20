package pimp.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pimp.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static pimp.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static pimp.logic.commands.CommandTestUtil.assertCommandFailure;
import static pimp.logic.commands.CommandTestUtil.assertCommandSuccess;
import static pimp.logic.commands.CommandTestUtil.showFirstPersonOnly;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import pimp.commons.core.Messages;
import pimp.commons.core.index.Index;
import pimp.logic.CommandHistory;
import pimp.model.ModelManager;
import pimp.model.person.Person;
import pimp.testutil.PersonBuilder;
import pimp.testutil.TypicalIndexes;
import pimp.testutil.TypicalPersons;
import pimp.logic.UndoRedoStack;
import pimp.logic.parser.ParserUtil;
import pimp.model.AddressBook;
import pimp.model.Model;
import pimp.model.UserPrefs;
import pimp.model.tag.Tag;

public class AddRemoveTagsCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased()))
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, "friends").build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);

        AddRemoveTagsCommand addTagsCommand = prepareCommandAdd(TypicalIndexes.INDEX_FIRST_PERSON, ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased())).withTags().build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add("friends");

        AddRemoveTagsCommand removeTagsCommand = prepareCommandRemove(TypicalIndexes.INDEX_FIRST_PERSON,
                ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_REMOVE_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        final AddRemoveTagsCommand standardCommand = new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON, tags);

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        AddRemoveTagsCommand commandWithSameValues = new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON, tags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Return false with different type
        AddRemoveTagsCommand commandWithDifferentType = new AddRemoveTagsCommand(false, TypicalIndexes.INDEX_FIRST_PERSON, tags);
        assertFalse(standardCommand.equals(commandWithDifferentType));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        AddRemoveTagsCommand commandWithDifferentIndex = new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_SECOND_PERSON,
                ParserUtil.parseTags(tagsList));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different tags
        ArrayList<String> differentTagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        AddRemoveTagsCommand commandWithDifferentTags = new AddRemoveTagsCommand(true, TypicalIndexes.INDEX_FIRST_PERSON,
                ParserUtil.parseTags(differentTagsList));
        assertFalse(standardCommand.equals(commandWithDifferentTags));
    }

    /**
     * Returns an add {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandAdd(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(true, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }

    /**
     * Returns a remove {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandRemove(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(false, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }
}
