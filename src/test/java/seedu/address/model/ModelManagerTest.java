package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Score;
import seedu.address.model.student.StudentId;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void getFilteredAssessmentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredAssessmentList().remove(0));
    }

    @Test
    public void getFilteredGradeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGradeList().remove(0));
    }

    @Test
    public void displayMode_defaultIsCourses() {
        assertEquals(DisplayMode.COURSES, modelManager.getDisplayMode());
    }

    @Test
    public void setDisplayMode_validDisplayMode_setsDisplayMode() {
        modelManager.setDisplayMode(DisplayMode.ASSESSMENTS);
        assertEquals(DisplayMode.ASSESSMENTS, modelManager.getDisplayMode());
    }

    @Test
    public void updateFilteredGradeList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredGradeList(null));
    }

    @Test
    public void updateFilteredGradeList_validPredicate_filtersGradeList() {
        Grade gradeOne = new Grade("CS2103T", new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"), new Score("10"));
        Grade gradeTwo = new Grade("CS2101", new StudentId("A7654321B"),
                new AssessmentName("Presentation"), new Score("18"));

        AddressBook addressBook = new AddressBook();
        addressBook.addGrade(gradeOne);
        addressBook.addGrade(gradeTwo);

        modelManager = new ModelManager(addressBook, new UserPrefs());
        modelManager.updateFilteredGradeList(grade -> grade.getCourseCode().equalsIgnoreCase("CS2103T"));

        assertEquals(1, modelManager.getFilteredGradeList().size());
        assertIterableEquals(Arrays.asList(gradeOne), modelManager.getFilteredGradeList());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBook();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        differentAddressBook.addCourse(new seedu.address.model.course.Course("CS2103T"));
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different display mode -> returns false
        modelManager.setDisplayMode(DisplayMode.ASSESSMENTS);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
        modelManager.setDisplayMode(DisplayMode.COURSES);

        // different grade filter -> returns false
        Grade gradeOne = new Grade("CS2103T", new StudentId("A1234567X"),
                new AssessmentName("Quiz 1"), new Score("10"));
        Grade gradeTwo = new Grade("CS2101", new StudentId("A7654321B"),
                new AssessmentName("Presentation"), new Score("18"));

        addressBook.addGrade(gradeOne);
        addressBook.addGrade(gradeTwo);
        modelManager = new ModelManager(addressBook, userPrefs);

        modelManager.updateFilteredGradeList(grade -> grade.getCourseCode().equalsIgnoreCase("CS2103T"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
        modelManager.updateFilteredGradeList(grade -> true);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
