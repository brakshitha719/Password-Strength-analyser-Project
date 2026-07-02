import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordStrengthGUI extends JFrame {

    JPasswordField passwordField;
    JButton checkButton;
    JLabel resultLabel;
    JProgressBar progressBar;
    JTextArea feedbackArea;

    public PasswordStrengthGUI() {
        setTitle("Password Strength Analyzer");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Label
        add(new JLabel("Enter Password:"));

        // Password field
        passwordField = new JPasswordField(20);
        add(passwordField);

        // Button
        checkButton = new JButton("Check Strength");
        add(checkButton);

        // Result label
        resultLabel = new JLabel("Strength: ");
        add(resultLabel);

        // Progress bar
        progressBar = new JProgressBar(0, 5);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        add(progressBar);

        // Feedback area
        feedbackArea = new JTextArea(10, 30);
        feedbackArea.setEditable(false);
        add(new JScrollPane(feedbackArea));

        // Button click event
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());

                int score = calculateStrength(password);

                resultLabel.setText("Strength: " + getRating(score));
                progressBar.setValue(score);

                feedbackArea.setText(showMistakes(password));
            }
        });
    }

    // Calculate password strength
    public int calculateStrength(String password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        boolean lengthOK = password.length() >= 8;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (!Character.isWhitespace(c))
                hasSpecial = true;
        }

        int score = 0;

        if (lengthOK) score++;
        if (hasUpper) score++;
        if (hasLower) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;

        return score;
    }

    // Show missing rules
    public String showMistakes(String password) {
        String feedback = "";

        if (password.length() < 8)
            feedback += "-> Password must be at least 8 characters long\n";
        else
            feedback += "-> Length rule satisfied\n";

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (!Character.isWhitespace(c))
                hasSpecial = true;
        }

        if (!hasUpper)
            feedback += "-> Missing uppercase letter (A-Z)\n";
        else
            feedback += "-> Uppercase rule satisfied\n";

        if (!hasLower)
            feedback += "-> Missing lowercase letter (a-z)\n";
        else
            feedback += "-> Lowercase rule satisfied\n";

        if (!hasDigit)
            feedback += "-> Missing digit (0-9)\n";
        else
            feedback += "-> Digit rule satisfied\n";

        if (!hasSpecial)
            feedback += "-> Missing special character (!@#$%^&* etc.)\n";
        else
            feedback += "-> Special character rule satisfied\n";

        if (password.length() >= 8 && hasUpper && hasLower && hasDigit && hasSpecial)
            feedback += "\n-> Your password satisfies all security rules!";

        return feedback;
    }

    // Rating system
    public String getRating(int score) {
        switch (score) {
            case 5:
                return "Very Strong";
            case 4:
                return "Strong";
            case 3:
                return "Moderate";
            case 2:
                return "Weak";
            default:
                return "Very Weak";
        }
    }

    // Main method
    public static void main(String[] args) {
        new PasswordStrengthGUI().setVisible(true);
    }
}
