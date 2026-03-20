package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;

/**
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Course Code is missing!";
    private final String courseCode;

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given course code.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("courseCode") String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Constructs a {@code JsonAdaptedCourse} using the given {@link Course}.
     */
    public JsonAdaptedCourse(Course source) {
        courseCode = source.getCourseCode().toString();
    }

    /**
     * Converts this Jackson-friendly adapted course object into a {@link Course} object.
     *
     * @return a new Course object
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Course toModelType() throws IllegalValueException {
        if (courseCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "assessmentName"));
        }
        if (courseCode.trim().isEmpty()) {
            throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
        }

        final Course course;

        try {
            course = new Course(courseCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
        }

        return course;
    }
}
