package seedu.tracey.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tracey.model.person.CovidStatus;
import seedu.tracey.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private HBox covidStatusPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label faculty;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(String.format("%s %s", person.getName().fullName, person.getMatriculationNumber().value));
        faculty.setText(person.getFaculty().studentFaculty);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().covidStatus);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setStatusPaneColor(person.getStatus().covidStatus);
    }

    /**
     * Sets the background of the covid status pane according to the severity of the given {@code status}.
     * @param status The covid status of the person.
     */
    private void setStatusPaneColor(String status) {
        assert CovidStatus.isValidCovidStatus(status);
        String covidStatus = status.toLowerCase().trim();
        String color;

        switch(covidStatus) {
        case "positive":
            color = "RED";
            break;
        case "negative":
            color = "LIGHTGREEN";
            break;
        case "hrw":
            color = "ORANGE";
            break;
        case "hrn":
            color = "YELLOW";
            break;
        default:
            color = "BLACK";
            assert false : covidStatus;
        }

        String style = String.format("-fx-background-color: %s; "
                + "-fx-background-radius: 15px; "
                + "-fx-border-radius: 15px; "
                + "-fx-border-color: white; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 50px;", color);

        covidStatusPane.setStyle(style);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
