package seedu.tracey.ui;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/**
 * Alerts to notify user.
 */
public class UiAlert {

    /**
     * Returns a confirmation alert pop-up to notify the user.
     * @param headerMessage Message for the header of the alert.
     * @param contentMessage Messge for the content of the alert.
     * @return Confirmation alert.
     */
    public static Alert makeConfirmationAlert(String headerMessage, String contentMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerMessage);
        alert.setContentText(contentMessage);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
        return alert;
    }
}
