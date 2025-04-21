import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * RecursiveLister is a Swing-based Java application that allows the user
 * to select a directory and recursively lists all files in it and its subdirectories.
 * The file list is displayed in a scrollable text area.
 */
public class RecursiveLister extends JFrame {
    private JTextArea displayArea;
    private JScrollPane scrollPane;
    private JButton startButton;
    private JButton quitButton;
    private JLabel titleLabel;

    /**
     * Constructor: sets up the entire GUI layout and all event listeners.
     */
    public RecursiveLister() {
        super("Recursive File Lister");
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("Recursive File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Text area setup
        displayArea = new JTextArea(25, 50);
        displayArea.setEditable(false);
        scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Start button logic
        startButton.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDir = chooser.getSelectedFile();
                displayArea.setText(""); // Clear previous results
                StringBuilder output = new StringBuilder();
                listFilesRecursive(selectedDir, output);
                displayArea.setText(output.toString());
            }
        });

        // Quit button logic
        quitButton.addActionListener((ActionEvent e) -> System.exit(0));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    /**
     * Recursively lists all files and directories in the selected directory.
     *
     * @param dir    the starting directory
     * @param output the StringBuilder to collect the file paths
     */
    private void listFilesRecursive(File dir, StringBuilder output) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            output.append(file.getAbsolutePath()).append("\n");
            if (file.isDirectory()) {
                listFilesRecursive(file, output);  // Recurse into subdirectory
            }
        }
    }

    /**
     * Main method to launch the GUI.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecursiveLister::new);
    }
}
