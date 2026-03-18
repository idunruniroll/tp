package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays all students enrolled in a specified course.
 */
public class ListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "liststudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all students in a course.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2103T";

    public static final String MESSAGE_SUCCESS = "\u2705 Displaying students in %s.";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";
    public static final String MESSAGE_FORMAT_ERROR = "\u274C Format: " + COMMAND_WORD
            + " " + PREFIX_COURSE_CODE + "COURSE_CODE";

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

        model.setCurrentCourseForDisplay(Optional.of(courseCode));
        return new CommandResult(String.format(MESSAGE_SUCCESS, courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListStudentsCommand
                        && courseCode.equals(((ListStudentsCommand) other).courseCode));
    }
}
