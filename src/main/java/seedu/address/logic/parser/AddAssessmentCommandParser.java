package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_SCORE;

import seedu.address.logic.commands.AddAssessmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assessment.AssessmentName;
import seedu.address.model.assessment.MaxScore;

/**
 * Parses input arguments and creates a new AddAssessmentCommand object.
 */
public class AddAssessmentCommandParser implements Parser<AddAssessmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddAssessmentCommand and returns an AddAssessmentCommand object for
     * execution.
     * @param args the user input
     * @return a new AddAssessmentCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAssessmentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the arguments
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE,
                PREFIX_ASSESSMENT_NAME,
                PREFIX_MAX_SCORE);

        // Check if all necessary prefixes are present
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_ASSESSMENT_NAME,
                PREFIX_MAX_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAssessmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE, PREFIX_ASSESSMENT_NAME, PREFIX_MAX_SCORE);

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());
        AssessmentName assessmentName = ParserUtil.parseAssessmentName(
                argMultimap.getValue(PREFIX_ASSESSMENT_NAME).get());
        MaxScore maxScore = ParserUtil.parseMaxScore(argMultimap.getValue(PREFIX_MAX_SCORE).get());

        // Return a new AddAssessmentCommand with the parsed parameters
        return new AddAssessmentCommand(courseCode, assessmentName, maxScore);
    }
}
