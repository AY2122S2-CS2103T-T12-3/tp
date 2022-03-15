package seedu.tracey.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tracey.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a Person's faculty in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFaculty(String)}
 */
public class Faculty {

    public static final String MESSAGE_CONSTRAINTS =
            "Only the following faculties exists: " + getFacultyEnumAsString();

    public final String studentFaculty;

    public enum Nus {
        FASS,
        BIZ,
        SOC,
        SCALE,
        FOD,
        CDE,
        DUKE,
        FOL,
        YLLSOM,
        YSTCOM,
        SOPP,
        LKYSPP,
        SPH,
        TEST,
        FOS
    }

    /**
     * Constructs a {@code Faculty}.
     *
     * @param faculty A valid faculty.
     */
    public Faculty(String faculty) {
        requireNonNull(faculty);
        checkArgument(isValidFaculty(faculty), MESSAGE_CONSTRAINTS);
        studentFaculty = faculty.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid faculty.
     * @param test
     * @return true if a given string is valid, false otherwise.
     */
    public static boolean isValidFaculty(String test) {
        return Stream.of(Nus.values())
                .anyMatch(faculty -> faculty.name()
                        .equalsIgnoreCase(test));
    }

    /**
     * Returns the list enum values from the Nus enum class.
     *
     * @return String of Nus enum values.
     */
    public static String getFacultyEnumAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.of(Nus.values()).forEach(faculty -> stringBuilder.append(faculty + " "));
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return studentFaculty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Faculty // instanceof handles nulls
                && studentFaculty.equals(((Faculty) other).studentFaculty)); // state check
    }

    @Override
    public int hashCode() {
        return studentFaculty.hashCode();
    }

    /**
     * Returns a list of strings with enum values from the Nus enum class.
     *
     * @return List of Nus enum values.
     */
    public static List<String> getFacultyEnumAsList() {
        return Stream.of(Nus.values()).map(Enum::name).collect(Collectors.toList());
    }
}
