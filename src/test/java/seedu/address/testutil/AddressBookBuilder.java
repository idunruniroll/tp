package seedu.address.testutil;

import seedu.address.model.AddressBook;

/**
 * A utility class to help with building AddressBook objects.
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    /**
     * Creates an AddressBookBuilder with an empty AddressBook.
     */
    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    /**
     * Creates an AddressBookBuilder with the given AddressBook.
     */
    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Returns the built AddressBook.
     */
    public AddressBook build() {
        return addressBook;
    }
}
