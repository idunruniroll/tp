package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
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

        // Group assessments by course code
        Map<String, List<Assessment>> groupedAssessments = assessments.stream()
                .collect(Collectors.groupingBy(assessment -> assessment.getCourseCode()));

        StringBuilder sb = new StringBuilder("Assessments:\n");

        // Iterate over each group (course) and list the assessments under that course
        int courseIndex = 1;
        for (Map.Entry<String, List<Assessment>> entry : groupedAssessments.entrySet()) {
            String courseCode = entry.getKey();
            List<Assessment> courseAssessments = entry.getValue();

            sb.append("\nCourse: ").append(courseCode).append(" (Index: ").append(courseIndex).append(")\n");

            // Display assessments under this course with a specific index
            int assessmentIndex = 1;
            for (Assessment assessment : courseAssessments) {
                sb.append("    ").append(assessmentIndex).append(". ").append(assessment).append("\n");
                assessmentIndex++;
            }

            courseIndex++;
        }

        return new CommandResult(sb.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof ListAssessmentsCommand;
    }
}
