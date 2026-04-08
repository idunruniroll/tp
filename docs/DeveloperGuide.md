---
layout: page
title: Developer Guide
---

- Table of Contents
  {:toc}

---

## **Acknowledgements**

- {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the main components interact when the user executes the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

This diagram stays at the component level. The parser and command objects involved in the deletion flow are intentionally abstracted away here and are described in the `Logic` section below.

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The diagram focuses on the core collaborators in `Logic` together with a representative subset of concrete commands. Additional command classes follow the same `Command` inheritance structure and are omitted to keep the diagram readable.

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` as an example.

![Interactions Inside the Logic Component for the `delete` Command](images/DeleteSequenceDiagram.png)

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, `LogicManager` delegates parsing to `AddressBookParser`, which selects the parser matching the command word (for example, `DeleteCommandParser`).
1. The parser returns a `Command` object. For `delete 1`, this is a `DeleteCommand`.
1. The command is then executed with the `Model`. In the `delete` flow, the command retrieves the currently displayed person list, validates the requested index, and deletes the selected `Person` when the index is valid.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- `AddressBookParser` acts as the entry point for parsing. It inspects the command word and delegates to a concrete parser such as `DeleteCommandParser`, `AddCourseCommandParser`, `AddAssessmentCommandParser`, or `ExportCourseCommandParser`.
- Concrete parser classes implement the common `Parser<T>` interface and each creates exactly one matching `Command` subtype.
- Many parsers reuse shared helpers such as `ArgumentTokenizer`, `ArgumentMultimap`, `ParserUtil`, `CliSyntax`, and `Prefix` to tokenize prefixed arguments, validate them, and construct the target command object.
- Some simple commands, such as `clear`, `list`, `help`, `exit`, and `viewall`, are instantiated directly by `AddressBookParser` and therefore do not appear in the parser class diagram.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.
  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- Role: University-level academic educator teaching one or more undergraduate courses each semester

- Class size: Manages assessment records for classes ranging from dozens to a few hundred students

- Core tasks: Regularly updates grades after assignments, tests, and other assessments

- Work setup: Works primarily alone on a personal computer

- Responsibility: Maintains accurate, up-to-date grade records throughout the semester

- Pain points: Manual bookkeeping and repeated calculations are time-consuming and reduce time for teaching/student engagement

- Needs/values: Efficiency, clarity, and reduced administrative overhead

- Tech comfort: Comfortable using simple command-based tools if they speed up work and improve reliability

**Value proposition**: GradeBookPlus helps educators manage and interpret student assessment results by consolidating grades across assignments and tests into a single system, reducing manual record-keeping and enabling clearer insight into overall class performance and academic trends.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                            | I want to …                                               | So that I can…                                            |
| -------- | --------------------------------- | --------------------------------------------------------- | --------------------------------------------------------- |
| `* * *`  | potential user exploring the app  | see the app populated with sample data                    | easily see how the app will look like when it is in use.  |
| `* * *`  | first time user                   | see the available features                                | understand how the application works.                     |
| `* *`    | new user                          | to test the available features                            | see an example of how the application works.              |
| `* * *`  | new user                          | start with a clean table                                  | not have extra unnecessary data                           |
| `* * *`  | new user                          | create a new course in the system                         | manage assessment records for each course separately      |
| `* * *`  | new user                          | add a list of students to a course using a single command | quickly initialize the class roster                       |
| `* * *`  | new user                          | edit student records                                      | keep my class list accurate throughout the semester       |
| `* * *`  | beginner user                     | remove student records                                    | keep my class list accurate when students drop the course |
| `* * *`  | user who teaches multiple courses | switch between courses                                    | view and update the correct class records quickly         |
| `* * *`  | user                              | add an assessment component                               | organize grades by assignments/tests/exams                |
| `* * *`  | user                              | edit an assessment component                              | reflect changes in assessment structure                   |
| `* * *`  | user                              | delete an assessment component                            | remove assessments that are no longer relevant            |
| `* * *`  | user                              | record a student’s score for an assessment                | keep track of student performance                         |
| `* * *`  | user                              | update a student’s score                                  | correct mistakes or reflect regrading                     |
| `* * *`  | user                              | view a student’s scores                                   | understand how they performed across assessments          |
| `* * *`  | user                              | view the overall grade for a student                      | quickly see their standing                                |
| `* * *`  | user                              | list all students and their overall grades                | review the class performance at a glance                  |
| `* *`    | user                              | search for a student by name or ID                        | locate records quickly in a large cohort                  |
| `* *`    | user                              | filter students by performance band                       | identify students who need attention or who excel         |
| `* *`    | user                              | sort students by name or overall grade                    | navigate the records more efficiently                     |
| `* *`    | user                              | compute weighted totals automatically                     | save time and reduce calculation errors                   |
| `* *`    | user                              | set weightages for assessment components                  | ensure overall grades are computed correctly              |
| `* *`    | user                              | see grade breakdown for a student                         | explain how an overall grade was derived                  |
| `* *`    | user                              | export grade data to CSV                                  | submit results or back up data                            |
| `* *`    | user                              | import data from CSV                                      | reduce manual entry when setting up or migrating records  |
| `* *`    | user                              | undo the last action                                      | recover from accidental edits                             |
| `* *`    | user                              | view usage instructions                                   | refer to instructions when I forget how to use the app    |
| `* *`    | user                              | get clear error messages for invalid commands             | fix mistakes quickly without guessing                     |
| `* *`    | user                              | see confirmation before deleting important data           | avoid accidental loss of records                          |
| `* *`    | user                              | save data automatically                                   | not lose progress if the app closes unexpectedly          |

### Use cases

(For all use cases below, the **System** is `GradeBookPlus` and the **Actor** is the `user`, unless specified otherwise)

**Use Case: Add a student**

The following activity diagram summarizes what happens when a user executes the `addstudent` command:

<img src="images/AddStudentActivityDiagram.png" width="400" />

**MSS**

1. User requests to add a student to the list, including the course code (required), student ID (required), name
(required) and email (optional) in the command.
2. GradeBookPlus adds the student.

    Use case ends.

**Extensions**

* 2a. User inputs invalid course code.
  * 2a1. GradeBookPlus shows an error message.

    Use case ends.

* 3a. User inputs invalid student ID.
    * 3a1. GradeBookPlus shows an error message.

      Use case ends.

**Use case: Delete a student**

**MSS**

1. User requests to delete a specific student in the list, including the student's name and course code in the command.
2. GradeBookPlus deletes the student

    Use case ends.

**Extensions**

* 2a. The specified student cannot be found.
  * 2a1. GradeBookPlus shows an error message

    Use case ends.

* 3a. The given course code is invalid.

    * 3a1. GradeBookPlus shows an error message.

      Use case ends.

**Use case: Add a course**

**MSS**

1. User requests to add a course, including its course code
2. GradeBookPlus adds the new course

**Use case: Add a course assessment**

**MSS**

1. User requests to add an assessment to an existing course, including the course code and the assessment name in the
command command.
2. GradeBookPlus adds an assessment to the selected course.

**Extensions**

* 2a. Specified course cannot be found.
  * 2a1. GradeBookPlus shows an error message.

    Use case ends.

**Use case: Add a student's grade**

**MSS**

1. User requests to add score to a student in a course, including the student ID, course code, assessment name, and
score in the command.
2. GradeBookPlus adds the score to the specified student.

**Extensions**

* 2a. Student not found.
  * 2a1. GradeBookPlus shows an error message.

    Use case ends.

* 3a. Course not found.
  * 3a1. GradeBookPlus shows an error message.

    Use case ends.

* 4a. Assessment not found.
    * 4a1. GradeBookPlus shows an error message.

      Use case ends.

**Use case: Remove a student's grade**

**MSS**

1. User requests to remove score from an assessment in a course for a student, including the student ID, course code and
assessment name in the command.
2. GradeBookPlus removes the score in the assessment tied to the student in the course

**Extensions**

* 2a. Student not found.
    * 2a1. GradeBookPlus shows an error message.

      Use case ends.

* 3a. Course not found.
    * 3a1. GradeBookPlus shows an error message.

      Use case ends.

* 4a. Assessment not found.
    * 4a1. GradeBookPlus shows an error message.

      Use case ends.

* 5a. Score not found (assessment has no score to begin with).
    * 5a1. GradeBookPlus shows an error message.

      Use case ends.

_{More to be added}_

### Non-Functional Requirements

1. Portability: Should work on any mainstream OS as long as it has Java 17 or higher installed.

2. Performance: Should be able to hold up to 1000 students per course (with associated grades) without a noticeable sluggishness in performance for typical usage (e.g., listing all students/grades, adding/removing entries).

3. Usability: A user with above‑average typing speed for regular English text (i.e., not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than a mouse.

4. Reliability: Data should persist across application restarts without loss, even after crashes or unexpected closures.

5. Usability (CLI): Command responses should appear within 2 seconds for typical operations on 1000‑student datasets.

6. Usability (Error Messages): All error messages should be specific, actionable, and indicate exactly what went wrong and how to fix it (e.g., "Invalid course code. Example: c/CS2103T").

7. Scalability: Should support up to 20 courses simultaneously without performance degradation.

8. Maintainability: Codebase should follow SOLID principles and have test coverage >80% for core logic (student/grade CRUD).

9. Usability (Input Validation): All commands should validate parameters before processing and reject invalid inputs immediately with clear feedback.

10. Accessibility: Command syntax should be intuitive and consistent across features (e.g., all CRUD ops use c/COURSE_CODE id/STUDENT_ID prefix pattern).

### Glossary

- **Assessment component**: A graded item within a course (e.g., Assignment 1, Midterm, Final) that contributes to the overall grade.
- **Weightage**: The percentage contribution of an assessment component to the overall grade (e.g., Midterm = 30%).
- **Course**: A module/class identified by a course code (e.g., CS2103T) that contains students and assessment components.
- **Course code**: A unique identifier for a course (e.g., CS2103T, CS2040S).
- **Student ID**: A unique identifier for a student within the institution (format defined by the app).
- **Roster**: The list of students enrolled in a course.
- **Grade record**: A student’s stored scores across assessment components for a specific course.
- **Overall grade**: The computed weighted total for a student in a course based on scores and weightages.
- **Performance band**: A category/range used to group students by overall grade (e.g., A: ≥85, B: 70–84).
- **Import**: Loading course/student/grade data from an external file (e.g., CSV) into GradeBookPlus.
- **Export**: Saving course/student/grade data from GradeBookPlus into an external file (e.g., CSV).
- **Mainstream OS**: Windows, macOS, Linux.
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch
   1. Download the jar file and copy it into an empty folder.

   1. Open a terminal in that folder and run `java -jar "GradeBookPlus.jar"`.<br>
      Expected: Shows the GradeBookPlus GUI. The app may initialize sample data depending on the current release.

1. Saving window preferences
   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app.<br>
      Expected: The most recent window size and location is retained.

### Adding a course

1. Adding a course
   1. Test case: `addcourse c/CS2103T`<br>
      Expected: The course is added successfully and a success message is shown.

   1. Test case: `addcourse c/CS2103T` again<br>
      Expected: The app rejects the duplicate course and shows an error message.

### Adding a student

1. Adding a student to an existing course
   1. Prerequisite: A course such as `CS2103T` already exists.

   1. Test case: `addstudent c/CS2103T id/A0123456X n/Alex Yeoh`<br>
      Expected: The student is added successfully.

   1. Test case: `addstudent c/FAKE1234 id/A0123456X n/Alex Yeoh`<br>
      Expected: The app rejects the command because the course does not exist.

### Adding an assessment

1. Adding an assessment to an existing course
   1. Prerequisite: A course such as `CS2103T` already exists.

   1. Test case: `addassessment c/CS2103T an/Midterm m/100`<br>
      Expected: The assessment is added successfully.

   1. Test case: `addassessment c/CS2103T an/Midterm m/100` again<br>
      Expected: The app rejects the duplicate assessment and shows an error message.

### Adding a grade

1. Adding a grade for a student
   1. Prerequisites:
      - A course such as `CS2103T` already exists.
      - A student has already been added to the course.
      - At least one assessment exists for the course.

   1. Test case: `addgrade c/CS2103T id/A0123456X as/1 g/85`<br>
      Expected: The grade is added successfully.

   1. Test case: `addgrade c/CS2103T id/A0123456X as/99 g/85`<br>
      Expected: The app rejects the invalid assessment index and shows an error message.

### Viewing the overall summary

1. Viewing summary information
   1. Test case: `viewall`<br>
      Expected: Displays a summary showing the total number of assessments, the total number of grades, and the number of grades recorded for each assessment.

### Saving data

1. Dealing with missing/corrupted data files
   1. Start the app in a fresh folder without existing data files.<br>
      Expected: The app creates the required files automatically.

   1. Modify or remove the data file manually and relaunch the app.<br>
      Expected: The app handles the situation gracefully and informs the user if recovery is not possible.
