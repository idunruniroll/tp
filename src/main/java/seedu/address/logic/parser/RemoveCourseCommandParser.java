package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RemoveCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCourseCommand object.
 */
public class RemoveCourseCommandParser implements Parser<RemoveCourseCommand> {

    public static final String MESSAGE_FORMAT_ERROR = "\u274C Format: removecourse COURSE_CODE";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveCourseCommand
     * and returns a RemoveCourseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCourseCommand parse(String args) throws ParseException {
        try {
            String arg = ParserUtil.parseCourseCode(args.trim());
            return new RemoveCourseCommand(arg);
        } catch (ParseException pe) {
            throw new ParseException(
                    MESSAGE_INVALID_COMMAND_FORMAT + RemoveCourseCommand.MESSAGE_USAGE,
                    pe);
        }
    }
}
