package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Removes an existing course from the address book.
 */
public class RemoveCourseCommand extends Command {

    public static final String COMMAND_WORD = "removecourse";

    // Delete by course index on displaying
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing course.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2102";

    public static final String MESSAGE_SUCCESS = "\u2705 Removed course %s.";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";

    private final String courseCode;

    /**
     * Constructs a RemoveCourseCommand with the specified course index.
     *
     * @param course the course index
     */
    public RemoveCourseCommand(String course) {
        requireNonNull(course);

        this.courseCode = course;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Course course = new Course(courseCode);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        model.removeCourse(course);
        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDetailedCoursesForDisplay(List.of());
        model.setDisplayMode(DisplayMode.COURSES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveCourseCommand
                        && courseCode.equals(((RemoveCourseCommand) other).courseCode));
    }
}
