Finished:
View All Courses
Unenroll
Enroll 

Not Started:
View My Courses
Find Course by name, id and time
Displaying Current Capacity and Time offered for courses
I think it should look something like this:

Course Code: Course Name  -  Available Seats: Current Capacity
Offered Times: Day1: Time1 -  Day2: Time2
Course Code: Course Name  -  Available Seats: Current Capacity
Offered Times: Day1: Time1 -  Day2: Time2
Course Code: Course Name  -  Available Seats: Current Capacity
Offered Times: Day1: Time1 -  Day2: Time2
Course Code: Course Name  -  Available Seats: Current Capacity
Offered Times: Day1: Time1 -  Day2: Time2


Student Should: 
1. Be able to enroll in a course
2. Unenroll from a course
3. Can see the courses they are enrolled in 
4. Can see all courses 

Course code is unique
Store codes to Student, use the codes to find the courses later to display
Basically loop through the user's courses, 
take each course code and find it in the courses table, 
then use that information to pull the rest of the course details from the courses table 

Functionality 
1. Store how many students are currently enrolled in any given course
2. Return error if too many people are already enrolled 
3. Return an error if you have two courses that start at the same time


Searching for course by: 
Code and Name simple, do as previously done in instructor (using the getter functions made for them in dbHandler)
Time: Give accesss to drop down menu (Time filter) in the UI underneath the Course Name text field
When no filter is active, show all courses
All of this will be in the Show Courses Button (same as universal show courses)
