---
layout: page
title: User Guide
---

# GradeBookPlus User Guide

GradeBookPlus is a **desktop gradebook application** for managing courses, students, assessments, and grades. It is optimized for use via a **Command Line Interface** while still providing a **Graphical User Interface** for viewing data clearly.

If you prefer typing commands quickly, GradeBookPlus helps you manage class records faster than clicking through menus.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have **Java 17 or above** installed on your computer.

2. Download the latest `.jar` file from your team’s release page.

3. Copy the `.jar` file into the folder you want to use as the app’s home folder.

4. Open a terminal in that folder and run:

   `java -jar GradeBookPlus.jar`

5. Wait a few seconds for the application window to appear.

6. Type commands into the command box and press Enter to execute them.

Some example commands you can try:

* `addcourse c/CS2103T`
* `addstudent c/CS2103T id/A0123456X n/Alex Yeoh e/alex@example.com`
* `addassessment c/CS2103T an/Quiz 1 m/10`
* `addgrade c/CS2103T id/A0123456X as/1 g/8`
* `listgrades c/CS2103T`

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are parameters to be supplied by the user.<br>
  e.g. in `addcourse c/COURSE_CODE`, `COURSE_CODE` is a parameter.

* Items in square brackets are optional.<br>
  e.g. `addstudent c/COURSE_CODE id/STUDENT_ID n/NAME [e/EMAIL]`

* Parameters can be in any order unless stated otherwise.

* Course codes are case-insensitive.<br>
  e.g. `c/cs2103t` and `c/CS2103T` are treated as the same course.

* Assessment indexes refer to the indexes shown in the displayed assessment list for that course.

</div>

### Viewing help: `help`

**Purpose:** Use this command to open the Help window for a quick reference to GradeBookPlus commands.

Opens the Help window, which displays a summary of supported commands and their usage.

Format: `help`

Example:
* `help`

Expected outcome:
* The Help window opens.
* You can refer to the listed commands without leaving the app.

![Example of Help](images/Help/helpCommand.png)

---

## Course management

### Adding a course: `addcourse`

**Purpose:** Use this command to create one or more courses before adding students, assessments, or grades to them.

Adds one or more courses to the database.

Format: `addcourse c/COURSE_CODE[,COURSE_CODE,...]`

Examples:
* `addcourse c/CS2103T`
* `addcourse c/CS2103T, CS2101, CS2102`

![Example of AddCourse](images/CourseCommands/AddCourse.png)


### Listing all courses: `listcourses`

**Purpose:** Use this command to view all courses currently stored in GradeBookPlus.

Lists all existing courses.

Format: `listcourses`

Examples:
* `listcourses`

![Example of ListCourses](images/CourseCommands/ListCourses.png)


### Removing a course: `removecourse`

**Purpose:** Use this command to delete one or more courses that are no longer needed, together with their associated student and assessment records.

Removes one or more courses using course code.

Format: `removecourse c/COURSE_CODE[,COURSE_CODE,...]`

Example:
* `removecourse c/CS2103T`
* `removecourse c/CS2103T, cs2102`

Notes:
* Removing a course also removes all students and assessments associated with that course.

![Example of RemoveCourse](images/CourseCommands/RemoveCourse.png)


---

## Student management

### Adding a student to a course: `addstudent`

**Purpose:** Use this command to enroll a student into a specific course so that their grades can be recorded later.

Adds a student to a course roster.

Format: `addstudent c/COURSE_CODE id/STUDENT_ID n/NAME [e/EMAIL]`

Examples:
* `addstudent c/CS2103T id/A0123456X n/Alex Yeoh`
* `addstudent c/CS2103T id/A0123456X n/Alex Yeoh e/alex@example.com`

Notes:
* `STUDENT_ID` must follow the format: one letter `A`, followed by exactly 7 digits, followed by one uppercase letter (e.g. `A0123456X`).
* `NAME` must contain only letters, spaces, and the characters `. , ' / -`. Names with `s/o` or `d/o` are supported.
* `EMAIL`, if provided, must be a valid address containing a domain with at least one dot (e.g. `user@example.com`). The local part (before `@`) may only contain alphanumeric characters and the special characters `+`, `_`, `.`, `-`. Characters such as `!`, `#`, `$`, `%`, `^`, `&` are not accepted.

### Listing students in a course: `liststudents`

**Purpose:** Use this command to see all students currently enrolled in a specific course.

Lists all students enrolled in the specified course.

Format: `liststudents c/COURSE_CODE`

Examples:
* `liststudents c/CS2103T`

### Removing a student from a course: `removestudent`

**Purpose:** Use this command to remove a student from a course roster when they are no longer taking that course.

Removes a student from the specified course.

Format: `removestudent c/COURSE_CODE id/STUDENT_ID`

Examples:
* `removestudent c/CS2103T id/A0123456X`

Notes:
* Removing a student also removes all grades associated with that student in the course.

---

## Assessment management

### Adding an assessment: `addassessment`

**Purpose:** Use this command to create an assessment for a course so that grades can be recorded against it.

Adds an assessment to a course.

Format: `addassessment c/COURSE_CODE an/ASSESSMENT_NAME m/MAX_SCORE`

Examples:
* `addassessment c/CS2103T an/Quiz 1 m/10`
* `addassessment c/CS2103T an/Final Exam m/100`

Notes:
* The course must already exist.
* Assessment names cannot be blank and must be at most 50 characters long.
* Maximum score must be greater than 0 and at most 999, with at most 1 decimal place.
* The same assessment cannot be added twice to the same course. Assessment names are compared after ignoring case and spaces.
  For example, `Quiz 1`, `quiz   1`, and `QUIZ1` are considered the same assessment name in the same course.

![Example of AddAssessment](images/AssessmentCommands/addassessmentCommand.png)

### Listing assessments: `listassessments`

**Purpose:** Use this command to view all assessments, either across all courses or within one specific course.

Lists all assessments, optionally filtered by course.

Format:
* `listassessments`
* `listassessments c/COURSE_CODE`

Examples:
* `listassessments`
* `listassessments c/CS2103T`

Notes:
* If a course code is provided, the course must already exist.
* If no matching assessments are found, the app displays a message instead of an empty result.

![Example of ListAssessments](images/AssessmentCommands/listassessmentsCommand.png)

![Example of ListAssessmentsCourseFilter](images/AssessmentCommands/listassessmentsfiltercourseCommand.png)

### Removing an assessment: `removeassessment`

**Purpose:** Use this command to delete an assessment from a course when it is no longer needed or was added by mistake.

Removes an assessment from a course using its displayed index.

Format: `removeassessment c/COURSE_CODE as/ASSESSMENT_INDEX`

Example:
* `removeassessment c/CS2103T as/1`

Notes:
* The course must already exist.
* The assessment index must be a non-zero unsigned integer shown in the assessment list for that course.
* Removing an assessment also removes all grades associated with that assessment.

![Example of ListAssessment](images/AssessmentCommands/removeassessmentCommand.png)

---

## Grade management

### Adding a grade: `addgrade`

**Purpose:** Use this command to record a student’s score for a specific assessment in a course.

Adds a grade for a student in a course assessment.

Format: `addgrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX g/SCORE`

Examples:
* `addgrade c/CS2103T id/A0123456X as/1 g/8`
* `addgrade c/CS2103T id/A0123456X as/2 g/85`

Notes:
* The course must already exist.
* The student must already be enrolled in the course.
* The assessment index must refer to an assessment in the specified course.
* The score must be 0 or above, at most 999, and have at most 1 decimal place.
* The score cannot exceed the assessment’s max score.
* A student can have only one grade for the same assessment. To change a score, remove the existing grade first and add the new grade.

![Example of AddGrade](images/GradeCommands/addgradeCommand.png)

### Listing grades: `listgrades`

**Purpose:** Use this command to view recorded grades by course, by assessment, or by student.

Lists grades by course, by assessment within a course, or by student ID.

Format:
* `listgrades c/COURSE_CODE`
* `listgrades c/COURSE_CODE as/ASSESSMENT_INDEX`
* `listgrades id/STUDENT_ID`

Examples:
* `listgrades c/CS2103T`
* `listgrades c/CS2103T as/1`
* `listgrades id/A0123456X`

Notes:
* Use either `id/STUDENT_ID` alone, or `c/COURSE_CODE` with an optional `as/ASSESSMENT_INDEX`.
* If a course code is provided, the course must already exist.
* If an assessment index is provided, it must refer to an assessment in the specified course.
* If no matching grades are found, the app displays a message instead of an empty result.
* Only students with grades added will be listed

![Example of ListGrades](images/GradeCommands/listgradesCommand.png)

### Removing a grade: `removegrade`

**Purpose:** Use this command to delete an incorrect or outdated grade entry for a student’s assessment.

Removes a grade for a student from a course assessment.

Format: `removegrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX`

Example:
* `removegrade c/CS2103T id/A0123456X as/1`

Notes:
* The course must already exist.
* The student must already be enrolled in the course.
* The assessment index must refer to an assessment in the specified course.
* The grade must already exist.

![Example of RemoveGrades](images/GradeCommands/removegradeCommand.png)

---

## Other commands

### Viewing detailed course information: `listdetails`

**Purpose:** Use this command to view a course’s detailed information, including its students and assessments, in one place.

Displays assessments and students information for one or more courses.

Format: `listdetails c/COURSE_CODE[,COURSE_CODE,...]`

Example:
* `listdetails c/CS2103T`
* `listdetails c/CS2103T, CS2101`

![Example of ListDetails](images/CourseCommands/ListDetails.png)

### Exporting a course: `exportcourse`

**Purpose:** Use this command to export the records of a course for external viewing, sharing, or backup.

Exports all students, assessments, and grades for a course to a CSV file.

Format: `exportcourse c/COURSE_CODE`

Example:
* `exportcourse c/CS2103T`

Notes:
* The output file is saved as `<COURSE_CODE>.csv` (e.g. `CS2103T.csv`) in the folder from which the app was launched.
* The CSV contains one row per student, with columns: `Student ID`, `Name`, `Email`, followed by one column per assessment (with its max score shown in the header).
* Cells for assessments where a grade has not been recorded are left empty.
* The file is overwritten each time the command is run for the same course.

### Viewing overall summary: `viewall`

Displays an overview summary of the current assessment and grade data.

Format: `viewall`

Example:
* `viewall`

Expected outcome:
* Displays the total number of assessments currently stored in the app.
* Displays the total number of grades currently stored in the app.
* Displays the number of grades recorded for each assessment.

Typical usage:
* Use `viewall` after adding or removing assessments to confirm that the assessment count has updated as expected.
* Use `viewall` after adding or removing grades to quickly verify that the overall grade count has changed.
* Use `viewall` when you want a quick summary without manually switching through multiple list commands.

Notes:
* `viewall` is intended as a lightweight overview command rather than a full detailed report.
* The command is useful for quickly checking whether recent updates to assessment and grade records have been reflected in the system.
* In an empty state, `viewall` still provides a quick way to confirm that there are currently no assessments or grades recorded.

### Exiting the program: `exit`

**Purpose**: Use this command to close GradeBookPlus safely.

Exits the application.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q:** How do I move my data to another computer?<br>
**A:** Copy the data file from the old computer into the data folder used by GradeBookPlus on the new computer.

**Q:** Where is my data stored?<br>
**A:** Data is stored automatically in the app’s data folder as a JSON file.

**Q:** Where is the CSV file saved when I use `exportcourse`?<br>
**A:** The file is saved as `COURSE_CODE.csv` (e.g. `CS2103T.csv`) in the folder where you launched the app (i.e. the working directory).

**Q:** Why does `removeassessment` or `removegrade` say the assessment index is invalid?<br>
**A:** Assessment indexes are based on the currently displayed assessment list for the specified course. Run `listassessments c/COURSE_CODE` first, then use the index shown there.

**Q:** Why can’t I add a grade for a student?<br>
**A:** The student must already be enrolled in that course, the assessment index must exist for that course, and the score must not exceed the assessment max score.

**Q:** Can the same student be in multiple courses?<br>
**A:** Yes. Students are enrolled per course, so the same student ID can appear in different course rosters.

**Q:** What happens if I remove a course?<br>
**A:** Removing a course also removes all assessments and grades associated with that course.

**Q:** What happens if I remove an assessment?<br>
**A:** Removing an assessment also removes all grades tied to that assessment in the same course.

**Q:** Why do I see "Course ... not found" even though my command format is correct?<br>
**A:** Format checks and data checks are different. A command can be syntactically valid but still fail if the referenced course does not exist in your current data.

**Q:** Are command keywords and course codes case-sensitive?<br>
**A:** Command keywords should be typed as documented. Course codes are case-insensitive (for example, `cs2103t` and `CS2103T` refer to the same course).

**Q:** What does "No grades found" or "No assessments found" mean?<br>
**A:** The command ran successfully, but there are no matching records for the filter you requested.

**Q:** Why does adding an assessment fail with "This assessment already exists"?<br>
**A:** GradeBookPlus rejects duplicate assessment names in the same course after ignoring case and spaces. For example, if `Quiz 1` already exists in `CS2103T`, adding `quiz   1` or `QUIZ1` to `CS2103T` will fail.

**Q:** When should I use `viewall` instead of the other list commands?<br>
**A:** Use `viewall` when you want a quick summary of overall assessment and grade data. Use commands such as `liststudents`, `listassessments`, and `listgrades` when you need more detailed records.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. If you move the application between multiple monitors, the window may reopen off-screen. Delete `preferences.json` and relaunch the app.
2. If the Help window is minimized, reopening help may not restore it automatically. Restore it manually.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format
--------|------------------
**Add course** | `addcourse c/COURSE_CODE[,COURSE_CODE]...`
**List courses** | `listcourses`
**Remove course** | `removecourse c/COURSE_CODE[,COURSE_CODE]...`
**Add student** | `addstudent c/COURSE_CODE id/STUDENT_ID n/NAME [e/EMAIL]`
**List students** | `liststudents c/COURSE_CODE`
**Remove student** | `removestudent c/COURSE_CODE id/STUDENT_ID`
**Add assessment** | `addassessment c/COURSE_CODE an/ASSESSMENT_NAME m/MAX_SCORE`
**List assessments** | `listassessments [c/COURSE_CODE]`
**Remove assessment** | `removeassessment c/COURSE_CODE as/ASSESSMENT_INDEX`
**Add grade** | `addgrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX g/SCORE`
**Remove grade** | `removegrade c/COURSE_CODE id/STUDENT_ID as/ASSESSMENT_INDEX`
**List grades** | `listgrades c/COURSE_CODE` / `listgrades c/COURSE_CODE as/ASSESSMENT_INDEX` / `listgrades id/STUDENT_ID`
**List details** | `listdetails c/COURSE_CODE`
**Export course** | `exportcourse c/COURSE_CODE`
**View all** | `viewall`
**Help** | `help`
**Exit** | `exit`
