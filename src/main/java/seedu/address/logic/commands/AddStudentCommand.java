package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.student.Student;

/**
 * Adds a student to a course roster.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to a course.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE "
            + PREFIX_STUDENT_ID + "STUDENT_ID "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "CS2103T "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_NAME + "Alex Yeoh "
            + PREFIX_EMAIL + "alex@example.com";

    public static final String MESSAGE_FORMAT = "\u274C Format: " + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "COURSE_CODE "
            + PREFIX_STUDENT_ID + "STUDENT_ID "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_EMAIL + "EMAIL]";

    public static final String MESSAGE_SUCCESS = "\u2705 Student added: %s %s (Course: %s).";
    public static final String MESSAGE_COURSE_NOT_FOUND = "\u274C Course %s not found.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "\u274C Student %s already exists in %s.";
    public static final String MESSAGE_ID_NAME_CONFLICT =
            "\u274C Student ID %s is already registered as \"%s\" in another course. "
            + "Use the same name to enrol this student in multiple courses.";

    private final String courseCode;
    private final Student studentToAdd;

    /**
     * Constructs an AddStudentCommand with the specified course code and student.
     *
     * @param courseCode the course code
     * @param studentToAdd the student to add
     */
    public AddStudentCommand(String courseCode, Student studentToAdd) {
        requireNonNull(courseCode);
        requireNonNull(studentToAdd);
        this.courseCode = courseCode;
        this.studentToAdd = studentToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(courseCode)) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, courseCode));
        }

        boolean duplicate = model.getCourse(courseCode)
                .map(c -> c.hasStudent(studentToAdd.getStudentId()))
                .orElse(false);

        if (duplicate) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT,
                    studentToAdd.getStudentId(), courseCode));
        }

        for (Course course : model.getCourseList()) {
            for (Student existing : course.getStudents()) {
                if (existing.getStudentId().equalsIgnoreCase(studentToAdd.getStudentId())
                        && !existing.getStudentName().equalsIgnoreCase(studentToAdd.getStudentName())) {
                    throw new CommandException(String.format(MESSAGE_ID_NAME_CONFLICT,
                            studentToAdd.getStudentId(), existing.getStudentName()));
                }
            }
        }

        model.addStudentToCourse(courseCode, studentToAdd);

        // If user is currently viewing this course's student list, keep the view on it.
        Optional<String> currentCourse = model.getCurrentCourseForDisplay();
        if (currentCourse.isPresent() && currentCourse.get().equalsIgnoreCase(courseCode)) {
            model.setCurrentCourseForDisplay(Optional.of(courseCode));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                studentToAdd.getStudentId(), studentToAdd.getStudentName(), courseCode));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddStudentCommand
                && courseCode.equals(((AddStudentCommand) other).courseCode)
                && studentToAdd.equals(((AddStudentCommand) other).studentToAdd));
    }
}

