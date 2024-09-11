import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BankingGame extends JFrame
{
    private int randomNumber;
    private int attemptsLeft;
    private int score;
    private int round;
    private static final int MAX_ATTEMPTS = 5;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;

    private JLabel roundLabel, scoreLabel, attemptsLabel, feedbackLabel;
    private JTextField guessInput;
    private JButton submitButton, newRoundButton;

    public BankingGame()
    {
        setTitle("Banking Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        round = 1;
        score = 0;

        roundLabel = new JLabel("Round: 1");
        scoreLabel = new JLabel("Score: 0");
        attemptsLabel = new JLabel("Attempts Left: " + MAX_ATTEMPTS);
        feedbackLabel = new JLabel("Guess a number between " + MIN_NUMBER + " and " + MAX_NUMBER);

        guessInput = new JTextField();
        submitButton = new JButton("Submit Guess");
        newRoundButton = new JButton("New Round");
        newRoundButton.setEnabled(false);

        add(roundLabel);
        add(scoreLabel);
        add(attemptsLabel);
        add(feedbackLabel);
        add(guessInput);
        add(submitButton);

        startNewRound();

        submitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int guess = Integer.parseInt(guessInput.getText());
                    checkGuess(guess);
                }

                catch (NumberFormatException ex)
                {
                    feedbackLabel.setText("Please enter a valid number.");
                }
            }
        });

        newRoundButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startNewRound();
            }
        });

        setVisible(true);
    }

    private void startNewRound()
    {
        Random rand = new Random();
        randomNumber = rand.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        attemptsLeft = MAX_ATTEMPTS;
        round++;
        roundLabel.setText("Round: " + round);
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        feedbackLabel.setText("Guess a number between " + MIN_NUMBER + " and " + MAX_NUMBER);
        guessInput.setText("");
        submitButton.setEnabled(true);
        newRoundButton.setEnabled(false);
    }

    private void checkGuess(int guess)
    {
        attemptsLeft--;
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);

        if (guess == randomNumber)
        {
            feedbackLabel.setText("Correct! You win this round.");
            score++;
            scoreLabel.setText("Score: " + score);
            endRound();
        } 
        
        else if (guess > randomNumber)
        {
            feedbackLabel.setText("Too high! Try again.");
        }

        else
        {
            feedbackLabel.setText("Too low! Try again.");
        }

        if (attemptsLeft == 0 && guess != randomNumber)
        {
            feedbackLabel.setText("Out of attempts! The correct number was " + randomNumber);
            endRound();
        }
    }

    private void endRound()
    {
        submitButton.setEnabled(false);
        newRoundButton.setEnabled(true);
        add(newRoundButton);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new BankingGame());
    }
}
