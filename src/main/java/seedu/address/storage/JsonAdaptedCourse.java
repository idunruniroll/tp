package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Course Code is missing!";
    private final String courseCode;
    private final List<JsonAdaptedStudent> students;

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given course code and students.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("courseCode") String courseCode,
            @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.courseCode = courseCode;
        this.students = students != null ? students : new ArrayList<>();
    }

    /**
     * Constructs a {@code JsonAdaptedCourse} using the given {@link Course}.
     */
    public JsonAdaptedCourse(Course source) {
        courseCode = source.getCourseCode().toString();
        students = source.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted course object into a {@link Course} object.
     *
     * @return a new Course object
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Course toModelType() throws IllegalValueException {
        if (courseCode == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!Course.isValidCourseCode(courseCode)) {
            throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
        }
        final Course course = new Course(courseCode);

        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            course.addStudent(student);
        }

        return course;
    }
}
