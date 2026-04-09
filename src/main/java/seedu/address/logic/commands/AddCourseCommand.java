package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Adds one or more new courses to the address book.
 */
public class AddCourseCommand extends Command {
    public static final String COMMAND_WORD = "addcourse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or more new courses.\n"
            + "Parameters: c/COURSE_CODE[,COURSE_CODE]...\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T,CS2101";

    public static final String MESSAGE_FORMAT = "\u274C Format: " + COMMAND_WORD + " c/COURSE_CODE[,COURSE_CODE]...";

    public static final String MESSAGE_SUCCESS = "\u2705 Course added: %s.";
    public static final String MESSAGE_DUPLICATE_COURSE_INPUT =
            "\u274C Duplicate course codes in the same command are not allowed: %s.";
    public static final String MESSAGE_DUPLICATE_COURSE = "\u274C Course %s already exists.";

    private static final String COURSE_DELIMITER = ", ";

    private final List<String> courseCodes;


    /**
     * Constructs an AddCourseCommand with the specified course codes.
     *
     * @param courseCodes list of course codes to add
     */
    public AddCourseCommand(List<String> courseCodes) {
        requireNonNull(courseCodes);
        this.courseCodes = courseCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        validateCourseCodes(model);
        addCourses(model);
        resetCourseDisplay(model);

        String addedCourses = String.join(COURSE_DELIMITER, courseCodes);
        return new CommandResult(String.format(MESSAGE_SUCCESS, addedCourses));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddCourseCommand
                        && courseCodes.equals(((AddCourseCommand) other).courseCodes));
    }

    private void validateCourseCodes(Model model) throws CommandException {
        Set<String> seenCourseCodes = new HashSet<>();

        for (String courseCode : courseCodes) {
            String normalizedCourseCode = courseCode.trim().toUpperCase();

            if (!seenCourseCodes.add(normalizedCourseCode)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_COURSE_INPUT, normalizedCourseCode));
            }

            if (model.hasCourse(courseCode)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_COURSE, courseCode));
            }
        }
    }

    private void addCourses(Model model) {
        for (String courseCode : courseCodes) {
            model.addCourse(new Course(courseCode));
        }
    }

    private void resetCourseDisplay(Model model) {
        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDetailedCoursesForDisplay(List.of());
        model.setDisplayMode(DisplayMode.COURSES);
    }
}
