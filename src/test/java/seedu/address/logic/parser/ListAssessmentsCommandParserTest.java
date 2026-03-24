package seedu.address.logic.parser;

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
    public void parse_anyArgs_success() {
        assertParseSuccess(parser, "some random text", new ListAssessmentsCommand());
    }

    @Test
    public void parse_multipleWords_success() {
        assertParseSuccess(parser, "extra arguments here", new ListAssessmentsCommand());
    }
}
