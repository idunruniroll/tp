package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

public class RemoveAssessmentCommand extends Command {

    public static final String COMMAND_WORD = "removeassessment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the assessment identified by the "
            + "index number used in the displayed assessment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ASSESSMENT_SUCCESS = "Removed assessment: %1$s";
    public static final String MESSAGE_INVALID_ASSESSMENT_INDEX = "The assessment index provided is invalid.";

    private String courseCode;
    private AssessmentName assessmentName;
    private final Index targetIndex;

    public RemoveAssessmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Assessment> lastShownList = model.getAssessmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ASSESSMENT_INDEX);
        }

        Assessment assessmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.removeAssessment(assessmentToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ASSESSMENT_SUCCESS, assessmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveAssessmentCommand
                        && targetIndex.equals(((RemoveAssessmentCommand) other).targetIndex));
    }
}
