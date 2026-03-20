package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Adds a new course to the address book.
 */
public class AddCourseCommand extends Command {

    public static final String COMMAND_WORD = "addcourse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new course.\n"
            + "Parameters: "
            + PREFIX_COURSE_CODE + "COURSE CODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "CS2103T";

    public static final String MESSAGE_FORMAT = "\u274C Format: " + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "COURSE_CODE ";

    public static final String MESSAGE_SUCCESS = "New Course added: ";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "This Course already exists.";

    private final String toAdd;

    /**
     * Constructs an AddCourseCommand with the specified course code.
     *
     * @param courseCode the course code to add
     */
    public AddCourseCommand(String courseCode) {
        requireNonNull(courseCode);
        toAdd = courseCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCourse(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSESSMENT);
        }

        Course courseToAdd = new Course(toAdd);
        model.addCourse(courseToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddCourseCommand
                        && toAdd.equals(((AddCourseCommand) other).toAdd));
    }
}
