package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.storage.JsonAdaptedAssessment;
import seedu.address.storage.JsonAdaptedGrade;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.assessment.Assessment;
import seedu.address.model.grade.Grade;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ASSESSMENT = "Assessments list contains duplicate assessment(s).";
    public static final String MESSAGE_DUPLICATE_GRADE = "Grades list contains duplicate grade(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedAssessment> assessments = new ArrayList<>();
    private final List<JsonAdaptedGrade> grades = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("assessments") List<JsonAdaptedAssessment> assessments,
            @JsonProperty("grades") List<JsonAdaptedGrade> grades) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (assessments != null) {
            this.assessments.addAll(assessments);
        }
        if (grades != null) {
            this.grades.addAll(grades);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        assessments.addAll(source.getAssessmentList().stream()
                .map(JsonAdaptedAssessment::new)
                .collect(Collectors.toList()));
        grades.addAll(source.getGradeList().stream()
                .map(JsonAdaptedGrade::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            var person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

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

        return addressBook;
    }

}
