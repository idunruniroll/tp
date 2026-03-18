package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import seedu.address.logic.commands.AddCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 */
public class AddCourseCommandParser implements Parser<AddCourseCommand> {

    public static final String MESSAGE_FORMAT_ERROR = "\u274C Format: addcourse"
            + " " + PREFIX_COURSE_CODE + "COURSE_CODE";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCourseCommand
     * and returns an AddCourseCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_FORMAT_ERROR,
                    AddCourseCommand.MESSAGE_USAGE));
        }

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());

        return new AddCourseCommand(courseCode);
    }
}
