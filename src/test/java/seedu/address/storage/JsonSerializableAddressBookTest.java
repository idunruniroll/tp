package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path DUPLICATE_COURSE_FILE = TEST_DATA_FOLDER.resolve("duplicateCourseAddressBook.json");

    @Test
    public void toModelType_duplicateCourses_keepsFirstOccurrence() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_COURSE_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertEquals(2, addressBookFromFile.getCourseList().size());
        assertEquals("CS2103T", addressBookFromFile.getCourseList().get(0).getCourseCode());
        assertEquals(1, addressBookFromFile.getCourseList().get(0).getStudents().size());
        assertEquals("A1234567A", addressBookFromFile.getCourseList().get(0).getStudents().get(0).getStudentId());
        assertEquals("CS2101", addressBookFromFile.getCourseList().get(1).getCourseCode());
    }
}
