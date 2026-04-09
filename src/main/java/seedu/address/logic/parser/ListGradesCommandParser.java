package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListGradesCommand;
import seedu.address.logic.commands.ListGradesCommand.FilterType;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListGradesCommand object.
 */
public class ListGradesCommandParser implements Parser<ListGradesCommand> {

    @Override
    public ListGradesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_COURSE_CODE, PREFIX_ASSESSMENT, PREFIX_STUDENT_ID);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListGradesCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE, PREFIX_ASSESSMENT, PREFIX_STUDENT_ID);

        boolean hasStudentId = argMultimap.getValue(PREFIX_STUDENT_ID).isPresent();
        boolean hasCourseCode = argMultimap.getValue(PREFIX_COURSE_CODE).isPresent();
        boolean hasAssessment = argMultimap.getValue(PREFIX_ASSESSMENT).isPresent();

        if (hasStudentId) {
            if (hasCourseCode || hasAssessment) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListGradesCommand.MESSAGE_USAGE));
            }

            String studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
            return new ListGradesCommand(FilterType.STUDENT, studentId, null);
        }

        if (hasCourseCode) {
            String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());

            if (hasAssessment) {
                Index assessmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSESSMENT).get());
                return new ListGradesCommand(FilterType.COURSE_ASSESSMENT, courseCode, assessmentIndex);
            }

            return new ListGradesCommand(FilterType.COURSE, courseCode, null);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListGradesCommand.MESSAGE_USAGE));
    }
}
