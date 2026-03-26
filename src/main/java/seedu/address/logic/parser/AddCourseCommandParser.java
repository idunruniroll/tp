package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                                        + AddCourseCommand.MESSAGE_USAGE));
        }

        // Split by comma and parse each course code
        List<String> rawCodes = Arrays.stream(trimmedArgs.split(","))
                .map(String::trim)
                .filter(code -> !code.isEmpty())
                .collect(Collectors.toList());

        if (rawCodes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                                        + AddCourseCommand.MESSAGE_USAGE));
        }

        List<String> courseCodes = new ArrayList<>();
        for (String rawCode : rawCodes) {
            courseCodes.add(ParserUtil.parseCourseCode(rawCode));
        }

        return new AddCourseCommand(courseCodes);
    }
}
