package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.grade.Score;

/**
 * Parses input arguments and creates a new AddGradeCommand object.
 */
public class AddGradeCommandParser implements Parser<AddGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddGradeCommand
     * and returns an AddGradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected
     *                        format
     */
    @Override
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_STUDENT_ID, PREFIX_ASSESSMENT, PREFIX_GRADE, PREFIX_COURSE_CODE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_STUDENT_ID, PREFIX_ASSESSMENT,
                PREFIX_GRADE, PREFIX_COURSE_CODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddGradeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_STUDENT_ID, PREFIX_ASSESSMENT, PREFIX_GRADE, PREFIX_COURSE_CODE);

        String studentIdRaw = argMultimap.getValue(PREFIX_STUDENT_ID)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE)));
        String assessmentRaw = argMultimap.getValue(PREFIX_ASSESSMENT)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE)));
        String scoreRaw = argMultimap.getValue(PREFIX_GRADE)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE)));
        String courseCodeRaw = argMultimap.getValue(PREFIX_COURSE_CODE)
                .orElseThrow(() -> new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, AddGradeCommand.MESSAGE_USAGE)));

        String studentId = ParserUtil.parseStudentId(studentIdRaw);
        Index assessmentIndex = ParserUtil.parseIndex(assessmentRaw);
        Score score = ParserUtil.parseScore(scoreRaw);
        String courseCode = ParserUtil.parseCourseCode(courseCodeRaw);

        return new AddGradeCommand(courseCode, studentId, assessmentIndex, score);
    }
}
