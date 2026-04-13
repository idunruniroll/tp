package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

/**
 * Shows an overview summary of the current data (assessments and grades).
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "viewall";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows an overview summary of all assessments and grades.\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ViewAllCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing viewall command");

        model.setDisplayMode(DisplayMode.OVERVIEW);

        int assessmentCount = model.getAddressBook().getAssessmentList().size();
        int gradeCount = model.getAddressBook().getGradeList().size();

        Map<String, Integer> gradesPerAssessment = new LinkedHashMap<>();

        for (Assessment assessment : model.getAddressBook().getAssessmentList()) {
            String key = formatAssessmentKey(
                    assessment.getCourseCode().toString(),
                    assessment.getAssessmentName().toString());
            gradesPerAssessment.put(key, 0);
        }

        for (Grade grade : model.getAddressBook().getGradeList()) {
            String key = formatAssessmentKey(
                    grade.getCourseCode().toString(),
                    grade.getAssessmentName().toString());
            gradesPerAssessment.put(key, gradesPerAssessment.getOrDefault(key, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Overview\n");
        sb.append("Assessments: ").append(assessmentCount).append("\n");
        sb.append("Grades: ").append(gradeCount).append("\n");
        sb.append("Grades per assessment:\n");

        if (gradesPerAssessment.isEmpty()) {
            sb.append("- None\n");
        } else {
            for (Map.Entry<String, Integer> entry : gradesPerAssessment.entrySet()) {
                sb.append("- ")
                        .append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append("\n");
            }
        }

        return new CommandResult(sb.toString().trim());
    }

    private String formatAssessmentKey(String courseCode, String assessmentName) {
        return courseCode + " / " + assessmentName;
    }
}
