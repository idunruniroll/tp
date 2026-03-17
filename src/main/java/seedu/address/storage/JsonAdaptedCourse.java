package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;

public class JsonAdaptedCourse {

    private static String MISSING_FIELD_MESSAGE_FORMAT = "Course Code is missing!";
    private final String courseCode;

    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("courseCode") String courseCode) {
        this.courseCode = courseCode;
    }

    public JsonAdaptedCourse(Course source) {
        courseCode = source.getCourseCode().toString();
    }

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
