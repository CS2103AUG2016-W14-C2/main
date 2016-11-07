Script for manual testing

Setting up the test files:

1. Navigate to \src\test\data\ManualTesting\
2. Look for SampleData.xml
3. Copy this file and navigate to \data\
4. Copy SampleData.xml into \data\ 
5. Rename SampleData.xml to menion.xml and overwrite the previous menion.xml
6. Now the test file is loaded, run the program and it should display the test file


Help Command test:

1. input: help
   output: a image of all the commands will be available


Add Command test:

1. input: add floatingTest
   output: a floating task "floatingTest" will be added and there will be no note

2. input: add floatTestWithNote n: this is a floating task
   output: a floating task "floatTestWithNote" will be added and there will be a note "this is a floating task"

3. input: add taskTest by: today 2359
   output: a task "taskTest" with deadline with today's date and time of 2359 will be added and there will be no note

4. input: add taskTestWithNoTime by: today n: this is a task with no time
   output: a task "taskTestWithNoTimeAndNote" will be added with today's date and no time will be added 
           and there will be a note "this is a task with no time"

5. input: add eventTest from: today 2300 to: today 2359
   output: an event "eventTest" will be added with today's date as start date and end date and start time of 2300 and end time of 2359

6. input: add eventTestWithNoTime from: today to: today n: this is a event with no start and end time
   output: an event "eventTestWithNoTime" will be added with today's date as start date and end date and no start and end time
           and there will be a note "this is a event with no start and end time"   


Delete Command test:

1. input: delete floating 15
	output: the floating task "floatingTest" will be deleted

2. input: delete task 3 5
	output: the task "taskTest" and "testTaskWithNoName" will be deleted


Edit Command test:

1. input: edit floating 1 name: HelloWorld new name
   output: changes the activity's name in index 1 of floating, from "Buy Lunch" to "HelloWorld new name"

2. input: edit floating 1 n: HelloWorld new note
   output: changes the activity's note in index 1 of floating, from "Spend less than $20" to "HelloWorld new note"

3. input: edit floating 1 by: 08 oct 2016 6pm
   output: changes the activity in index 1 of floating, into a Task type. the floating card will be removed and a new card will be added
   to the Task type with the appropriate date and time parameters. It is now in index 1 as it is the earliest task.

4. input: edit floating 1 by: 08 oct 2016 8pm
   output: changes the activity in index 1 of floating, into a Task type. The Tloating card will be removed and a new card will be added
   to the Task type with the appropriate date and time parameters. It is now in index 3 as it is the 3rd earliest task.

5. input: edit task 2 by: 07 oct 2016
   output: changes the deadline of the activity in index 2 from "08 Oct 2016 1900" to "07 Oct 2016". Since time is not specified, it will not
	be included. The task now shifts to index 1 as it is the earliest task.

6. input: edit task 3 to: floating
   output: changes the activity in index 3 of Task, into a Floating type. The Task card will be removed and a new card will be added
   to the Floating type without any date and time parameters. It is now in index 14 in Floating.

7. input: edit event 1 from: 09 oct
   output: changes the "From" date of activity in index 1 of Event, from "10 Oct 2016 1400" to "09 Oct 2016". It is still in index 1, as it is the earliest Event
   in starting time.

8. input: edit event 1 to: 8pm
   output: changes the "To" date of activity in index 1 of Event, from "10 Oct 2016 1800" to "[today's date] 2000". 


Find Command test:

1. input: find cs2103
   output: All the activities that has keyword "cs2103" in the name will be displayed


List Command test:

1. input: list completed
   output: all activities which have been marked completed will be displayed

2. input: list uncompleted
   output: all activities which have been marked uncompleted will be displayed

3. input: list 08-10-2016
   output: only the task named: complete cs2101 op2 script will be displayed

4. input: list october
   output: all activities which falls on october will be displayed

5. input: list wrongarguments
   output: an wrong arguments error message will be shown

6. input: list all
   output: all activities stored in Menion will be displayed



Complete Command test:

1. input: complete floating 1
   output: completes the activity, in index 1 of Floating. Brings the now completed Floating to index 14 in the Floating list

2. input: complete task 1
   output: completes the activity, in index 1 of Task. Brings the now completed Task to index 10 in the Task list. Since completed tasks are sorted according
   to date as well


Uncomplete Command test:

1. input: uncomplete floating 14
   output: uncompletes the activity, in index 14 of Floating. Completed status icon is no longer shown beside the Floating card, and the card stays at it's index.


Modify Storage Path command test:

1. input: modify testData/menion.xml
   output: the storage file location at the bottom of the UI will be updated and 
   		   new storage file will be in user home directory 


Undo/Redo Command test:

1. input: undo modify
   output: the storage file location will be undo back to the original file path which is data/menion.xml

2. input: redo modify 
   output: the storage file location will be redo back to the testData/menion.xml in the user home directory

3. input: undo modify
   output: go back the the orignal file location in the first place
   
3. input: undo
   output: undo the previous uncompleted floating task

4. input: redo
   output: redo the previous undo command


Remind Command test:

1. input: remind [Email_Address]
   output: creates an "email.txt." file in the Menion folder, which stores the permission to send email "True" or "False" in the first line. Followed by
   the [Email_Address] given in the second line.


Unremind Command test:

1. input: unremind
   output: modifies the first line of existing "email.txt." file. changes it from "True" to "False" thus removing the permission for Menion to send email.


Clear Command test:

1. input: clear
   output: activities will be cleared and activity manager will be empty

