package seedu.tracey.model;

import java.nio.file.Path;

import seedu.tracey.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
