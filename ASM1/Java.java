
import java.util.Scanner;
import java.util.Stack;

class Student {

    int id;
    String name;
    double score;

    public Student(int id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Score: " + score;
    }
}

class StudentStack {

    Stack<Student> stacks;

    public StudentStack() {
        stacks = new Stack<>();
    }

    public void addStudent(int id, String name, double score) {
        stacks.push(new Student(id, name, score));
    }

    public void removeStudentById(int id) {
        Stack<Student> tempStack = new Stack<>();
        boolean found = false;

        while (!stacks.isEmpty()) {
            Student student = stacks.pop();
            if (student.id == id) {
                found = true;
                System.out.println("Removed Student: " + student);
                break;
            }
            tempStack.push(student);
        }

        while (!tempStack.isEmpty()) {
            stacks.push(tempStack.pop());
        }

        if (!found) {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    public void viewAllStudents() {
        if (!stacks.isEmpty()) {
            System.out.println("All Students in Stack:");
            for (Student student : stacks) {
                System.out.println(student);
            }
        } else {
            System.out.println("Stack is empty!");
        }
    }

    public void editStudentById(int id, String newName, double newScore) {
        Stack<Student> tempStack = new Stack<>();
        boolean found = false;

        while (!stacks.isEmpty()) {
            Student student = stacks.pop();
            if (student.id == id) {
                student.name = newName;
                student.score = newScore;
                found = true;
            }
            tempStack.push(student);
        }

        while (!tempStack.isEmpty()) {
            stacks.push(tempStack.pop());
        }

        if (found) {
            System.out.println("Updated student with ID " + id);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
}

public class Java {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentStack studentStack = new StudentStack();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student by ID");
            System.out.println("3. View All Students");
            System.out.println("4. Edit Student by ID");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Score: ");
                    double score = scanner.nextDouble();
                    studentStack.addStudent(id, name, score);
                    break;
                case 2:
                    System.out.print("Enter ID to remove: ");
                    int removeId = scanner.nextInt();
                    studentStack.removeStudentById(removeId);
                    break;
                case 3:
                    studentStack.viewAllStudents();
                    break;
                case 4:
                    System.out.print("Enter ID to edit: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Score: ");
                    double newScore = scanner.nextDouble();
                    studentStack.editStudentById(editId, newName, newScore);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
