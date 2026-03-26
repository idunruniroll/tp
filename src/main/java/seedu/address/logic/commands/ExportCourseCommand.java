package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.student.Student;

/**
 * Exports all information about a course (students, assessments, grades) to a CSV file.
 */
public class ExportCourseCommand extends Command {

    public static final String COMMAND_WORD = "exportcourse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all course data to a CSV file.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T";

    public static final String MESSAGE_SUCCESS = "\u2705 Exported course data to %s";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";
    public static final String MESSAGE_IO_ERROR = "\u274C Unable to write CSV file: %s";

    private final String courseCode;

    /**
     * Creates an ExportCourseCommand for the given course code.
     */
    public ExportCourseCommand(String courseCode) {
        requireNonNull(courseCode);
        this.courseCode = courseCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        Course course = model.getCourse(courseCode).get();

        // Collect assessments for this course only
        ObservableList<Assessment> allAssessments = model.getAssessmentList();
        List<Assessment> courseAssessments = new ArrayList<>();
        for (Assessment a : allAssessments) {
            if (a.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseAssessments.add(a);
            }
        }

        // Collect grades for this course, keyed by studentId -> assessmentName -> score
        ObservableList<Grade> allGrades = model.getGradeList();
        Map<String, Map<String, String>> gradeMap = new LinkedHashMap<>();
        for (Grade g : allGrades) {
            if (g.getCourseCode().equalsIgnoreCase(courseCode)) {
                gradeMap
                    .computeIfAbsent(g.getStudentId().value.toUpperCase(), k -> new LinkedHashMap<>())
                    .put(g.getAssessmentName().toString(), g.getScore().toString());
            }
        }

        Path outputPath = Paths.get(courseCode + ".csv");

        try (PrintWriter pw = new PrintWriter(outputPath.toFile(), StandardCharsets.UTF_8)) {
            // Header row: StudentID, Name, Email, then one column per assessment
            StringBuilder header = new StringBuilder("Student ID,Name,Email");
            for (Assessment a : courseAssessments) {
                header.append(",").append(escapeCsv(a.getAssessmentName().toString()));
                header.append(" (max ").append(a.getMaxScore()).append(")");
            }
            pw.println(header);

            // One row per student
            for (Student s : course.getStudents()) {
                StringBuilder row = new StringBuilder();
                row.append(escapeCsv(s.getStudentId())).append(",");
                row.append(escapeCsv(s.getStudentName())).append(",");
                row.append(escapeCsv(s.getEmail().orElse("")));

                Map<String, String> studentGrades = gradeMap.getOrDefault(
                        s.getStudentId().toUpperCase(), Map.of());

                for (Assessment a : courseAssessments) {
                    String score = studentGrades.getOrDefault(
                            a.getAssessmentName().toString(), "");
                    row.append(",").append(escapeCsv(score));
                }
                pw.println(row);
            }
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IO_ERROR, e.getMessage()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, outputPath.toAbsolutePath()));
    }

    /** Wraps a value in quotes if it contains a comma, quote, or newline. */
    private static String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportCourseCommand
                        && courseCode.equals(((ExportCourseCommand) other).courseCode));
    }
}
