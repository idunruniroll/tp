package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DisplayMode;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudentListPanel studentListPanel;
    private CourseListPanel courseListPanel;
    private CourseDetailListPanel courseDetailListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private AssessmentListPanel assessmentListPanel;
    private GradeListPanel gradeListPanel;
    private OverviewPanel overviewPanel;

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
        this.primaryStage = primaryStage;
        this.logic = logic;

        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
    }

    /**
     * Returns the primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param menuItem the menu item
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
         * the KeyCombination(s).
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
        studentListPanel = new StudentListPanel(logic.getFilteredStudentList());
        courseListPanel = new CourseListPanel(
                logic.getFilteredCourseList(),
                logic.getAddressBook().getAssessmentList());
        courseDetailListPanel = new CourseDetailListPanel(
                logic.getDetailedCourseList(),
                logic.getAddressBook().getAssessmentList());
        assessmentListPanel = new AssessmentListPanel(logic.getFilteredAssessmentList());
        gradeListPanel = new GradeListPanel(
                logic.getFilteredGradeList(),
                logic.getAddressBook().getAssessmentList(),
                logic.getAddressBook().getCourseList());
        overviewPanel = new OverviewPanel(logic);

        personListPanelPlaceholder.getChildren().setAll(
                studentListPanel.getRoot(),
                courseListPanel.getRoot(),
                courseDetailListPanel.getRoot(),
                assessmentListPanel.getRoot(),
                gradeListPanel.getRoot(),
                overviewPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        updateListVisibility();
    }

    /**
     * Updates which panel is visible based on the current display mode.
     */
    private void updateListVisibility() {
        DisplayMode displayMode = logic.getDisplayMode();

        boolean showStudents = displayMode == DisplayMode.STUDENTS;
        boolean showCourses = displayMode == DisplayMode.COURSES;
        boolean showCourseDetails = displayMode == DisplayMode.COURSE_DETAILS;
        boolean showAssessments = displayMode == DisplayMode.ASSESSMENTS;
        boolean showGrades = displayMode == DisplayMode.GRADES;
        boolean showOverview = displayMode == DisplayMode.OVERVIEW;

        studentListPanel.getRoot().setVisible(showStudents);
        studentListPanel.getRoot().setManaged(showStudents);

        courseListPanel.getRoot().setVisible(showCourses);
        courseListPanel.getRoot().setManaged(showCourses);

        courseDetailListPanel.getRoot().setVisible(showCourseDetails);
        courseDetailListPanel.getRoot().setManaged(showCourseDetails);

        assessmentListPanel.getRoot().setVisible(showAssessments);
        assessmentListPanel.getRoot().setManaged(showAssessments);

        gradeListPanel.getRoot().setVisible(showGrades);
        gradeListPanel.getRoot().setManaged(showGrades);

        overviewPanel.getRoot().setVisible(showOverview);
        overviewPanel.getRoot().setManaged(showOverview);
    }

    /**
     * Rebuilds the overview panel so it always shows the latest data.
     */
    private void refreshOverviewPanel() {
        OverviewPanel newOverviewPanel = new OverviewPanel(logic);
        int overviewIndex = personListPanelPlaceholder.getChildren().indexOf(overviewPanel.getRoot());
        personListPanelPlaceholder.getChildren().set(overviewIndex, newOverviewPanel.getRoot());
        overviewPanel = newOverviewPanel;
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
     * Shows the primary stage.
     */
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
        primaryStage.hide();
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

            refreshOverviewPanel();
            updateListVisibility();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Centers the window on the screen.
     */
    public void centerWindowOnScreen() {
        primaryStage.centerOnScreen();
    }
}
