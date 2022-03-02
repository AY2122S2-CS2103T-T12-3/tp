package seedu.tracey.logic.commands;

import static seedu.tracey.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tracey.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tracey.model.AddressBook;
import seedu.tracey.model.Model;
import seedu.tracey.model.ModelManager;
import seedu.tracey.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
