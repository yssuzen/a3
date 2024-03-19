# a3/studentApp
Video Link: https://drive.google.com/file/d/15N671GI-AV7xArBKz4tPDTIFeeCqlMII/view?usp=sharing

1. Created a new database named `a3` in your PostgreSQL environment using pgAdmin 4.

2. Executed the following SQL query to create the `students` table within the `a3` database:

`sql`
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    enrollment_date DATE
);

3. Populated the students table with initial data using the following SQL query:
`sql query`
INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');

**Compiling and Running the Program**

Compile the Java program using the following command in the terminal:
javac studentApp.java

Run the compiled program with the following command:
java -cp ".:postgresql-42.7.3.jar" studentApp

The program will start running in the terminal, displaying a menu for various operations on the student database. Follow the prompts in the program to perform desired actions.

Note: It is assumed that the computer has the jdbc.jar file installed and configured for the program to function. I have uploaded zip file for source code and additional resources in case of any issues. 
