
CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR (100),
        email VARCHAR (100)
);

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShowDataGui {
    private JFrame frame;
    private JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShowDataGui().createGUI());
    }

    public void createGUI() {
        frame = new JFrame("Database Viewer");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Load Data");
        loadButton.addActionListener(e -> loadData());
        frame.add(loadButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void loadData() {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "yourpassword";

        String query = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            textArea.setText("");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                textArea.append("ID: " + id + ", Name: " + name + ",Email: " + email + "/n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading data", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}


