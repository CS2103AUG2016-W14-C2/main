<center><h1>  Welcome to Menion </h1> </center>


(introduction by brehmer)

# Table of Contents

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)



## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
2. Download Menion: You can then download TaskManager.jar from the latest release here: 
   **COMING SOON**
3. Copy the file to the folder you want to use as the home folder for  Menion.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
   > <img src="images/MainPageLayout.jpg" width="600">

5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
6. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` Assignment 2 TASK_DEADLINE TASK_REMINDER PRIORITY NOTES...` : 
     adds a task named `Assignment 2` to the Menion, with high priority.
   * **`delete`**` 3` : deletes the 3rd task / event shown in the current list
   * **`exit`** : exits the app
7. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**<br>
> Words in `UPPER_CASE` are the parameters.<br>
> Items in `[SQUARE_BRACKETS]` are optional.<br>
> Items with `...` after them can have multiple instances.<br>
> The order of parameters is fixed.

 
#### Adding a person : `add`
Adds a person to Menion<br>
Format : <br>
`add TASK_NAME by TASK_DEADLINE [n:NOTES...]` <br>
`add EVENT_NAME from EVENT_START_TIME EVENT_START_DATE to EVENT_END_TIME EVENT_END_DATE [n:NOTES...]`<br>
`add FLOATING_TASK_NAME [n:NOTES...]`<br> 

> Task and event are differentiated by the input of time tag. Each activity can have any number of notes (including 0). Each note is limited to 140 characters.


Examples: 
`add Upload CS3230 Programming Assignment 2 d/16-10-16 r/false p/low n/Upload it onto Coursemology Portal`
`add Dinner With Family d/21-11-16 t/1800-2000 r/true p/high n/Wear formal`

#### Listing all events and tasks : `list all`
Shows a list of all persons in the Menion.<br>
Format : `list all`

#### Listing all events : `list events`
Shows a list of all events in the Menion for the day sorted according to the start date and time.<br>
Format : `list events`

#### Listing all tasks : `list tasks`
Shows a list of all tasks in the Menion sorted according to their priority.<br>
Format : `list tasks`

#### Listing all events and tasks of the specified date : `list day/week/month/date`
Shows a list of all tasks and events in the Menion for the day/week/month/date sorted according to priority.<br>
Format : `list day/week/month/date`

#### Listing all events and tasks of the specified range of dates : `list day to day`
Shows a list of all tasks and events in the Menion for the range of days sorted according to date.<br>
Format : `list day to day`

Examples:
* `list 10-10-16 to 20-10-16

#### Listing all block-out dates : `list blocks`
Shows a list of all blocked events and their respective timeslot in the Menion sorted according to priority.<br>
Format : `list block`

#### Finding all tasks or events containing any keyword in their name : `find task/event`
Finds all tasks whose names contain any of the given keywords.<br>
Format : `list KEYWORD [MORE_KEYWORDS]`
>* The search is not case sensitive. e.g. `sleep` will match `Sleep`
>* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
>* Only the name of task is searched.
>* Only full words will be matched e.g. `sleep` will not match `sleeping`
>* Task name matching at least one keyword will be returned (i.e `OR` search). e.g. `sleep` will match `sleep for 8 hours`

Examples
* find `Sleep`
* Displays : sleep for 8 hours
* find Go to gym
* Displays: any task having the keywords go, to, gym

#### Deleting a task / event : `delete`
Deletes the specified task/event  from the Menion. Irreversible.<br>
Format : `delete INDEX`

>Deletes the task/event at the specified `INDEX`. The index refers to the index number shown beside it.

The index must be a positive integer 1,2,3,...

Examples:
* delete 2
* deletes the 2nd task/event in the Menion.

#### Updating a task : `edit`
Updates a specified task from the Menion.
<br>
Format : `edit INDEX`
> Edits the task at the specified `INDEX`. The index refers to the index number shown beside the task. The order of the tag to edit is not important.

> The index must be a positive integer 1,2,3, ...

Examples :
* `edit 3`
* `d/dd-mm-yy p/low`
* `edit 2`
* `r/false d/dd-mm-yy`
* `edit 5`
* `t/2030-2200`

#### Blocking out dates : `block`
Block a date and time for uncertain task.<br>
Format : `block EVENT_NAME DATE TIME, DATE TIME, ...`

> BLOCK THE `DATE` and `TIME` of specified event. There can be multiple block `DATE` and `TIME` in a block command. If timeslot is taken, system will inform user with a error message.

Examples:
* `block Dinner with Friends d/20-02-16 t/2000-2200, d/21-02-16 t/2000-2200`

#### Confirm block event : `confirm`
Confirm an event, and removing other block-outs of the event.<br>
Format : `confirm EVENT_INDEX.TIMESLOT_INDEX`

> Confirms the event at the specified `INDEX.INDEX` This refers to the `INDEX.INDEX` shown beside the event.

Examples: 
* confirm 1.a
* confirm 2.c

#### Clearing all entries : `clear`
Clear all entries from the Menion.<br>
Format : `clear`

#### Modifying the storage path : `modify storage path`
Modify the storage path that stores all the data.
Format : `modify storage path STORAGE_LOCATION`

#### Exiting the program : `exit`
Exits the program.
Format : `exit`

#### Viewing help : `help`
Format : `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Saving the data 
Menion data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.<br>



## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Menion folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME d/DEADLINE_TASK r/TASK_REMINDER p/PRIORITY n/NOTES...`
Block | `block TASK_NAME d/DATE t/1111-2222, [MORE_DATE MORE_TIME]`
Confirm Blocked Event | `confirm EVENT_INDEX.TIMESLOT_INDEX`
Clear | `clear`
Delete | `delete INDEX`
Exit | `exit`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Modify Storage Path | `modify storage path STORAGE_LOCATION`
