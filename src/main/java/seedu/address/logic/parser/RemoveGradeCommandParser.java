package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import seedu.address.logic.commands.RemoveGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.commons.core.index.Index;

/**
 * Parses input arguments and creates a new RemoveGradeCommand object.
 */
public class RemoveGradeCommandParser implements Parser<RemoveGradeCommand> {

        /**
         * Parses the given {@code String} of arguments in the context of the
         * RemoveGradeCommand
         * and returns a RemoveGradeCommand object for execution.
         *
         * @throws ParseException if the user input does not conform the expected format
         */
        public RemoveGradeCommand parse(String args) throws ParseException {
                requireNonNull(args);

                ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                                args, PREFIX_STUDENT, PREFIX_ASSESSMENT, PREFIX_COURSE_CODE);

                if (!ParserUtil.arePrefixesPresent(argMultimap,
                                PREFIX_STUDENT, PREFIX_ASSESSMENT, PREFIX_COURSE_CODE)
                                || !argMultimap.getPreamble().isEmpty()) {
                        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        RemoveGradeCommand.MESSAGE_USAGE));
                }

                Index studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
                Index assessmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSESSMENT).get());
                String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());

                return new RemoveGradeCommand(courseCode, studentIndex, assessmentIndex);
        }
}
