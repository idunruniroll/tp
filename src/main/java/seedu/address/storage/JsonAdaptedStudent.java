package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String studentId;
    private final String studentName;
    private final String email;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("studentId") String studentId,
            @JsonProperty("studentName") String studentName,
            @JsonProperty("email") String email) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
    }

    /**
     * Constructs a {@code JsonAdaptedStudent} using the given {@link Student}.
     */
    public JsonAdaptedStudent(Student source) {
        studentId = source.getStudentId();
        studentName = source.getStudentName();
        email = source.getEmail().orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted student object into a {@link Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Student toModelType() throws IllegalValueException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "studentId"));
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "studentName"));
        }
        if (email != null) {
            return new Student(studentId, studentName, email);
        } else {
            return new Student(studentId, studentName);
        }
    }
}
