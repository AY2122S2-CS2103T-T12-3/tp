package seedu.tracey.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tracey.model.AddressBook;
import seedu.tracey.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Faculty("SOC"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new MatriculationNumber("a1234567m"), new CovidStatus("hrw"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Faculty("FOS"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new MatriculationNumber("a7364736m"), new CovidStatus("negative"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Faculty("FOD"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new MatriculationNumber("a4724567m"), new CovidStatus("hrn"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Faculty("FOL"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new MatriculationNumber("a1234423m"), new CovidStatus("negative"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Faculty("FASS"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new MatriculationNumber("a6374567m"), new CovidStatus("hrw"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Faculty("BIZ"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new MatriculationNumber("a8273645m"), new CovidStatus("positive"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
