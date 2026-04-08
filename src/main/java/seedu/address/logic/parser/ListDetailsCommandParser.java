package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.List;

import seedu.address.logic.commands.ListDetailsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommandDetailsCommand object.
 */
public class ListDetailsCommandParser implements Parser<ListDetailsCommand> {

    @Override
    public ListDetailsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListDetailsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE);

        String rawCourseCodes = argMultimap.getValue(PREFIX_COURSE_CODE).orElse("").trim();
        if (rawCourseCodes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListDetailsCommand.MESSAGE_USAGE));
        }
        List<String> courseCodes = ParserUtil.parseCourseCodes(rawCourseCodes);

        return new ListDetailsCommand(courseCodes);
    }
}
