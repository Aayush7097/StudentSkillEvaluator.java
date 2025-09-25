import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Student {
    String name;
    String id;
    ArrayList<Integer> scores = new ArrayList<>();
    int total;
    double average;
    char grade;
    String feedback;

    // Constructor
    public Student(String name, String id, ArrayList<Integer> scores) {
        this.name = name;
        this.id = id;
        this.scores = scores;
        calculateResults();
    }

    // Calculate total, average, grade, feedback
    private void calculateResults() {
        total = 0;
        for (int score : scores) {
            total += score;
        }
        average = (double) total / scores.size();
        grade = calculateGrade(average);
        feedback = generateFeedback(grade);
    }

    // Grading logic
    private char calculateGrade(double avg) {
        if (avg >= 90) return 'A';
        else if (avg >= 75) return 'B';
        else if (avg >= 60) return 'C';
        else if (avg >= 40) return 'D';
        else return 'F';
    }

    // Feedback logic
    private String generateFeedback(char grade) {
        switch (grade) {
            case 'A': return "Excellent performance! Keep it up.";
            case 'B': return "Good job! Aim higher next time.";
            case 'C': return "Average work. You can improve.";
            case 'D': return "Needs improvement. Stay focused!";
            case 'F': return "Fail. Don’t give up, try again!";
            default: return "No feedback available.";
        }
    }

    // Print student report
    public void printReport(ArrayList<String> skillNames) {
        System.out.println("\n--- Student Report ---");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);

        System.out.println("\nSkill Scores:");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(skillNames.get(i) + ": " + scores.get(i));
        }

        System.out.println("\nTotal Marks: " + total);
        System.out.printf("Average Score: %.2f\n", average);
        System.out.println("Grade: " + grade);
        System.out.println("Feedback: " + feedback);
        System.out.println("----------------------\n");
    }

    // Export student report to file
    public void exportReport(ArrayList<String> skillNames) {
        try {
            FileWriter writer = new FileWriter(name + "_" + id + "_Report.txt");
            writer.write("Student Report\n");
            writer.write("Name: " + name + "\n");
            writer.write("ID: " + id + "\n\n");

            writer.write("Skill Scores:\n");
            for (int i = 0; i < scores.size(); i++) {
                writer.write(skillNames.get(i) + ": " + scores.get(i) + "\n");
            }

            writer.write("\nTotal Marks: " + total + "\n");
            writer.write(String.format("Average Score: %.2f\n", average));
            writer.write("Grade: " + grade + "\n");
            writer.write("Feedback: " + feedback + "\n");
            writer.close();

            System.out.println("✅ Report saved as " + name + "_" + id + "_Report.txt");
        } catch (IOException e) {
            System.out.println("❌ Error writing report file.");
        }
    }
}

public class StudentSkillEvaluator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Define skill areas
        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Programming");
        skillNames.add("Communication");
        skillNames.add("Problem-Solving");
        skillNames.add("Teamwork");
        skillNames.add("Creativity");

        ArrayList<Student> students = new ArrayList<>();

        System.out.println("===== Student Skill Evaluator =====");

        while (true) {
            System.out.print("\nEnter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter student ID: ");
            String id = sc.nextLine();

            ArrayList<Integer> scores = new ArrayList<>();
            for (String skill : skillNames) {
                System.out.print("Enter marks in " + skill + " (0-100): ");
                int mark = sc.nextInt();
                sc.nextLine(); // consume newline
                scores.add(mark);
            }

            // Create student object
            Student student = new Student(name, id, scores);
            students.add(student);

            // Print and export report
            student.printReport(skillNames);
            student.exportReport(skillNames);

            System.out.print("\nDo you want to add another student? (yes/no): ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) break;
        }

        // Display top performer
        if (!students.isEmpty()) {
            Student top = students.get(0);
            for (Student s : students) {
                if (s.average > top.average) {
                    top = s;
                }
            }
            System.out.println("\n🏆 Top Performer: " + top.name + " (ID: " + top.id + ") with Average: " + top.average);
        }

        System.out.println("\n===== Program Ended =====");
        sc.close();
    }
}
