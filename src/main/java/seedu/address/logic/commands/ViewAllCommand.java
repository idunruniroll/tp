package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

/**
 * Shows an overview summary of the current data (assessments and grades).
 * This is a lightweight command for v1.2 to support viewing/reporting needs.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "viewall";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows an overview summary of all assessments and grades.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int assessmentCount = model.getAddressBook().getAssessmentList().size();
        int gradeCount = model.getAddressBook().getGradeList().size();

        // Count how many grades exist per assessment (basic summary)
        Map<String, Integer> gradesPerAssessment = new LinkedHashMap<>();
        for (Assessment a : model.getAddressBook().getAssessmentList()) {
            gradesPerAssessment.put(a.getAssessmentName().toString(), 0);
        }
        for (Grade g : model.getAddressBook().getGradeList()) {
            String key = g.getAssessmentName().toString();
            gradesPerAssessment.put(key, gradesPerAssessment.getOrDefault(key, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Overview\n");
        sb.append("Assessments: ").append(assessmentCount).append("\n");
        sb.append("Grades: ").append(gradeCount).append("\n");
        sb.append("Grades per assessment:\n");
        for (Map.Entry<String, Integer> e : gradesPerAssessment.entrySet()) {
            sb.append("- ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
        }

        return new CommandResult(sb.toString().trim());
    }
}