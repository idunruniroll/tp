package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Course;

public class JsonAdaptedCourseTest {
    private static final String VALID_COURSE_CODE = "CS2103T";
    private static final String INVALID_COURSE_CODE = "CS 2103T";

    @Test
    public void toModelType_validCourseDetails_returnsCourse() throws Exception {
        JsonAdaptedCourse course = new JsonAdaptedCourse(new Course(VALID_COURSE_CODE));
        assertEquals(new Course(VALID_COURSE_CODE), course.toModelType());
    }

    @Test
    public void toModelType_invalidCourseCode_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(INVALID_COURSE_CODE, List.of());
        assertThrows(IllegalValueException.class, Course.MESSAGE_CONSTRAINTS, course::toModelType);
    }

    @Test
    public void toModelType_nullCourseCode_throwsIllegalValueException() {
        JsonAdaptedCourse course = new JsonAdaptedCourse(null, List.of());
        assertThrows(IllegalValueException.class, "Course Code is missing!", course::toModelType);
    }
}
