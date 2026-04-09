package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(java.util.Collections.emptyList(), addressBook.getAssessmentList());
        assertEquals(java.util.Collections.emptyList(), addressBook.getGradeList());
        assertEquals(java.util.Collections.emptyList(), addressBook.getCourseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = new AddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{assessments=" + addressBook.getAssessmentList()
                + ", grades=" + addressBook.getGradeList()
                + ", courses=" + addressBook.getCourseList()
                + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void removeAssessment_sameAssessmentNameDifferentCourses_onlyMatchingGradesRemoved() {
        Assessment cs2103tQuiz = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        Assessment cs2040Quiz = new Assessment("CS2040", new AssessmentName("Quiz 1"), new MaxScore("20"));

        Student alice = new Student("A0123456X", "Alice Tan");
        Student bob = new Student("A0654321B", "Bob Lim");

        Grade grade1 = new Grade("CS2103T", new StudentId(alice.getStudentId()),
                new AssessmentName("Quiz 1"), new Score("8"));
        Grade grade2 = new Grade("CS2040", new StudentId(bob.getStudentId()),
                new AssessmentName("Quiz 1"), new Score("15"));

        AddressBook ab = new AddressBook();
        ab.addAssessment(cs2103tQuiz);
        ab.addAssessment(cs2040Quiz);
        ab.addGrade(grade1);
        ab.addGrade(grade2);

        ab.removeAssessment(cs2103tQuiz);

        assertFalse(ab.getGradeList().contains(grade1));
        assertTrue(ab.getGradeList().contains(grade2));
    }

    @Test
    public void removeCourse_existingCourse_removesAssessmentsAndUnenrollsStudents() {
        Course course = new Course("CS2103T");
        Student student = new Student("A0123456X", "Alice Tan");
        Assessment assessment = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        Grade grade = new Grade("CS2103T", new StudentId(student.getStudentId()),
                new AssessmentName("Quiz 1"), new Score("8"));

        AddressBook ab = new AddressBook();
        ab.addCourse(course);
        ab.addStudentToCourse("CS2103T", student);
        ab.addAssessment(assessment);
        ab.addGrade(grade);

        ab.removeCourse(course);

        assertFalse(ab.hasCourse(new Course("CS2103T")));
        assertFalse(ab.getAssessmentList().contains(assessment));
        assertFalse(ab.getGradeList().contains(grade));
        assertTrue(course.getStudents().isEmpty());
    }
}
