package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;

public class ListAssessmentsCommand extends Command {

    public static final String COMMAND_WORD = "listassessments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Assessment> assessments = model.getAssessmentList();

        if (assessments.isEmpty()) {
            return new CommandResult("No assessments found.");
        }

        StringBuilder sb = new StringBuilder("Assessments:\n");
        for (int i = 0; i < assessments.size(); i++) {
            sb.append(i + 1).append(". ").append(assessments.get(i)).append("\n");
        }

        return new CommandResult(sb.toString().trim());
    }
}
