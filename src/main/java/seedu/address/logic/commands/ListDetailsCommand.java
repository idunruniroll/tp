package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DisplayMode;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Lists all assessments and all students for the specified course(s).
 */
public class ListDetailsCommand extends Command {

    public static final String COMMAND_WORD = "listdetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays assessments and students for one or more courses.\n"
            + "Parameters: c/COURSE_CODE[,COURSE_CODE,...]\n"
            + "Example: " + COMMAND_WORD + " c/CS2103T";

    private final List<String> courseCodes;

    /**
     * List details of given course code(s).
     * Shows assessments and students associated.
     * @param courseCodes
     */
    public ListDetailsCommand(List<String> courseCodes) {
        requireNonNull(courseCodes);
        this.courseCodes = courseCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Course> coursesToDisplay = getCoursesToDisplay(model);
        showCourseDetails(model, coursesToDisplay);
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListDetailsCommand
                        && courseCodes.equals(((ListDetailsCommand) other).courseCodes));
    }

    private List<Course> getCoursesToDisplay(Model model) throws CommandException {
        List<Course> coursesToDisplay = new ArrayList<>();

        for (String courseCode : courseCodes) {
            Optional<Course> matchingCourse = model.getCourse(courseCode);
            if (matchingCourse.isEmpty()) {
                throw new CommandException(String.format(Messages.MESSAGE_COURSE_NOT_FOUND, courseCode));
            }

            coursesToDisplay.add(matchingCourse.get());
        }

        return coursesToDisplay;
    }

    private void showCourseDetails(Model model, List<Course> coursesToDisplay) {
        model.setCurrentCourseForDisplay(Optional.empty());
        model.setDetailedCoursesForDisplay(coursesToDisplay);
        model.setDisplayMode(DisplayMode.COURSE_DETAILS);
    }
}
