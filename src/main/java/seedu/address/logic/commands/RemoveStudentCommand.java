package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Removes a student from a course roster and deletes all their associated grade records.
 */
public class RemoveStudentCommand extends Command {

    public static final String COMMAND_WORD = "removestudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a student from a course roster.\n"
            + "Parameters: "
            + PREFIX_COURSE + "COURSE_CODE "
            + PREFIX_ID + "STUDENT_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COURSE + "CS2103T "
            + PREFIX_ID + "A0123456X";

    public static final String MESSAGE_SUCCESS = "\u2705 Removed student %s from %s.";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "\u274C Student %s not found in %s.";

    private final String courseCode;
    private final String studentId;

    public RemoveStudentCommand(String courseCode, String studentId) {
        requireNonNull(courseCode);
        requireNonNull(studentId);
        this.courseCode = courseCode;
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        boolean studentExists = model.getCourse(courseCode)
                .map(c -> c.hasStudent(studentId))
                .orElse(false);

        if (!studentExists) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentId, courseCode));
        }

        model.removeStudentFromCourse(courseCode, studentId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentId, courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveStudentCommand
                        && courseCode.equals(((RemoveStudentCommand) other).courseCode)
                        && studentId.equals(((RemoveStudentCommand) other).studentId));
    }
}
