package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;

import seedu.address.logic.commands.ListStudentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListStudentsCommand object.
 */
public class ListStudentsCommandParser implements Parser<ListStudentsCommand> {

    @Override
    public ListStudentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ListStudentsCommand.MESSAGE_FORMAT_ERROR);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE);

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE).get());
        return new ListStudentsCommand(courseCode);
    }
}
