package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveAssessmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveAssessmentCommand object.
 */
public class RemoveAssessmentCommandParser implements Parser<RemoveAssessmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveAssessmentCommand
     * and returns a RemoveAssessmentCommand object for execution.
     * 
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAssessmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new RemoveAssessmentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            RemoveAssessmentCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
