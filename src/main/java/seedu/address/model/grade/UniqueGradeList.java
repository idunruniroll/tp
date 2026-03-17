package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.grade.exceptions.DuplicateGradeException;
import seedu.address.model.grade.exceptions.GradeNotFoundException;

public class UniqueGradeList implements Iterable<Grade> {

    private final ObservableList<Grade> internalList = FXCollections.observableArrayList();
    private final ObservableList<Grade> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    public boolean contains(Grade toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGrade);
    }

    public void add(Grade toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGradeException();
        }
        internalList.add(toAdd);
    }

    public void setGrades(List<Grade> grades) {
        requireAllNonNull(grades);
        if (!gradesAreUnique(grades)) {
            throw new DuplicateGradeException();
        }

        internalList.setAll(grades);
    }

    public void remove(Grade toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GradeNotFoundException();
        }
    }

    public void removeIf(Predicate<Grade> predicate) {
        requireNonNull(predicate);
        internalList.removeIf(predicate);
    }

    public ObservableList<Grade> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Grade> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueGradeList
                        && internalList.equals(((UniqueGradeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean gradesAreUnique(List<Grade> grades) {
        for (int i = 0; i < grades.size() - 1; i++) {
            for (int j = i + 1; j < grades.size(); j++) {
                if (grades.get(i).isSameGrade(grades.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
