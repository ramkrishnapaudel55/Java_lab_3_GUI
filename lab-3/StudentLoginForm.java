import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class StudentLoginForm {
    public static void main(String[] args){
        JFrame frame = new JFrame("Student Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocation(400, 50);

        JPanel panel = new JPanel(new GridLayout(30, 2, 5, 5));
        panel.setPreferredSize(new Dimension(800, 1400)); // Adjust dimensions as needed

        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameText = new JTextField();

        JLabel rollLabel = new JLabel("Roll Number: ");
        JTextField rollText = new JTextField();

        JLabel qualificationLabel = new JLabel("Qualification");
        DefaultListModel<String> L1 = new DefaultListModel<>();
        L1.addElement("High School");
        L1.addElement("Graduate");
        L1.addElement("Post Graduate");
        JList<String> qualificationList = new JList<>(L1);
        qualificationList.setVisibleRowCount(3); // Set visible row count for JList

        JLabel subjectLabel = new JLabel("Subject: ");
        String[] subject = {"BCA", "BE", "BBA", "SE"};
        JComboBox<String> cb = new JComboBox<>(subject);

        JLabel peraddLabel = new JLabel("Permanent Address: ");
        JLabel stateLabel = new JLabel("State");
        JTextField stateText = new JTextField();
        JLabel districtLabel = new JLabel("District: ");
        JTextField districtText = new JTextField();
        JLabel wardnoLabel = new JLabel("Ward No: ");
        JTextField wardnoText = new JTextField();
        JLabel stAddLabel = new JLabel("Street Address: ");
        JTextField stAddText = new JTextField();

        JLabel tempaddLabel = new JLabel("Temporary Address: ");
        JLabel stateLabel2 = new JLabel("State");
        JTextField stateText2 = new JTextField();
        JLabel districtLabel2 = new JLabel("District: ");
        JTextField districtText2 = new JTextField();
        JLabel wardnoLabel2 = new JLabel("Ward No: ");
        JTextField wardnoText2 = new JTextField();
        JLabel stAddLabel2 = new JLabel("Street Address: ");
        JTextField stAddText2 = new JTextField();

        JButton submit = new JButton("Submit");
        JButton display = new JButton("Display");


        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(rollLabel);
        panel.add(rollText);
        panel.add(qualificationLabel);
        panel.add(new JScrollPane(qualificationList)); // Wrap JList in JScrollPane
        panel.add(subjectLabel);
        panel.add(cb);
        panel.add(peraddLabel);

        panel.add(stateLabel);
        panel.add(stateText);
        panel.add(districtLabel);
        panel.add(districtText);
        panel.add(wardnoLabel);
        panel.add(wardnoText);
        panel.add(stAddLabel);
        panel.add(stAddText);
        panel.add(tempaddLabel);

        panel.add(stateLabel2);
        panel.add(stateText2);
        panel.add(districtLabel2);
        panel.add(districtText2);
        panel.add(wardnoLabel2);
        panel.add(wardnoText2);
        panel.add(stAddLabel2);
        panel.add(stAddText2);
        panel.add(submit);
        panel.add(display);




        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String roll = rollText.getText();
                String qual = qualificationList.getSelectedValue();
                String subject = (String) cb.getSelectedItem();
                String perState = stateText.getText();
                String perdis = districtText.getText();
                String perward = wardnoText.getText();
                String stadd = stAddText.getText();
                String tempState = stateText.getText();
                String tempdis = districtText.getText();
                String tempward = wardnoText.getText();
                String tempstadd = stAddText.getText();


                if(Objects.equals(name,"") || Objects.equals(roll,"") || Objects.equals(qual,"") || Objects.equals(subject,"")|| Objects.equals(perState,"")
                || Objects.equals(perdis,"") || Objects.equals(perward,"")||Objects.equals(stadd,"")||Objects.equals(tempState,"")||Objects.equals(tempdis,"")
                ||Objects.equals(tempward,"")||Objects.equals(tempstadd,"")){
                    JOptionPane.showMessageDialog(panel, "Please Enter the empty fields");
                }

                final String url ="jdbc:mysql://localhost:3306/student";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection con = DriverManager.getConnection(url, "root", "Sourcecode#007");

                    String sql = "INSERT INTO StudentLogin (name, roll_number, qualification, subject, per_state, per_district, per_ward_no, per_street_address, temp_state, temp_district, temp_ward_no, temp_street_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, roll);
                    pstmt.setString(3, qual);
                    pstmt.setString(4, subject);
                    pstmt.setString(5, perState);
                    pstmt.setString(6, perdis);
                    pstmt.setString(7, perward);
                    pstmt.setString(8, stadd);
                    pstmt.setString(9, tempState);
                    pstmt.setString(10, tempdis);
                    pstmt.setString(11, tempward);
                    pstmt.setString(12, tempstadd);
                    pstmt.executeUpdate();
                    System.out.println("Data inserted successfully!");
                }catch (SQLException se){
                    se.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                JTable table = new JTable(model);

                // Add columns
                model.addColumn("Name");
                model.addColumn("Roll Number");
                model.addColumn("Qualification");
                model.addColumn("Subject");
                model.addColumn("Permanent State");
                model.addColumn("Permanent District");
                model.addColumn("Permanent Ward No");
                model.addColumn("Permanent Street Address");
                model.addColumn("Temporary State");
                model.addColumn("Temporary District");
                model.addColumn("Temporary Ward No");
                model.addColumn("Temporary Street Address");

                final String url ="jdbc:mysql://localhost:3306/student";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection con = DriverManager.getConnection(url, "root", "Sourcecode#007");

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT * FROM StudentLogin");

                        // Populate table with data
                        while (rs.next()) {
                            Object[] row = new Object[12];
                            row[0] = rs.getString("name");
                            row[1] = rs.getString("roll_number");
                            row[2] = rs.getString("qualification");
                            row[3] = rs.getString("subject");
                            row[4] = rs.getString("per_state");
                            row[5] = rs.getString("per_district");
                            row[6] = rs.getString("per_ward_no");
                            row[7] = rs.getString("per_street_address");
                            row[8] = rs.getString("temp_state");
                            row[9] = rs.getString("temp_district");
                            row[10] = rs.getString("temp_ward_no");
                            row[11] = rs.getString("temp_street_address");
                            model.addRow(row);
                        }
                    JScrollPane scrollPane = new JScrollPane(table);
                    frame.add(scrollPane, BorderLayout.SOUTH);
                }catch (SQLException se){
                    se.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // Wrap the panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);

        // Add the JScrollPane to the frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
