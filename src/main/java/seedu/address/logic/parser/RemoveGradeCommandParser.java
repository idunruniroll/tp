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
    @Override
    public RemoveGradeCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_STUDENT,
                PREFIX_ASSESSMENT);

        // Ensure the necessary prefixes are present
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_STUDENT, PREFIX_ASSESSMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveGradeCommand.MESSAGE_USAGE));
        }

        // Safely extract values from the ArgumentMultimap
        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE)
                .orElseThrow(() -> new ParseException("Course code is missing!"));
        String studentIndexString = argMultimap.getValue(PREFIX_STUDENT)
                .orElseThrow(() -> new ParseException("Student index is missing!"));
        String assessmentIndexString = argMultimap.getValue(PREFIX_ASSESSMENT)
                .orElseThrow(() -> new ParseException("Assessment index is missing!"));

        // Log the extracted values for debugging purposes
        System.out.println("Parsed values:");
        System.out.println("Course Code: " + courseCode);
        System.out.println("Student Index: " + studentIndexString);
        System.out.println("Assessment Index: " + assessmentIndexString);

        // Convert the indices to Index objects
        Index studentIndex = ParserUtil.parseIndex(studentIndexString);
        Index assessmentIndex = ParserUtil.parseIndex(assessmentIndexString);

        // Return the RemoveGradeCommand
        return new RemoveGradeCommand(courseCode, studentIndex, assessmentIndex);
    }
}
