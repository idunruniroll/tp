package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;

import seedu.address.logic.commands.AddCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 */
public class AddCourseParser implements Parser<AddCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCourseCommand
     * and returns an AddCourseCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCourseCommand.MESSAGE_USAGE));
        }

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE).get());

        return new AddCourseCommand(courseCode);
    }
}
