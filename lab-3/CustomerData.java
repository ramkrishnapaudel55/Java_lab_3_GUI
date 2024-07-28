import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CustomerData {

    private JTable table;

    public CustomerData() {
        // Create table model
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Add columns to the model
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Address");

        // Fetch data from database
        final String url = "jdbc:mysql://localhost:3306/customer";
        final String user = "root";
        final String password = "Sourcecode#007";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customer")) {

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("email");
                row[3] = rs.getString("phone");
                row[4] = rs.getString("address");
                model.addRow(row);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public JTable getTable() {
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Customer Table");
            CustomerData example = new CustomerData();
            JTable table = example.getTable();
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
