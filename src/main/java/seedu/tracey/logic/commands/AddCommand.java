package seedu.tracey.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_COVID_STATUS;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tracey.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.tracey.logic.commands.exceptions.CommandException;
import seedu.tracey.model.Model;
import seedu.tracey.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_FACULTY + "FACULTY "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_MATRICULATION_NUMBER + "MATRICULATION NUMBER "
            + PREFIX_COVID_STATUS + "COVID STATUS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_FACULTY + "SoC "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_MATRICULATION_NUMBER + "A0253647C "
            + PREFIX_COVID_STATUS + "NEGATIVE "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
