package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.logic.commands.RemoveStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveStudentCommand object.
 */
public class RemoveStudentCommandParser implements Parser<RemoveStudentCommand> {

    @Override
    public RemoveStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE, PREFIX_ID);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveStudentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE, PREFIX_ID);

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE).get());
        String studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_ID).get());

        return new RemoveStudentCommand(courseCode, studentId);
    }
}
