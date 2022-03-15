package seedu.tracey.model.person;

import static seedu.tracey.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.tracey.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Faculty faculty;
    private final Phone phone;
    private final Email email;
    private final MatriculationNumber number;

    // Data fields
    private final Address address;
    private final CovidStatus status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Faculty faculty, Phone phone, Email email, Address address,
                  MatriculationNumber number, CovidStatus status, Set<Tag> tags) {
        requireAllNonNull(name, faculty, phone, email, address, number, status, tags);
        this.name = name;
        this.faculty = faculty;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.number = number;
        this.status = status;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public MatriculationNumber getMatriculationNumber() {
        return number;
    }

    public CovidStatus getStatus() {
        return status;
    }

    /**
     * Returns the Covid Status as a String instead of type CovidStatus.
     *
     * @return a string of the covidStatus
     */
    public String getStatusAsString() {
        return status.toString();
    }

    /**
     * Returns the Faculty of this person as a String instead of type Faculty.
     *
     * @return a string of the faculty
     */
    public String getFacultyAsString() {
        return faculty.toString();
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getFaculty().equals(getFaculty())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getMatriculationNumber().equals(getMatriculationNumber())
                && otherPerson.getStatus().equals(getStatus())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, faculty, phone, email, address, number, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Faculty: ")
                .append(getFaculty())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Matriculation Number: ")
                .append(getMatriculationNumber())
                .append("; Covid Status: ")
                .append(getStatus());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
