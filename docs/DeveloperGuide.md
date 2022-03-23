---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagramUpdated.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature

The add mechanism implements the following sequence and interactions for the method call execute("add NEW_PERSON_TAGS") on a LogicManager object where NEW_PERSON_TAGS refers to the tags of a person to be added. 

![AddSequenceDiagram](images/AddSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `AddCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Replace `NEW_PERSON_TAGS` in the sequence diagram with the tags stated in the notes shown in the sequence diagram.

### Summarise feature

The summarise mechanism implements the following sequence and interactions for the method call execute("summarise") on a LogicManager object.

![SummariseSequenceDiagram](images/SummariseSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `SummariseCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

When execute is called on the SummariseCommand object, there are multiple call back to self to anaylse and produce the result back to the Logic Manager.

#### Pie Chart Window

##### How the feature is implemented

**New class** <br>
A `PieChartWindow` controller class and a `PieChartWindow` FXML class is created for this feature.
The `PieChartWindow` controller class will create pie charts using data from `SummariseCommand#getPositiveStatsByFacultyData()` and `SummariseCommand#getCovidStatsByBlockDataList()`.

Addition of method(s) to `PieChartWindow` controller class:
* `PieChartWindow#execute()` puts all of the pie chart into a `VBox` and set the scene for the `PieChartWindow` FXML class
* `PieChartWindow#makePieChart()` creates a pie chart
* `PieChartWindow#collateBlocksChart()` creates a pie chart using `PieChartWindow#makePieChart()` for each block that shows covid positive, negative and HRN percentage for contacts present in Tracey and adds it to a `HBox`
* `PieChartWindow#createFacultyChartPositive()` creates a pie chart using `PieChartWindow#makePieChart()` that shows the covid positive percentage for all faculties for each contact present in Tracey and adds it to a `HBox`
* `PieChartWindow#makePieChartScene()` creates the scene for the pie charts
* `PieChartWindow#show()` show the pie chart window
* `PieChartWindow#isShowing()` returns a boolean whether the pie chart window is showing
* `PieChartWindow#hide()` hide the pie chart window
<br>
**Modifications to `SummariseCommand`** <br>
Addition of variable(s):
* `positiveStatsByFacultyData`, a private static variable of type `TreeMap<String, Double>`
* `covidStatsByBlockDataList`, a private static variable of type `TreeMap<String, TreeMap<String, Double>>`
<br>
Modifications to existing method(s):
* `SummariseCommand#summariseFaculty()` will put in `numberOfPositive` (percentage of covid-positive cases in its respective faculty) for each `facultyName` (pre-defined constants from the `Faculty` class) into `positiveStatsByFacultyData` for contacts that are present in Tracey.
* `SummariseCommand#summariseBlock()` will put in `numberOfPositive`, `numberOfNegative` and `numberOfHrn` for each `blockLetter` (pre-defined constants from the `Block` class) into a local variable of type `TreeMap<String, Double>` and then puts into `covidStatsByBlockDataList` for each for contacts that are present in Tracey.
<br>
Addition of method(s):
* `SummariseCommand#getPositiveStatsByFacultyData()` getter method that returns `positiveStatsByFacultyData`
* `SummariseCommand#getCovidStatsByBlockDataList()` getter method that returns `covidStatsByBlockDataList`
<br>
**Modifications to `MainWindow`**
Modifications to existing method(s):
* `MainWindow#executeCommand()` will now check if the command is a `SummariseCommand`, if it is then `MainWindow#handleSummarise()` will be invoked
Addition of method(s):
* `MainWindow#handleSummarise()` shows the pie chart window if it is not already open, or else will reopen a new pie chart window

[Insert Diagram here]

##### Why it is implemented that way
The data needed for the pie charts should be coupled with `SummariseCommand`, therefore it is necessary to implement this feature in such a way that the pie chart data is created upon invocation `SummariseCommand`. A `PieChartWindow` controller and FXML class is also needed to abstract the creation of the pie charts and opening a new window respectively. The `MainWindow` class is then modified accordingly.

##### Alternatives considered

**Aspect: How data is passed to the pie charts:**

* **Alternative 1 (current choice):** `SummariseCommand` will pass in necessary data into data structures (`TreeMap` in this case) upon invocation which then can be obtained using getter methods
    * Pros: Easy to implement.
    * Cons: Dependent on the `SummariseCommand` class to pass in correct inputs.
    * Other consideration(s):
      * Use the Singleton design principle for the data structures. 

* **Alternative 2:** Parse the feedback to user message from `SummariseCommand`
    * Pros: No modifications to the `SummariseCommand` class.
    * Cons: Dependent on the feedback message, need to implement complicated methods to parse the message, parsing methods need to be modified if the format of the feedback message is changed.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

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

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

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

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts for contact-tracing of students
* has a simple-to-use product to check on health status of students
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

Keep track of students’ covid status. This is a central repository for covid status updating to ease the facilitation of
management of NUS students across different faculties. It will be easier to read and update covid status.

Every students’ info in one integrated application platform. The app will help to manage students across different
faculties within NUS (no support for other schools).



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                 | I want to …​                     | So that I can…​                                                |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | IT admin                                   | obtain student contact information | quickly contact students if required                               |
| `* * *`  | IT admin                                   | get a list of students with covid  |  find out any possible hotspots                                    |
| `* * *`  | IT admin                                   | save the data locally              | for easy dissemination of information to other admins              |
| `* * *`  | IT admin                                   | find details of a particular student | follow up with checking on the student                           |
| `* * *`  | IT admin                                   | add a student’s details into the system | store their details for reference                             |
| `* * *`  | First time user                            | add a list of students with their info into the system  | have a centralised hub for covid health status |
| `* * *`  | IT admin                                   | delete a user from the system      | remove wrongly keyed in inputs from the system                     |
| `* * *`  | IT admin                                   | clear the system database          | quickly restore the initial state of the system and to start on a clean slate again             |
| `* * *`  | IT admin                                   | edit a contact’s information       | can modify any change in contact details regarding a user          |
| `* * *`  | Professors                                 | get the statistics of covid cases among different groups of students | Can use for further medical research |
| `* *`    | IT admin                                   | seek help if unfamiliar with Tracey | straighten out any uncertainties regarding the usage of the system |
| `* *`    | IT admin                                   | easily keep track and update  the covid status of students  | use data to inform relevant module lecturers and TAs |
| `* *`    | IT admin                                   | see when a particular student started having covid symptoms | find who is the superspreader             |
| `* *`    | IT admin                                   | store the date when the student tested positive for covid   | check the duration for which the student has to be isolated for                |
| `* *`    | Busy admin                                 | mass send emails to specific students | check on their health                                           |
| `* *`    | IT admin                                   | be notified of when a student’s status change   | follow up on their health                             |
| `* *`    | OCD IT admin                               | categorise contacts according to faculty | I am happy                                                   |
| `* *`    | IT admin                                   | filter out those students with covid easily     | write out future modules plan for different faculty   |
| `* *`    | IT admin                                   | easily export data from the application  | To show my boss                                              |
| `* *`    | User                                       | send contact info of someone    | his/her TA can contact the student only if TA is allowed to do so              |
| `* *`    | IT admin                                   | mass send emails to the professors of affected students  | inform the relevant TAs and professors about their absence in class              |
| `* *`    | UHC people                                 | get the number of students with covid    | prepare enough medical resources accordingly                 |
| `* *`    | IT admin                                   | easy method to Import data into the app  | save the hassle                                              |
| `* *`    | Hostel Management people                   | Retrieve the number of students with covid in the different hostel  | Can plan out the number of rooms for covid use like quarantine etc.                |
| `* *`    | User                                       | have a quick keyword search     | to find a specific person if unsure of his full name or complete contact number                |
| `* *`    | Professor                                  | be informed when one of my students are covid positive     | make arrangements for makeup class or examinations                |
| `* *`    | IT admin                                   | add the last known location of where a particular student caught the virus     | manage the severity of the hotspot and choose to take follow up actions              |
| `* `    | Parents                                     | get the name of students with covid      | check if their children or friends or neighbors’ children got covid or not               |
| `* `    | NUS President                               | know covid status of students  and staffs     | come up with policies                                   |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Tracey` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a student**

**MSS**

1.  User requests to list students
2.  Tracey shows a list of students
3.  User requests to delete a specific student in the list
4.  Tracey deletes the person

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tracey shows an error message.

      Use case resumes at step 2.

**Use case: Search for a student**

**MSS**

1.  User requests to search for student
2.  Tracey shows the info of student with that matching name

    Use case ends.

**Extensions**

* 2a. The list does not contain student(s) of that name.

  Use case ends.

* 2b. The given student name exists multiple places on the list.

    * 2b1. Tracey shows a list of students with the name with their info.

      Use case ends.

**Use case: Add a student into Tracey**

**MSS**

1.  User requests to add the student with his/her details such as year, faculty, covid status
2.  Tracey shows the info of student with that matching name

    Use case ends.

**Extensions**

* 1a. The student info has an invalid format.

    * 1a1. Tracey will inform user that he/she did not provide the correct information.

    * 1a2. User provide the correct details in the correct format.

* 1b. The student to be added already exist in the list by Tracey.

    * 1b1. Tracey inform user that the contact exist in her.
    
* 1c. User adds multiple students in one go.
  
    * 1c1. Tracey will list out a list of new students added with their info.

* 1d. User uses wrong pre-defined constants for fields such as faculty or covid status.

    * 1d1. Tracey will provide a list of pre-defined constants for the user.

    * 1d2. User use the correct pre-defined constants for the respective tags.
    
**Use case: Edit information of a student**

**MSS**

1.  User requests to list students
2.  Tracey shows a list of students
3.  User requests to edit a specific student in the list
4.  Tracey updates details of the person

    Use case ends.

**Extensions**

* 2a. The list does not contain student(s) of that name.

  Use case ends.

* 2b. The given student name exists multiple places on the list.

    * 2b1. Tracey will inform user that he/she did not provide the correct information formatting.

    * 2b2. User will key in the correct format to edit student.

**Use case: Clear the system database**

**MSS**

1.  User requests to clear all students
2.  Tracey shows an empty list of students

    Use case ends.

**Extensions**

* 2a. The list does not contain student(s) of that name.

  Use case ends.

**Use case: Summarize the system database for number of covid patient**

**MSS**

1.  User requests to summarise number of students with covid
2.  Tracey shows lists of students of that with the covid positive status

    Use case ends.

**Extensions**

* 2a. The list with that tag does not contain student(s) of that name.

  Use case ends.

**Use case: List all students**

**MSS**

1.  User requests to list all students
2.  Tracey shows a list of students

    Use case ends.

**Extensions**

* 2a. The list does not contain student(s) of that name.

  Use case ends.

**Use case: Request for help from Tracey**

**MSS**

1.  User requests Tracey for more details on what she can do.
2.  Tracey redirects user to user guide.

    Use case ends.

**Extensions**

* 2a. The user guide is empty.

  Use case ends.

**Use case: Filter the system database for list overview by filter**

**MSS**

1.  User requests to filter all students by a tag (ie. year)
2.  Tracey shows a list of students of that specific tag with the covid status

    Use case ends.

**Extensions**

* 2a. The list with that tag does not contain student(s) of that name.

  Use case ends.

### Non-Functional Requirements

1.  Application is offered free.
2.  Has storage function
3. Source code is open source
4. The user interface should be intuitive enough for users who are not IT-savvy.
5. Should be compatible with Mac and Windows
6. A record table should be able to have up to 5000 NUS students.
7. the application should work on both 32-bit and 64-bit environments.
8. The application will always answer the user.
9. The application cannot handle image input.
10. The application cannot store 2 or more schools into a instance of application
11. Product is not required to handle printing of reports
12. Product should respond within 10 seconds.


*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Student Status**: A student detail that indicates whether the student has Covid-19
* **Health Risk Warning**: A warning that informs school and students for high health risk
* **Covid-19**: An infectious disease caused by the SARS-CoV-2 virus.
* **NUS IT**: The information technology department at the National University of Singapore

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_


1. _{ more test cases …​ }_

