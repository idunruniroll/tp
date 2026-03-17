package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentId;


public class RemoveCourseCommand extends Command {

    public static final String COMMAND_WORD = "removecourse";

    // Delete by course index on displaying
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing course.\n"
            + "Parameters: COURSE_CODE \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Removed course: ";
    // public static final String MESSAGE_COURSE_NOT_FOUND = "This course does not exist in the system.";
    public static final String MESSAGE_INVALID_COURSE_CODE = "The course index provided is invalid.";

    private final Index courseIndex;

    public RemoveCourseCommand(Index course) {
        requireNonNull(course);

        this.courseIndex = course;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Course> coursesList = model.getCourseList();

        Course course = coursesList.get(courseIndex.getZeroBased());
        String courseCode = course.getCourseCode();


        if (!model.hasCourse(courseCode)) {
            throw new CommandException(MESSAGE_INVALID_COURSE_CODE);
        }

        model.removeCourse(course);
        return new CommandResult(String.format(MESSAGE_SUCCESS, courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemoveCourseCommand
                        && courseIndex.equals(((RemoveCourseCommand) other).courseIndex));
    }
}
