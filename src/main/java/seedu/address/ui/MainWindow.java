package seedu.address.ui;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.io.Files;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.commands.SummariseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.excel.ImportFileParser;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_SUCCESS = "wrong";
    private static HelpWindow helpWindow;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Double resultDisplaySizeOne = 100.0;
    private final Double resultDisplaySizeTwo = 200.0;
    private final Double resultDisplaySizeThree = 300.0;

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private EmailWindow emailWindow;
    private ImportWindow importWindow;


    @FXML
    private PieChartWindow pieChartWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        emailWindow = new EmailWindow(logic.getFilteredPersonList());
        pieChartWindow = new PieChartWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */

    void fillInnerParts() {

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens file manager for import window
     */
    @FXML
    public void handleImport () throws CommandException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selectedFile = fileChooser.showOpenDialog(stage);
        String fileType = selectedFile.toString();
        if (!Files.getFileExtension(fileType).equals("xlsx") && !Files.getFileExtension(fileType).equals("xls")) {
            resultDisplay.setFeedbackToUser("Imported file is not in excel format");
        }
        ImportFileParser converter = new ImportFileParser();
        List<String> res;
        res = converter.jsonToPerson(selectedFile);
        for (int i = 0; i < res.size(); i++) {
            try {
                executeCommand(res.get(i));
            } catch (ParseException e) {
                resultDisplay.setFeedbackToUser("Some of the entry are not in correct format");
            }
        }
        resultDisplay.setFeedbackToUser("File successfully imported");
    }

    /**
     * Toggles display text place holder height to grow and shrink.
     */
    @FXML
    public void handleResize() {
        double height = resultDisplayPlaceholder.getHeight();
        if (height == resultDisplaySizeOne) {
            resultDisplayPlaceholder.setMinHeight(resultDisplaySizeTwo);
        } else if (height == resultDisplaySizeTwo) {
            resultDisplayPlaceholder.setMinHeight(resultDisplaySizeThree);
        } else {
            resultDisplayPlaceholder.setMinHeight(resultDisplaySizeOne);
        }
    }

    /**
     * Instantiates a new EmailWindow and shows it.
     */
    public void createImportWindow() {
        importWindow = new ImportWindow();
        importWindow.show();
    }

    /**
     * Opens the email window or opens an updated window it if it's already opened.
     */
    @FXML
    public void handleEmailWindow() {
        if (!emailWindow.isShowing()) {
            createEmailWindow();
        } else {
            emailWindow.hide();
            createEmailWindow();
        }
    }

    /**
     * Resizes the result display window according to 3 different sizes.
     */
    @FXML
    public void handleResizeResultDisplayWindow() {
        resultDisplayPlaceholder.setMinHeight(ResizeCommand.getResultWindowDisplaySize()
                * ResizeCommand.getResieWindowMultiplier());
    }

    /**
     * Instantiates a new EmailWindow and shows it.
     */
    public void createEmailWindow() {
        emailWindow = new EmailWindow(logic.getFilteredPersonList());
        emailWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        emailWindow.hide();
        pieChartWindow.hide();
        primaryStage.hide();
    }

    /**
     * Creates and open a pie chart window if it is not yet created or not showing. Or else it will close, create and
     * open a new pie chart window. Focus of window is not used because user might edit the contact in Tracey and
     * use the {@code SummariseCommand} again, so an updated window is needed to be shown.
     *
     * @param message Feedback message from {@code SummariseCommand} to user
     */
    @FXML
    private void handleSummarise(String message) {
        if (SummariseCommand.shouldOpenPieChartWindow()) {
            if (pieChartWindow == null || !pieChartWindow.isShowing()) {
                pieChartWindow = new PieChartWindow();
                pieChartWindow.execute();
                pieChartWindow.show();
                logger.info("Pie chart window is not yet initialised or not showing!");
            } else {
                pieChartWindow.hide();
                pieChartWindow = new PieChartWindow();
                pieChartWindow.show();
                logger.info("Pie chart window already showing, proceeding to reopen it!");
            }
        } else {
            logger.info("Pie chart window not opened because address book is empty!");
        }
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isSummarise()) {
                handleSummarise(commandResult.getFeedbackToUser());
            }

            if (commandResult.isResize()) {
                handleResizeResultDisplayWindow();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowEmail()) {
                handleEmailWindow();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
