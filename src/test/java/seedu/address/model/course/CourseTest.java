package seedu.address.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_nullCourseCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_invalidCourseCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Course.MESSAGE_CONSTRAINTS, () -> new Course("CS 2103T"));
    }

    @Test
    public void constructor_validCourseCode_normalizesToUppercaseTrimmedCode() {
        Course course = new Course("  cs2103t  ");
        assertEquals("CS2103T", course.getCourseCode());
    }

    @Test
    public void isValidCourseCode() {
        assertFalse(Course.isValidCourseCode(null));
        assertFalse(Course.isValidCourseCode(""));
        assertFalse(Course.isValidCourseCode(" "));
        assertFalse(Course.isValidCourseCode("CS 2103T"));
        assertFalse(Course.isValidCourseCode("C"));
        assertFalse(Course.isValidCourseCode("CS2103TEXTRA"));

        assertTrue(Course.isValidCourseCode("CS2103T"));
        assertTrue(Course.isValidCourseCode(" cs2103t "));
        assertTrue(Course.isValidCourseCode("MA1"));
    }
}
