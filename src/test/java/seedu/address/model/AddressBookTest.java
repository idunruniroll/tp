package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + ", assessments=" + addressBook.getAssessmentList()
                + ", grades=" + addressBook.getGradeList()
                + ", courses=" + addressBook.getCourseList()
                + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Assessment> assessments = FXCollections.observableArrayList();
        private final ObservableList<Grade> grades = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Assessment> getAssessmentList() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<Grade> getGradeList() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return FXCollections.emptyObservableList();
        }
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

        AddressBook addressBook = new AddressBook();
        addressBook.addAssessment(cs2103tQuiz);
        addressBook.addAssessment(cs2040Quiz);
        addressBook.addGrade(grade1);
        addressBook.addGrade(grade2);

        addressBook.removeAssessment(cs2103tQuiz);

        assertFalse(addressBook.getGradeList().contains(grade1));
        assertTrue(addressBook.getGradeList().contains(grade2));
    }

    @Test
    public void removeCourse_existingCourse_removesAssessmentsAndUnenrollsStudents() {
        Course course = new Course("CS2103T");
        Student student = new Student("A0123456X", "Alice Tan");
        Assessment assessment = new Assessment("CS2103T", new AssessmentName("Quiz 1"), new MaxScore("10"));
        Grade grade = new Grade("CS2103T", new StudentId(student.getStudentId()),
                new AssessmentName("Quiz 1"), new Score("8"));

        AddressBook addressBook = new AddressBook();
        addressBook.addCourse(course);
        addressBook.addStudentToCourse("CS2103T", student);
        course.addAssessment(assessment);
        addressBook.addAssessment(assessment);
        addressBook.addGrade(grade);

        addressBook.removeCourse(course);

        assertFalse(addressBook.hasCourse(new Course("CS2103T")));
        assertFalse(addressBook.getAssessmentList().contains(assessment));
        assertFalse(addressBook.getGradeList().contains(grade));
        assertTrue(course.getStudents().isEmpty());
    }
}
