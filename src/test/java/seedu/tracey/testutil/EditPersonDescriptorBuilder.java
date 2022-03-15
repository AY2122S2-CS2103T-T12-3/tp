package seedu.tracey.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tracey.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tracey.model.person.Address;
import seedu.tracey.model.person.CovidStatus;
import seedu.tracey.model.person.Email;
import seedu.tracey.model.person.Faculty;
import seedu.tracey.model.person.MatriculationNumber;
import seedu.tracey.model.person.Name;
import seedu.tracey.model.person.Person;
import seedu.tracey.model.person.Phone;
import seedu.tracey.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setFaculty(person.getFaculty());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setMatriculationNumber(person.getMatriculationNumber());
        descriptor.setCovidStatus(person.getStatus());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Faculty} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFaculty(String faculty) {
        descriptor.setFaculty(new Faculty(faculty));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code MatriculationNumber} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMatriculationNumber(String matriculationNumber) {
        descriptor.setMatriculationNumber(new MatriculationNumber(matriculationNumber));
        return this;
    }

    /**
     * Sets the {@code CovidStatus} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCovidStatus(String covidStatus) {
        descriptor.setCovidStatus(new CovidStatus(covidStatus));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
