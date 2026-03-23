package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.commons.core.index.Index;
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
                PREFIX_ASSESSMENT,
                PREFIX_STUDENT_ID);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListGradesCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            String studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
            return new ListGradesCommand("student", studentId, null);
        }

        if (argMultimap.getValue(PREFIX_COURSE_CODE).isPresent()) {
            String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());

            if (argMultimap.getValue(PREFIX_ASSESSMENT).isPresent()) {
                Index assessmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSESSMENT).get());
                return new ListGradesCommand("courseassessment", courseCode, assessmentIndex);
            }

            return new ListGradesCommand("course", courseCode, null);
        }

        throw new ParseException(ListGradesCommand.MESSAGE_USAGE);
    }
}
