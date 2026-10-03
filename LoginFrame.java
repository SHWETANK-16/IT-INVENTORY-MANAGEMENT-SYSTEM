import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LoginFrame extends JFrame
{
    JTextField txtUser;
    JPasswordField txtPass;

    static final String URL =
            "jdbc:mysql://localhost:3306/it_inventory";

    static final String USER = "root";
    static final String PASSWORD = "user1";

    public LoginFrame()
    {
        setTitle("Login");

        setSize(350,200);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Username"));

        txtUser =
                new JTextField();

        add(txtUser);

        add(new JLabel("Password"));

        txtPass =
                new JPasswordField();

        add(txtPass);

        JButton loginBtn =
                new JButton("Login");

        add(loginBtn);

        loginBtn.addActionListener(
                e -> login());

        setVisible(true);
    }

    Connection getConnection()
            throws Exception
    {
        Class.forName(
                "com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }

    void login()
{
    try
    {
        Connection con =
                getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "SELECT * FROM users " +
                        "WHERE username=? " +
                        "AND password=?");

        ps.setString(
                1,
                txtUser.getText());

        ps.setString(
                2,
                new String(
                        txtPass.getPassword()));

        ResultSet rs =
                ps.executeQuery();

        if(rs.next())
        {
            String role =
                    rs.getString("role");

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful\nRole: "
                    + role);

            dispose();

            new InventoryManagement();
        }
        else
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password");
        }

        con.close();
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage());
    }
}

    public static void main(String[] args)
    {
        new LoginFrame();
    }
}