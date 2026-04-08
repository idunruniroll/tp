package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCoursesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCoursesCommand object.
 */
public class ListCoursesCommandParser implements Parser<ListCoursesCommand> {

    @Override
    public ListCoursesCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCoursesCommand.MESSAGE_USAGE));
        }

        return new ListCoursesCommand();
    }
}
