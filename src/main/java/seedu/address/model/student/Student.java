package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Student {
    private final String studentId;
    private final String studentName;

    public Student(String studentId, String studentName) {
        requireAllNonNull(studentId, studentName);
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }
        return otherStudent != null
                && otherStudent.getStudentId().equals(getStudentId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Student)) {
            return false;
        }
        Student otherStudent = (Student) other;
        return studentId.equals(otherStudent.studentId)
                && studentName.equals(otherStudent.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName);
    }

    @Override
    public String toString() {
        return studentId + " " + studentName;
    }
}
