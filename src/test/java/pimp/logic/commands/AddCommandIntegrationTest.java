package pimp.logic.commands;

import static pimp.logic.commands.CommandTestUtil.assertCommandFailure;
import static pimp.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Test;

import pimp.logic.CommandHistory;
import pimp.model.ModelManager;
import pimp.model.person.Person;
import pimp.testutil.PersonBuilder;
import pimp.testutil.TypicalPersons;
import pimp.logic.UndoRedoStack;
import pimp.model.Model;
import pimp.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = new Person(model.getAddressBook().getPersonList().get(0));
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
