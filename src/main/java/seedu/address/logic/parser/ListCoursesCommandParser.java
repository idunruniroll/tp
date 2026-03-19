package seedu.address.logic.parser;


import seedu.address.logic.commands.ListCoursesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCoursesCommand object.
 */
public class ListCoursesCommandParser implements Parser<ListCoursesCommand> {

    @Override
    public ListCoursesCommand parse(String args) throws ParseException {

        return new ListCoursesCommand();
    }
}
