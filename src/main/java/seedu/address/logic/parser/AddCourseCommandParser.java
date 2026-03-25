package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 */
public class AddCourseCommandParser implements Parser<AddCourseCommand> {

    public static final String MESSAGE_FORMAT_ERROR = "\u274C Format: addcourse COURSE_CODE";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCourseCommand
     * and returns an AddCourseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String arg = ParserUtil.parseCourseCode(args.trim());
            return new AddCourseCommand(arg);
        } catch (ParseException pe) {
            throw new ParseException(
                    MESSAGE_INVALID_COMMAND_FORMAT + AddCourseCommand.MESSAGE_USAGE,
                    pe);
        }
    }
}
