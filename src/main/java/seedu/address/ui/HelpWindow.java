package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2122s2-cs2103t-t12-3.github.io/tp/UserGuide.html";
    public static final String NEWLINE_AND_INDENTATION ="\n     ";
    public static final String HELP_MESSAGE = "Tracey supports the following features: Find, Add, Delete, Edit, Clear, "
        + "Summarise, List, Help and Filter.\n"
        + "This guide aims to showcase the syntax used for the aforementioned features.\n"
        + "For a full comprehensive guide, please refer to " + USERGUIDE_URL
        + "\n"

        + "\n1. FIND a particular contact in Tracey."
        + NEWLINE_AND_INDENTATION
        + "Format: find NAME"
        + NEWLINE_AND_INDENTATION
        + "Example 1: find John\n"

        + "\n2. ADD a student with relevant details into Tracey."
        + NEWLINE_AND_INDENTATION
        + "Format: add n/NAME p/PHONENUMBER e/EMAIL ..."
        + NEWLINE_AND_INDENTATION
        + "Example 1: add n/Melvin f/SOC cs/ Negative"
        + NEWLINE_AND_INDENTATION
        + "Example 2: add e/student69@u.nus.edu n/ Martin\n"

        + "\n3.DELETE a contact at a specific INDEX. The index refers to the index number shown in the " +
            "displayed person list."
        + NEWLINE_AND_INDENTATION
        + "Format: delete INDEX"
        + NEWLINE_AND_INDENTATION
        + "Example 1: Delete 2"
        + NEWLINE_AND_INDENTATION
        + "Example 2: Delete 4\n"

        + "\n4. EDIT the person at the specified INDEX. The index refers to the index number shown in the " +
            "displayed person list."
        + NEWLINE_AND_INDENTATION
        + "Format: edit INDEX n/NAME ..."
        + NEWLINE_AND_INDENTATION
        + "Example 1: edit 1 p/91234567 e/johndoe@example.com"
        + NEWLINE_AND_INDENTATION
        + "Example 2: edit 2 n/David Limpeh t/\n"

        + "\n5. CLEAR all the data inside Tracey"
        + NEWLINE_AND_INDENTATION
        + "Format: clear\n"

        + "\n6. SUMMARISE the record inside Tracey that an overview of the data," +
            " such as number of students who are covid-positive"
        + NEWLINE_AND_INDENTATION
        + "Format:summarise record\n"

        + "\n7. LIST the full record, which displays all the student’s data that are logged into Tracey"
        + NEWLINE_AND_INDENTATION
        + "Format: list\n"

        + "\n8. Display syntax examples through HELP"
        + NEWLINE_AND_INDENTATION
        + "Format: help\n"

        + "\n9. FILTER student based on health statuses"
        + NEWLINE_AND_INDENTATION
        + "Format: filter covid-positive";


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
