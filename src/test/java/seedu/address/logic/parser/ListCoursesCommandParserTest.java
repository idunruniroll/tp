package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCoursesCommand;

public class ListCoursesCommandParserTest {
    private ListCoursesCommandParser parser = new ListCoursesCommandParser();

    @Test
    public void parse_noArgs_success() {
        assertParseSuccess(parser, "", new ListCoursesCommand());
    }

    @Test
    public void parse_whitespaceOnlyArgs_success() {
        // Parser ignores arguments, so whitespace should be accepted
        assertParseSuccess(parser, "   ", new ListCoursesCommand());
    }

    @Test
    public void parse_anyArgs_success() {
        // Parser ignores arguments, so any input should be accepted
        assertParseSuccess(parser, "some random text", new ListCoursesCommand());
    }

    @Test
    public void parse_multipleWords_success() {
        // Parser ignores arguments, so multiple words should be accepted
        assertParseSuccess(parser, "extra arguments here", new ListCoursesCommand());
    }
}
