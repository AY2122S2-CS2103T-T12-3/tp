package seedu.tracey.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tracey.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.tracey.testutil.Assert.assertThrows;
import static seedu.tracey.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.tracey.commons.exceptions.IllegalValueException;
import seedu.tracey.model.person.Address;
import seedu.tracey.model.person.CovidStatus;
import seedu.tracey.model.person.Email;
import seedu.tracey.model.person.Faculty;
import seedu.tracey.model.person.MatriculationNumber;
import seedu.tracey.model.person.Name;
import seedu.tracey.model.person.Phone;



public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_FACULTY = "sco";
    private static final String INVALID_MATRICULATION_NUMBER = "b1234567v";
    private static final String INVALID_COVID_STATUS = "shn";


    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_FACULTY = BENSON.getFaculty().toString();
    private static final String VALID_MATRICULATION_NUMBER = BENSON.getMatriculationNumber().toString();
    private static final String VALID_COVID_STATUS = BENSON.getStatus().toString();

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_FACULTY, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidFaculty_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_FACULTY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = Faculty.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFaculty_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Faculty.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, null,
                VALID_ADDRESS, VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, null,
                VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMatriculationNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = MatriculationNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMatriculationNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MatriculationNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCovidStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, INVALID_COVID_STATUS, VALID_TAGS);
        String expectedMessage = CovidStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCovidStatus_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_MATRICULATION_NUMBER, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CovidStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_FACULTY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_MATRICULATION_NUMBER, VALID_COVID_STATUS, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
