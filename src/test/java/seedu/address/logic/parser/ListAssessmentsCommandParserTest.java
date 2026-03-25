package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAssessmentsCommand;

public class ListAssessmentsCommandParserTest {

    private final ListAssessmentsCommandParser parser = new ListAssessmentsCommandParser();

    @Test
    public void parse_noArgs_success() {
        assertParseSuccess(parser, "", new ListAssessmentsCommand());
    }

    @Test
    public void parse_whitespaceOnlyArgs_success() {
        assertParseSuccess(parser, "   ", new ListAssessmentsCommand());
    }

    @Test
    public void parse_nonEmptyArgs_failure() {
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAssessmentsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleWords_failure() {
        assertParseFailure(parser, "extra arguments here",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAssessmentsCommand.MESSAGE_USAGE));
    }
}
