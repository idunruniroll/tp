package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Student;

/**
 * Parses input arguments and creates a new {@link AddStudentCommand} object.
 */
public class AddStudentCommandParser implements Parser<AddStudentCommand> {

    @Override
    public AddStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_STUDENT_ID, PREFIX_NAME, PREFIX_EMAIL);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_STUDENT_ID, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(AddStudentCommand.MESSAGE_FORMAT);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COURSE_CODE, PREFIX_STUDENT_ID, PREFIX_NAME, PREFIX_EMAIL);

        String courseCode = ParserUtil.parseCourseCode(argMultimap.getValue(PREFIX_COURSE_CODE).get());
        String studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        String name = ParserUtil.parseStudentName(argMultimap.getValue(PREFIX_NAME).get());

        Student student;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            try {
                String email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
                student = new Student(studentId, name, email);
            } catch (ParseException e) {
                throw new ParseException("\u274C Invalid email address. Example: e/john@u.nus.edu");
            }
        } else {
            student = new Student(studentId, name);
        }

        return new AddStudentCommand(courseCode, student);
    }
}

