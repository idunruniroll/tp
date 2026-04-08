package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.List;

import seedu.address.logic.commands.AddCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 * Accepts one or more course codes separated by commas.
 */
public class AddCourseCommandParser implements Parser<AddCourseCommand> {

    /**
     * Parses the given {@code String} of arguments: course_code[,course_code]...
     * and returns an AddCourseCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + args.trim(), PREFIX_COURSE_CODE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCourseCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE);

        String rawCourseCodes = argMultimap.getValue(PREFIX_COURSE_CODE).orElse("").trim();
        if (rawCourseCodes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCourseCommand.MESSAGE_USAGE));
        }
        List<String> courseCodes = ParserUtil.parseCourseCodes(rawCourseCodes);

        return new AddCourseCommand(courseCodes);
    }
}
