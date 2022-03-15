package seedu.tracey.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tracey.commons.util.AppUtil.checkArgument;

import java.util.stream.Stream;

/**
 * Represents a Person's covid status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCovidStatus(String)}
 */
public class CovidStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Covid status should be one of the following: " + getCovidStatusEnumAsString();

    public enum CovidStatusTier {
        HRW,
        HRN,
        NEGATIVE,
        POSITIVE
    }

    public final String covidStatus;

    /**
     * Constructs a {@code CovidStatus}.
     *
     * @param status A valid covid status.
     */
    public CovidStatus(String status) {
        requireNonNull(status);
        checkArgument(isValidCovidStatus(status), MESSAGE_CONSTRAINTS);
        covidStatus = status.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid covid status.
     *
     * @param test
     * @return true if given string is valid, false otherwise.
     */
    public static boolean isValidCovidStatus(String test) {
        return Stream.of(CovidStatusTier.values())
                .anyMatch(status -> status.name()
                        .equalsIgnoreCase(test));
    }

    /**
     * Returns the list enum values from the CovidStatusTier enum class.
     *
     * @return String of CovidStatusTier enum values.
     */
    public static String getCovidStatusEnumAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.of(CovidStatusTier.values()).forEach(status -> stringBuilder.append(status + " "));
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return covidStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CovidStatus // instanceof handles nulls
                && covidStatus.equals(((CovidStatus) other).covidStatus)); // state check
    }

    @Override
    public int hashCode() {
        return covidStatus.hashCode();
    }
}
