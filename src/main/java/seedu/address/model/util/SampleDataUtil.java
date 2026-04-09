package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns a sample address book with no pre-loaded data.
     */
    public static ReadOnlyAddressBook getSampleAddressBook() {
        return new AddressBook();
    }

}
