package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

/**
 * Lists all assessments in the system.
 */
public class ListAssessmentsCommand extends Command {

    public static final String COMMAND_WORD = "listassessments";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all assessments grouped by course.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Assessment> assessments = model.getAssessmentList();

        if (assessments.isEmpty()) {
            return new CommandResult("No assessments found.");
        }

        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDisplayMode(DisplayMode.ASSESSMENTS);

        return new CommandResult("Displayed all assessments.");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof ListAssessmentsCommand;
    }
}
