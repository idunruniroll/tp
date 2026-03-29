package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import seedu.address.logic.commands.ListAssessmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListAssessmentsCommand object.
 */
public class ListAssessmentsCommandParser implements Parser<ListAssessmentsCommand> {

    @Override
    public ListAssessmentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAssessmentsCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_COURSE_CODE).isEmpty()) {
            return new ListAssessmentsCommand();
        }

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());
        return new ListAssessmentsCommand(courseCode);
    }
}
