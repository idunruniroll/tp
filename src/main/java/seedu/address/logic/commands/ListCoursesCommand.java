package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

/**
 * Lists all courses in the system.
 */
public class ListCoursesCommand extends Command {

    public static final String COMMAND_WORD = "listcourses";

    /**
     * Executes the list courses command.
     *
     * @param model the model
     * @return command result containing the list of courses
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ObservableList<Course> assessments = model.getCourseList();

        if (assessments.isEmpty()) {
            return new CommandResult("No Courses found.");
        }

        StringBuilder sb = new StringBuilder("Courses:\n");
        for (int i = 0; i < assessments.size(); i++) {
            sb.append(i + 1).append(". ").append(assessments.get(i)).append("\n");
        }

        return new CommandResult(sb.toString().trim());
    }
}
