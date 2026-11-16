# COMP3005 Assignment 3

Java app that connects to a PostgreSQL database and does basic CRUD on a students table.

## What's in this repo

- schema.sql — creates the students table and inserts the starting data
- assignment3.java — the application with the four functions
- postgresql-42.7.14.jar — PostgreSQL JDBC driver (from https://jdbc.postgresql.org/download/)

## Database setup

1. Open pgAdmin and create a database (I used "Assignment 3").
2. Open the Query Tool for that database.
3. Run everything in schema.sql.
4. Check with SELECT * FROM students; you should see 3 students.

If you need to reset the table back to the original data, drop it first then run schema.sql again:

DROP TABLE IF EXISTS students;

Then run the rest of schema.sql

In assignment3.java, update the url, user, and password to match your own Postgres setup in pgAdmin. You also need a JDK installed.

## How to compile and run

From the project folder:

export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-25.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

javac -cp "postgresql-42.7.14.jar" assignment3.java
java -cp ".:postgresql-42.7.14.jar" assignment3

If you change the code, compile again before running. If you didn't change anything, you can just run the java command.

If you use Windows, use a semicolon in the classpath instead of a colon:
java -cp ".;postgresql-42.7.14.jar" assignment3

## Functions

getAllStudents() — prints all rows from the students table
addStudent(first_name, last_name, email, enrollment_date) — inserts a new student
updateStudentEmail(student_id, new_email) — updates that student's email
deleteStudent(student_id) — deletes that student
