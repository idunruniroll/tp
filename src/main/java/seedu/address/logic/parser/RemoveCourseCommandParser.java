package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemoveCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCourseCommand object.
 */
public class RemoveCourseCommandParser implements Parser<RemoveCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveCourseCommand
     * and returns a RemoveCourseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCourseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + RemoveCourseCommand.MESSAGE_USAGE));
        }
        try {
            String courseCode = ParserUtil.parseCourseCode(trimmedArgs);
            return new RemoveCourseCommand(courseCode);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + RemoveCourseCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
