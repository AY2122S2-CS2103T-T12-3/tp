package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Block;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Person;

/**
 * Summarise all the students by faculty and show how many students in that faculty and block are positive,
 * negative or on HRN.
 */
public class SummariseCommand extends Command {

    public static final String COMMAND_WORD = "summarise";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Summarise all data in the address book and presents an overview of the covid situation in school.\n";

    public static final String MESSAGE_SUMMARISE_PERSON_SUCCESS = "Summarising all students in this hall: \n";

    public static final String MESSAGE_SUMMARISE_PERSON_FAILURE = "Unable to summarise \n";

    private static final String FACULTY_SUMMARY_FORM = "\nIn %s with %d student(s),\n"
            + "Covid Positive: %d student(s)\n"
            + "Covid Negative: %d student(s)\n"
            + "Health Risk Notice: %d student(s)\n"
            + "%.2f percent of student(s) here are suffering...\n";
    private static final String BLOCK_SUMMARY_FORM = "\nIn block %s with %d student(s),\n"
            + "Covid Positive: %d student(s)\n"
            + "Covid Negative: %d student(s)\n"
            + "Health Risk Notice: %d student(s)\n"
            + "%.2f percent of student(s) here are suffering...\n";
    private static final String HALL_SUMMARY_FORM = "\nIn this hall, %d of %d student(s) are covid positive.\n"
            + "The breakdowns by Block level and Faculty level are given below:\n";

    private static final List<String> FACULTIES = Faculty.getFacultyEnumAsList();
    private static final List<String> BLOCKS = Block.getBlockEnumAsList();

    private static final Predicate<Person> BY_POSITIVE = person -> person.getStatusAsString().equals("POSITIVE");
    private static final Predicate<Person> BY_NEGATIVE = person -> person.getStatusAsString().equals("NEGATIVE");
    private static final Predicate<Person> BY_HRN = person -> person.getStatusAsString().equals("HRN");


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        String answer = summariseAll(lastShownList) + filterByBlock(lastShownList) + filterByFaculty(lastShownList);

        if (answer.isEmpty()) {
            return new CommandResult(MESSAGE_SUMMARISE_PERSON_FAILURE);
        } else {
            return new CommandResult(MESSAGE_SUMMARISE_PERSON_SUCCESS + answer,
                    false, false, true);
        }
    }

    /**
     * Filter entire list by faculties to provide overview of covid situation in each faculty.
     *
     * @param list the unfiltered entire list in the database
     * @return The summarised overview for all students by faculties
     */
    public String filterByFaculty(List<Person> list) {
        StringBuilder ans = new StringBuilder();

        for (String facultyName : FACULTIES) {
            Predicate<Person> byFaculty = person -> person.getFacultyAsString().equals(facultyName);
            List<Person> students = list.stream().filter(byFaculty).collect(Collectors.toList());
            if (students.size() <= 0) {
                continue;
            }
            ans.append(summariseFaculty(students, facultyName));
        }
        return ans.toString();
    }

    /**
     * Filter entire list by the hall block to provide overview of covid situation in each block.
     *
     * @param list the unfiltered entire list in the database
     * @return The summarised overview for all students by blocks
     */
    public String filterByBlock(List<Person> list) {
        StringBuilder ans = new StringBuilder();

        for (String blockLetter : BLOCKS) {
            Predicate<Person> byBlock = person -> person.getBlockAsString().equals(blockLetter);
            List<Person> students = list.stream().filter(byBlock).collect(Collectors.toList());
            if (students.size() <= 0) {
                continue;
            }
            ans.append(summariseBlock(students, blockLetter));
        }
        return ans.toString();
    }

    /**
     * Returns a standardised form with the breakdown of students by covid status within a specified faculty.
     *
     * @param result a list of students in said faculty
     * @param facultyName the faculty in which students are from
     * @return A string form containing the respective number of students with certain covid status
     */
    private String summariseFaculty(List<Person> result, String facultyName) {
        int totalNumberOfStudents = result.size();
        int numberOfPositive = (int) result.stream().filter(BY_POSITIVE).count();
        int numberOfNegative = (int) result.stream().filter(BY_NEGATIVE).count();
        int numberOfHrn = (int) result.stream().filter(BY_HRN).count();
        double percentagePositive = (double) numberOfPositive / totalNumberOfStudents * 100;

        return String.format(FACULTY_SUMMARY_FORM, facultyName, totalNumberOfStudents, numberOfPositive,
                numberOfNegative, numberOfHrn, percentagePositive);
    }

    /**
     * Returns a standardised form with the breakdown of students by covid status within a specified block.
     *
     * @param result a list of students in said block
     * @param blockLetter the block in which students are from
     * @return A string form containing the respective number of students with certain covid status
     */
    private String summariseBlock(List<Person> result, String blockLetter) {
        int totalNumberOfStudents = result.size();
        int numberOfPositive = (int) result.stream().filter(BY_POSITIVE).count();
        int numberOfNegative = (int) result.stream().filter(BY_NEGATIVE).count();
        int numberOfHrn = (int) result.stream().filter(BY_HRN).count();
        double percentagePositive = (double) numberOfPositive / totalNumberOfStudents * 100;

        return String.format(BLOCK_SUMMARY_FORM, blockLetter, totalNumberOfStudents, numberOfPositive,
                numberOfNegative, numberOfHrn, percentagePositive);
    }

    /**
     * Filter entire list to provide overview of covid situation in the hall.
     *
     * @param list the unfiltered entire list in the database
     * @return The summarised overview for all students
     */
    public String summariseAll(List<Person> list) {
        StringBuilder ans = new StringBuilder();
        int numberOfStudents = list.size();

        List<Person> students = list.stream().filter(BY_POSITIVE).collect(Collectors.toList());
        int numberOfPositive = students.size();

        return String.format(HALL_SUMMARY_FORM, numberOfPositive, numberOfStudents);
    }
}


