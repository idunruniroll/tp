package seedu.address.logic.parser;

import seedu.address.logic.commands.ListAssessmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAssessmentsCommand object.
 */
public class ListAssessmentsCommandParser implements Parser<ListAssessmentsCommand> {

    @Override
    public ListAssessmentsCommand parse(String args) throws ParseException {
        return new ListAssessmentsCommand();
    }
}
