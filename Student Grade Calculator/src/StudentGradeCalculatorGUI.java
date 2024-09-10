import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculatorGUI extends JFrame
{
    private JTextField[] subjectFields;
    private JButton calculateButton;
    private JLabel resultLabel;
    private int numSubjects;

    public StudentGradeCalculatorGUI(int numSubjects)
    {
        this.numSubjects = numSubjects;

        setTitle("Student Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(numSubjects + 2, 2));

        subjectFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++)
        {
            JLabel subjectLabel = new JLabel("Subject " + (i + 1) + " Marks (out of 100):");
            subjectFields[i] = new JTextField();
            add(subjectLabel);
            add(subjectFields[i]);
        }

        calculateButton = new JButton("Calculate Grade");
        resultLabel = new JLabel("Enter marks and press 'Calculate'");

        add(calculateButton);
        add(resultLabel);

        calculateButton.addActionListener(new ActionListener()
        {
            @Override // This was giving me an error! Its working now.
            public void actionPerformed(ActionEvent e)
            {
                calculateGrade();
            }
        });

        setVisible(true);
    }

    private void calculateGrade()
    {
        int totalMarks = 0;
        boolean validInput = true;

        for (int i = 0; i < numSubjects; i++)
        {
            try
            {
                int marks = Integer.parseInt(subjectFields[i].getText());
                if (marks < 0 || marks > 100) {
                    resultLabel.setText("Marks must be between 0 and 100.");
                    validInput = false;
                    break;
                }
                totalMarks += marks;
            }

            catch (NumberFormatException ex)
            {
                resultLabel.setText("Please enter valid numbers for all subjects.");
                validInput = false;
                break;
            }
        }

        if (validInput)
        {
            double averagePercentage = (double) totalMarks / numSubjects;

            String grade;
            if (averagePercentage >= 90)
            {
                grade = "A+";
            }

            else if (averagePercentage >= 80)
            {
                grade = "A";
            }

            else if (averagePercentage >= 70)
            {
                grade = "B";
            }

            else if (averagePercentage >= 60)
            {
                grade = "C";
            }

            else if (averagePercentage >= 50)
            {
                grade = "D";
            }

            else
            {
                grade = "F";
            }

            resultLabel.setText("Total Marks: " + totalMarks + ", Avg: " + averagePercentage + "%, Grade: " + grade);
        }
    }

    public static void main(String[] args)
    {
        String input = JOptionPane.showInputDialog(null, "Enter number of subjects:");

        try
        {
            int numSubjects = Integer.parseInt(input);

            if (numSubjects > 0)
            {
                SwingUtilities.invokeLater(() -> new StudentGradeCalculatorGUI(numSubjects));
            }

            else
            {
                JOptionPane.showMessageDialog(null, "Please enter a valid number of subjects.");
            }
        }

        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
        }
    }
}