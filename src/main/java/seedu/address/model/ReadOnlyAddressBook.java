package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    ObservableList<Assessment> getAssessmentList();

    ObservableList<Grade> getGradeList();

    ObservableList<Course> getCourseList();

}
