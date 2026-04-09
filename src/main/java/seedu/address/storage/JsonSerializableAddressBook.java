package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.course.Course;
import seedu.address.model.grade.Grade;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "Assessments list contains duplicate assessment(s).";
    public static final String MESSAGE_DUPLICATE_GRADE = "Grades list contains duplicate grade(s).";
    public static final String MESSAGE_DUPLICATE_COURSE = "Courses list contains duplicate course(s).";

    private final List<JsonAdaptedAssessment> assessments = new ArrayList<>();
    private final List<JsonAdaptedGrade> grades = new ArrayList<>();
    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given data.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("assessments") List<JsonAdaptedAssessment> assessments,
            @JsonProperty("grades") List<JsonAdaptedGrade> grades,
            @JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        if (assessments != null) {
            this.assessments.addAll(assessments);
        }
        if (grades != null) {
            this.grades.addAll(grades);
        }
        if (courses != null) {
            this.courses.addAll(courses);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        assessments.addAll(source.getAssessmentList().stream()
                .map(JsonAdaptedAssessment::new)
                .collect(Collectors.toList()));
        grades.addAll(source.getGradeList().stream()
                .map(JsonAdaptedGrade::new)
                .collect(Collectors.toList()));
        courses.addAll(source.getCourseList().stream()
                .map(JsonAdaptedCourse::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedAssessment jsonAdaptedAssessment : assessments) {
            Assessment assessment = jsonAdaptedAssessment.toModelType();
            if (addressBook.hasAssessment(assessment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSESSMENT);
            }
            addressBook.addAssessment(assessment);
        }

        for (JsonAdaptedGrade jsonAdaptedGrade : grades) {
            Grade grade = jsonAdaptedGrade.toModelType();
            if (addressBook.hasGrade(grade)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GRADE);
            }
            addressBook.addGrade(grade);
        }

        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            Course course = jsonAdaptedCourse.toModelType();
            if (addressBook.hasCourse(course)) {
                continue;
            }
            addressBook.addCourse(course);
        }

        return addressBook;
    }

}
