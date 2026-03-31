package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {

    // Replace with your real UG URL.
    public static final String USERGUIDE_URL = "https://ay2526-s2-cs2103t-f12-3.github.io/tp/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label titleLabel;

    @FXML
    private TextFlow helpContent;

    @FXML
    private Button userGuideButton;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        titleLabel.setText("GradeBookPlus Command List");
        buildHelpContent();
        userGuideButton.setText("Open User Guide");
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
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
     * Opens the user guide in the default browser.
     */
    @FXML
    private void openUserGuide() {
        try {
            Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
        } catch (Exception e) {
            logger.warning("Failed to open user guide: " + e.getMessage());
        }
    }

    private void buildHelpContent() {
        helpContent.getChildren().clear();

        addSection("Course commands:");
        addCommand("addcourse", " c/COURSE_CODE[,COURSE_CODE]...");
        addCommand("listcourses", "");
        addCommand("removecourse", " c/COURSE_CODE[,COURSE_CODE]...");
        addBlankLine();

        addSection("Student commands:");
        addCommand("addstudent", " c/COURSE_CODE id/STUDENT_ID n/NAME [e/EMAIL]");
        addCommand("liststudents", " c/COURSE_CODE");
        addCommand("removestudent", " c/COURSE_CODE id/STUDENT_ID");
        addBlankLine();

        addSection("Assessment commands:");
        addCommand("addassessment", " c/COURSE_CODE an/ASSESSMENT_NAME m/MAX_SCORE");
        addCommand("listassessments", " [c/COURSE_CODE]");
        addCommand("removeassessment", " c/COURSE_CODE as/ASSESSMENT_INDEX");
        addBlankLine();

        addSection("Grade commands:");
        addCommand("addgrade", " c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX g/SCORE");
        addCommand("listgrades", " c/COURSE_CODE");
        addCommand("listgrades", " c/COURSE_CODE as/ASSESSMENT_INDEX");
        addCommand("listgrades", " id/STUDENT_ID");
        addCommand("removegrade", " c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX");
        addBlankLine();

        addSection("Other commands:");
        addCommand("listdetails", " c/COURSE_CODE");
        addCommand("exportcourse", " c/COURSE_CODE");
        addCommand("viewall", "");
        addCommand("help", "");
        addCommand("exit", "");
    }

    private void addSection(String heading) {
        Text text = new Text(heading + "\n");
        text.getStyleClass().add("help-section");
        helpContent.getChildren().add(text);
    }

    private void addCommand(String commandWord, String suffix) {
        Text bullet = new Text("• ");
        bullet.getStyleClass().add("help-normal");

        Text command = new Text(commandWord);
        command.getStyleClass().add("help-code");

        Text remainder = new Text(suffix + "\n");
        remainder.getStyleClass().add("help-normal");

        helpContent.getChildren().addAll(bullet, command, remainder);
    }

    private void addBlankLine() {
        Text blank = new Text("\n");
        helpContent.getChildren().add(blank);
    }
}
