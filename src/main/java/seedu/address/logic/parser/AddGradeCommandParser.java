package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

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
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_ASSESSMENT,
                PREFIX_GRADE, PREFIX_COURSE_CODE); // Add PREFIX_COURSE

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_STUDENT, PREFIX_ASSESSMENT, PREFIX_GRADE,
                PREFIX_COURSE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddGradeCommand.MESSAGE_USAGE));
        }

        Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
        Index assessmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSESSMENT).get());
        Score score = ParserUtil.parseScore(argMultimap.getValue(PREFIX_GRADE).get());
        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get(); // Parse the courseCode

        return new AddGradeCommand(courseCode, studentIndex, assessmentIndex, score); // Pass courseCode here
    }
}
