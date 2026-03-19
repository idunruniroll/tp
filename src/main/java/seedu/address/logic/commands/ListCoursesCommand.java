package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.course.Course;

public class ListCoursesCommand extends Command {

    public static final String COMMAND_WORD = "listcourses";

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
