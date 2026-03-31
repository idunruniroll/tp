package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes one or more existing courses.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE[,COURSE_CODE]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS2102,CS2103T";

    public static final String MESSAGE_SUCCESS = "\u2705 Removed course(s): %s.";
    public static final String MESSAGE_DUPLICATE_COURSE_INPUT =
            "\u274C Duplicate course codes in the same command are not allowed: %s.";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";

    private final List<String> courseCodes;

    /**
     * Constructs a RemoveCourseCommand with the specified course codes.
     *
     * @param courseCodes the course codes
     */
    public RemoveCourseCommand(List<String> courseCodes) {
        requireNonNull(courseCodes);
        this.courseCodes = courseCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<String> seenCourseCodes = new HashSet<>();

        for (String courseCode : courseCodes) {
            String normalizedCourseCode = courseCode.trim().toUpperCase();
            if (!seenCourseCodes.add(normalizedCourseCode)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_COURSE_INPUT, normalizedCourseCode));
            }
            if (!model.hasCourse(courseCode)) {
                throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
            }
        }

        for (String courseCode : courseCodes) {
            model.removeCourse(new Course(courseCode));
        }

        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDetailedCoursesForDisplay(List.of());
        model.setDisplayMode(DisplayMode.COURSES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join(", ", courseCodes)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveCourseCommand
                        && courseCodes.equals(((RemoveCourseCommand) other).courseCodes));
    }
}
