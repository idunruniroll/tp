package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "GradeBookPlus commands:\n"
            + "\nCourse commands:\n"
            + "  addcourse c/COURSE_CODE[,COURSE_CODE]...\n"
            + "  listcourses\n"
            + "  removecourse c/COURSE_CODE[,COURSE_CODE]...\n"
            + "\nStudent commands:\n"
            + "  addstudent c/COURSE_CODE id/STUDENT_ID n/NAME [e/EMAIL]\n"
            + "  liststudents c/COURSE_CODE\n"
            + "  removestudent c/COURSE_CODE id/STUDENT_ID\n"
            + "\nAssessment commands:\n"
            + "  addassessment c/COURSE_CODE an/ASSESSMENT_NAME m/MAX_SCORE\n"
            + "  listassessments [c/COURSE_CODE]\n"
            + "  removeassessment c/COURSE_CODE as/ASSESSMENT_INDEX\n"
            + "\nGrade commands:\n"
            + "  addgrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX g/SCORE\n"
            + "  listgrades c/COURSE_CODE\n"
            + "  listgrades c/COURSE_CODE as/ASSESSMENT_INDEX\n"
            + "  listgrades id/STUDENT_ID\n"
            + "  removegrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX\n"
            + "\nOther commands:\n"
            + "  listdetails c/COURSE_CODE\n"
            + "  exportcourse c/COURSE_CODE\n"
            + "  viewall\n"
            + "  help\n"
            + "  exit\n"
            + "\nUser Guide:\n"
            + "  See the Help window for the GitHub User Guide link.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
