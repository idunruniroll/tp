package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Adds one or more new courses to the address book.
 */
public class AddCourseCommand extends Command {

    public static final String COMMAND_WORD = "addcourse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or more new courses.\n"
            + "Parameters: COURSE_CODE [,COURSE_CODE]...\n"
            + "Example: " + COMMAND_WORD + " CS2103T,CS2101";

    public static final String MESSAGE_FORMAT = "\u274C Format: " + COMMAND_WORD + " COURSE_CODE [,COURSE_CODE]...";

    public static final String MESSAGE_SUCCESS = "New Course(s) added: %s";
    public static final String MESSAGE_DUPLICATE_COURSE = "This Course already exists: %s";

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

        // Check for duplicates first
        for (String courseCode : courseCodes) {
            if (model.hasCourse(courseCode)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_COURSE, courseCode));
            }
        }

        // Add all courses
        for (String courseCode : courseCodes) {
            Course courseToAdd = new Course(courseCode);
            model.addCourse(courseToAdd);
        }

        String addedCourses = String.join(", ", courseCodes);
        return new CommandResult(String.format(MESSAGE_SUCCESS, addedCourses));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddCourseCommand
                        && courseCodes.equals(((AddCourseCommand) other).courseCodes));
    }
}
