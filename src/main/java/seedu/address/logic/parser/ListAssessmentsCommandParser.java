package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListAssessmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAssessmentsCommand object.
 */
public class ListAssessmentsCommandParser implements Parser<ListAssessmentsCommand> {

    @Override
    public ListAssessmentsCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListAssessmentsCommand.MESSAGE_USAGE));
        }

        return new ListAssessmentsCommand();
    }
}
