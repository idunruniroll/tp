package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.List;

import seedu.address.logic.commands.RemoveCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCourseCommand object.
 */
public class RemoveCourseCommandParser implements Parser<RemoveCourseCommand> {
    private static final String INVALID_COMMAND_FORMAT_MESSAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCourseCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveCourseCommand
     * and returns a RemoveCourseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = tokenize(args);
        validateCommandFormat(argMultimap);

        String rawCourseCodes = getRawCourseCodes(argMultimap);
        List<String> courseCodes = ParserUtil.parseCourseCodes(rawCourseCodes);
        return new RemoveCourseCommand(courseCodes);
    }

    private ArgumentMultimap tokenize(String args) {
        return ArgumentTokenizer.tokenize(" " + args.trim(), PREFIX_COURSE_CODE);
    }

    private void validateCommandFormat(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasCoursePrefix = ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE);
        boolean hasPreamble = !argMultimap.getPreamble().isEmpty();

        if (!hasCoursePrefix || hasPreamble) {
            throw new ParseException(INVALID_COMMAND_FORMAT_MESSAGE);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE);
    }

    private String getRawCourseCodes(ArgumentMultimap argMultimap) throws ParseException {
        String rawCourseCodes = argMultimap.getValue(PREFIX_COURSE_CODE).orElse("").trim();
        if (rawCourseCodes.isEmpty()) {
            throw new ParseException(INVALID_COMMAND_FORMAT_MESSAGE);
        }
        return rawCourseCodes;
    }
}
