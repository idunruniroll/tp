package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.ListGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListGradesCommand object.
 */
public class ListGradesCommandParser implements Parser<ListGradesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListGradesCommand and returns a ListGradesCommand object for
     * execution.
     *
     * @param args the user input
     * @return a new ListGradesCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListGradesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_COURSE_CODE,
                PREFIX_ASSESSMENT_NAME,
                PREFIX_STUDENT_ID);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException("Invalid command format");
        }

        if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            String studentId = argMultimap.getValue(PREFIX_STUDENT_ID).get();
            return new ListGradesCommand("student", studentId, "");
        }

        if (argMultimap.getValue(PREFIX_COURSE_CODE).isPresent()) {
            String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get();

            if (argMultimap.getValue(PREFIX_ASSESSMENT_NAME).isPresent()) {
                String assessmentName = argMultimap.getValue(PREFIX_ASSESSMENT_NAME).get();
                return new ListGradesCommand("courseassessment", courseCode, assessmentName);
            }

            return new ListGradesCommand("course", courseCode, "");
        }

        throw new ParseException("Invalid command format");
    }
}
