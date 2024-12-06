import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;

public class UserForm extends JFrame {

    private JTextField nameField, mobileField, addressField;
    private JRadioButton maleButton, femaleButton;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    private JCheckBox termsCheckBox;
    private JButton submitButton, resetButton;

    public UserForm() {
        setTitle("User Information Form");
        setLayout(new GridLayout(9, 2));  

      
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

   
        JLabel mobileLabel = new JLabel("Mobile:");
        mobileField = new JTextField();

        
        JLabel genderLabel = new JLabel("Gender:");
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        
        JLabel dobLabel = new JLabel("DOB:");

        
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }

        
        monthComboBox = new JComboBox<>();
        monthComboBox.addItem("JAN");
        monthComboBox.addItem("FEB");
        monthComboBox.addItem("MAR");
        monthComboBox.addItem("APR");
        monthComboBox.addItem("MAY");
        monthComboBox.addItem("JUN");
        monthComboBox.addItem("JUL");
        monthComboBox.addItem("AUG");
        monthComboBox.addItem("SEP");
        monthComboBox.addItem("OCT");
        monthComboBox.addItem("NOV");
        monthComboBox.addItem("DEC");

        
        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= currentYear; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        
        JLabel termsLabel = new JLabel("Accept Terms:");
        termsCheckBox = new JCheckBox();

        
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");

        
        add(nameLabel);
        add(nameField);
        add(mobileLabel);
        add(mobileField);
        add(genderLabel);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        add(genderPanel);

        
        JPanel dobPanel = new JPanel();
        dobPanel.add(dayComboBox);
        dobPanel.add(monthComboBox);
        dobPanel.add(yearComboBox);
        add(dobLabel);
        add(dobPanel);

        add(addressLabel);
        add(addressField);
        add(termsLabel);
        add(termsCheckBox);
        add(submitButton);
        add(resetButton);

        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to handle form submission
    private void submitForm() {
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String gender = maleButton.isSelected() ? "Male" : "Female";
        String day = dayComboBox.getSelectedItem().toString();
        String month = monthComboBox.getSelectedItem().toString();
        String year = yearComboBox.getSelectedItem().toString();
        String address = addressField.getText();
        boolean acceptedTerms = termsCheckBox.isSelected();

        // Combine the selected day, month, and year into a proper DOB format (YYYY-MM-DD)
        String dob = year + "-" + mapMonthToNumber(month) + "-" + day;

        // Insert data into the database
        insertIntoDatabase(name, mobile, gender, dob, address, acceptedTerms);
    }

    // Method to map month name to its respective number
    private String mapMonthToNumber(String month) {
        switch (month) {
            case "JAN": return "01";
            case "FEB": return "02";
            case "MAR": return "03";
            case "APR": return "04";
            case "MAY": return "05";
            case "JUN": return "06";
            case "JUL": return "07";
            case "AUG": return "08";
            case "SEP": return "09";
            case "OCT": return "10";
            case "NOV": return "11";
            case "DEC": return "12";
            default: return "01";
        }
    }

    // Method to reset the form
    private void resetForm() {
        nameField.setText("");
        mobileField.setText("");
        maleButton.setSelected(true);
        dayComboBox.setSelectedIndex(0);
        monthComboBox.setSelectedIndex(0);
        yearComboBox.setSelectedIndex(0);
        addressField.setText("");
        termsCheckBox.setSelected(false);
    }

    // Method to insert data into the database
    private void insertIntoDatabase(String name, String mobile, String gender, String dob, String address, boolean acceptedTerms) {
        // JDBC URL for MySQL
        String url = "jdbc:mysql://localhost:3306/registration";
        String username = "root";
        String password = "";

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(url, username, password);

            // SQL Query to insert data
            String query = "INSERT INTO `registration form` (NAME, MOBILE, DOB, ADRESS, GENDER) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

         
            stmt.setString(1, name);
            stmt.setString(2, mobile);
            stmt.setString(3, dob); 
            stmt.setString(4, address);
            stmt.setString(5, gender);

            
            stmt.executeUpdate();

           
            stmt.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "Data submitted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UserForm();
    }
}
