package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Displays all students enrolled in a specified course.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "liststudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all students in a course.\n"
            + "Parameters: " + PREFIX_COURSE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE + "CS2103T";

    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";
    public static final String MESSAGE_FORMAT_ERROR = "\u274C Format: " + COMMAND_WORD
            + " " + PREFIX_COURSE + "COURSE_CODE";
    public static final String MESSAGE_EMPTY = "\u2705 No students enrolled in %s.";

    private final String courseCode;

    public ListStudentsCommand(String courseCode) {
        requireNonNull(courseCode);
        this.courseCode = courseCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        ArrayList<Student> students = model.getCourse(courseCode).get().getStudents();

        if (students.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EMPTY, courseCode));
        }

        StringBuilder sb = new StringBuilder("\u2705 Displaying students in " + courseCode + ":\n");
        for (Student s : students) {
            sb.append("  ").append(s.getStudentId())
              .append(" | ").append(s.getStudentName());
            s.getEmail().ifPresent(e -> sb.append(" | ").append(e));
            sb.append("\n");
        }
        return new CommandResult(sb.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListStudentsCommand
                        && courseCode.equals(((ListStudentsCommand) other).courseCode));
    }
}
