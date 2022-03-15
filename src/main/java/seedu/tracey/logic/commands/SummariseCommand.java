package seedu.tracey.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tracey.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.tracey.logic.commands.exceptions.CommandException;
import seedu.tracey.model.Model;
import seedu.tracey.model.person.Faculty;
import seedu.tracey.model.person.Person;

/**
 * Summarise all the students by faculty and show how many students in that faculty are positive, negative, HRW, HRN
 */
public class SummariseCommand extends Command {

    public static final String COMMAND_WORD = "summarise";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Summarise all data in the address book and presents an overview of the covid situation in school.\n";

    public static final String MESSAGE_SUMMARISE_PERSON_SUCCESS = "Summarising all students: \n";

    private static final String FACULTY_SUMMARY_FORM = "\nIn %s with %d student(s),\n"
            + "Covid Positive: %d student(s)\n"
            + "Covid Negative: %d student(s)\n"
            + "Health Risk Warning: %d student(s)\n"
            + "Health Risk Notice: %d student(s)\n"
            + "%.2f percent of student(s) here are suffering...\n";

    private static final List<String> FACULTIES = Faculty.getFacultyEnumAsList();

    private static final Predicate<Person> BY_POSITIVE = person -> person.getStatusAsString().equals("POSITIVE");
    private static final Predicate<Person> BY_NEGATIVE = person -> person.getStatusAsString().equals("NEGATIVE");
    private static final Predicate<Person> BY_HRW = person -> person.getStatusAsString().equals("HRW");
    private static final Predicate<Person> BY_HRN = person -> person.getStatusAsString().equals("HRN");


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();
        String answer = MESSAGE_SUMMARISE_PERSON_SUCCESS + filterByFaculty(lastShownList);
        return new CommandResult(answer);
    }

    /**
     * Filter entire list by faculties to provide overview of covid situation in each faculty.
     *
     * @param list the unfiltered entire list in the database
     * @return the summarised overview for all students by faculties
     */
    private String filterByFaculty(List<Person> list) {
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
     * Returns a standardised form with the breakdown of students by covid status within a specified faculty.
     *
     * @param result a list of students in said faculty
     * @param facultyName the faculty in which students are from
     * @return a string form containing the respective number of students with certain covid status
     */
    private String summariseFaculty(List<Person> result, String facultyName) {
        int totalNumberOfStudents = result.size();
        int numberOfPositive = (int) result.stream().filter(BY_POSITIVE).count();
        int numberOfNegative = (int) result.stream().filter(BY_NEGATIVE).count();
        int numberOfHrw = (int) result.stream().filter(BY_HRW).count();
        int numberOfHrn = (int) result.stream().filter(BY_HRN).count();
        double percentagePositive = (double) numberOfPositive / totalNumberOfStudents * 100;

        return String.format(FACULTY_SUMMARY_FORM, facultyName, totalNumberOfStudents, numberOfPositive,
                numberOfNegative, numberOfHrw, numberOfHrn, percentagePositive);
    }

}


