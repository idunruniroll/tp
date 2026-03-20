package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveAssessmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveAssessmentCommand object.
 */
public class RemoveAssessmentCommandParser implements Parser<RemoveAssessmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveAssessmentCommand and returns a RemoveAssessmentCommand object for
     * execution.
     *
     */
    public RemoveAssessmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_COURSE_CODE,
                PREFIX_ASSESSMENT);

        // Ensure course code and assessment index are present
        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_ASSESSMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAssessmentCommand.MESSAGE_USAGE));
        }

        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get();
        Index assessmentIndex;

        try {
            // Parse the assessment index (convert it from string to Index object)
            assessmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSESSMENT).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAssessmentCommand.MESSAGE_USAGE), pe);
        }

        // Ensure course code is treated case-insensitively
        courseCode = courseCode.toLowerCase();

        // Create and return the RemoveAssessmentCommand object
        return new RemoveAssessmentCommand(courseCode, assessmentIndex);
    }

    /**
     * Returns true if the required prefixes are present in the argument multimap
     */
    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
