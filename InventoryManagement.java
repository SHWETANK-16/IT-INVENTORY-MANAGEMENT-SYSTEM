import java.awt.*;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManagement extends JFrame {

    // DATABASE CONFIGURATION
    static final String URL
            = "jdbc:mysql://localhost:3306/it_inventory";

    static final String USER = "root";

    static final String PASSWORD = "user1";

    // TABS
    JTabbedPane tabs;

    JTable availableTable;
    JTable inUseTable;
    JTable repairTable;
    JTable warrantyTable;

    DefaultTableModel availableModel;
    DefaultTableModel inUseModel;
    DefaultTableModel repairModel;
    DefaultTableModel warrantyModel;
    JTable logTable;
    DefaultTableModel logModel;

    // ADD FORM
    JTextField txtName;
    JTextField txtType;
    JTextField txtSerial;

    JComboBox<String> cmbStatus;

    JTextField txtDept;
    JTextField txtUser;
    JTextField txtRepair;

    JTextField txtPurchaseDate;
    JTextField txtWarrantyDate;
    JTextField txtVendor;
    JTextField txtCost;
    JTextField txtRemarks;

    // SEARCH
    JTextField txtSearch;

    // UPDATE FORM
    JTextField txtUpdateId;

    JComboBox<String> cmbUpdateStatus;

    JTextField txtUpdateDept;
    JTextField txtUpdateUser;
    JTextField txtUpdateRepair;

    // BUTTONS
    JButton addBtn;
    JButton updateBtn;
    JButton deleteBtn;
    JButton refreshBtn;
    JButton searchBtn;
    JButton exportBtn;
    JButton chartBtn;

    // DASHBOARD LABELS
    JLabel lblTotal;
    JLabel lblAvailable;
    JLabel lblInUse;
    JLabel lblRepair;

           

    public InventoryManagement() {

        setTitle("IT Inventory Management System");

        setSize(1400, 800);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        Font appFont =
        new Font(
                "Segoe UI",
                Font.PLAIN,
                14);

      UIManager.put("Label.font", appFont);
      UIManager.put("Button.font", appFont);
      UIManager.put("TextField.font", appFont);
      UIManager.put("Table.font", appFont);

        // DASHBOARD
        
        // DASHBOARD

        new JLabel(
    "IT INVENTORY MANAGEMENT SYSTEM",
    SwingConstants.CENTER);

  new Font(
    "Segoe UI",
    Font.BOLD,
    26);
 JLabel title =
        new JLabel(
                "IT INVENTORY MANAGEMENT SYSTEM",
                SwingConstants.CENTER);

 title.setFont(
        new Font(
                "Segoe UI",
                Font.BOLD,
                26));

 title.setForeground(
        new Color(44,62,80));

 JPanel dashboard =
        new JPanel(
                new GridLayout(1,4));

        lblTotal =
        createDashboardCard(
                "TOTAL",
                Color.BLUE);

     lblAvailable =
        createDashboardCard(
                "AVAILABLE",
                new Color(46,204,113));

    lblInUse =
        createDashboardCard(
                "IN USE",
                new Color(241,196,15));

   lblRepair =
        createDashboardCard(
                "REPAIR",
                new Color(231,76,60));
        dashboard.add(lblTotal);
        dashboard.add(lblAvailable);
        dashboard.add(lblInUse);
        dashboard.add(lblRepair);

        add(dashboard, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel
                = new JPanel(new BorderLayout());

        tabs = new JTabbedPane();
        
        tabs.setFont(
        new Font(
                "Segoe UI",
                Font.BOLD,
                14));

        availableModel = createModel();
        inUseModel = createModel();
        repairModel = createModel();
        warrantyModel =
        new DefaultTableModel(
                new String[]{
                        "ID",
                        "Component",
                        "Type",
                        "Warranty Expiry",
                        "Vendor"
                },
                0);
        
        logModel =
        new DefaultTableModel(
                new String[]{
                        "Log ID",
                        "Inventory ID",
                        "Action",
                        "Description",
                        "Time"
                },
                0);
        availableTable
                = new JTable(availableModel);
        
        
        availableTable.getTableHeader()
        .setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

 availableTable.getTableHeader()
        .setBackground(
                new Color(
                        52,73,94));

 availableTable.getTableHeader()
        .setForeground(
                Color.BLUE);        
        inUseTable
                = new JTable(inUseModel);
        
        inUseTable.getTableHeader()
        .setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

 inUseTable.getTableHeader()
        .setBackground(
                new Color(
                        52,73,94));

 inUseTable.getTableHeader()
        .setForeground(
                Color.BLUE);        
        repairTable
                = new JTable(repairModel);
        
        repairTable.getTableHeader()
        .setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14));

 repairTable.getTableHeader()
        .setBackground(
                new Color(
                        52,73,94));

 repairTable.getTableHeader()
        .setForeground(
                Color.BLUE);        
        
   logTable =
        new JTable(logModel);             
        availableTable.setRowHeight(30);
        inUseTable.setRowHeight(30);
        repairTable.setRowHeight(30);

       availableTable.setGridColor(
              Color.LIGHT_GRAY);

        inUseTable.setGridColor(
           Color.LIGHT_GRAY);

        repairTable.setGridColor(
            Color.LIGHT_GRAY);        

        availableTable.setBackground(
                new Color(220, 255, 220));

        inUseTable.setBackground(
                new Color(255, 255, 210));

        repairTable.setBackground(
                new Color(255, 220, 220));

        tabs.add(
                "Available Components",
                new JScrollPane(availableTable));

        tabs.add(
                "Components In Use",
                new JScrollPane(inUseTable));

        tabs.add(
                "Components Under Repair",
                new JScrollPane(repairTable));
        
        tabs.add(
        "Activity Logs",
        new JScrollPane(
                logTable));        
        centerPanel.add(
                tabs,
                BorderLayout.CENTER);

        add(centerPanel,
                BorderLayout.CENTER);

        warrantyTable =
        new JTable(warrantyModel);
        tabs.add(
        "Warranty Alerts",
        new JScrollPane(warrantyTable));        
        // LEFT FORM PANEL
        JPanel leftPanel
                = new JPanel();

        leftPanel.setLayout(
                new GridLayout(18, 2, 5, 5));

        txtName = new JTextField();
        txtType = new JTextField();
        txtSerial = new JTextField();

        cmbStatus
                = new JComboBox<>(
                        new String[]{
                            "AVAILABLE",
                            "IN_USE",
                            "REPAIR"
                        });

        txtDept = new JTextField();
        txtUser = new JTextField();
        txtRepair = new JTextField();

        txtPurchaseDate
                = new JTextField();

        txtWarrantyDate
                = new JTextField();

        txtVendor
                = new JTextField();

        txtCost
                = new JTextField();

        txtRemarks
                = new JTextField();

        leftPanel.add(
                new JLabel("Component Name"));
        leftPanel.add(txtName);

        leftPanel.add(
                new JLabel("Component Type"));
        leftPanel.add(txtType);

        leftPanel.add(
                new JLabel("Serial Number"));
        leftPanel.add(txtSerial);

        leftPanel.add(
                new JLabel("Status"));
        leftPanel.add(cmbStatus);

        leftPanel.add(
                new JLabel("Department"));
        leftPanel.add(txtDept);

        leftPanel.add(
                new JLabel("Assigned User"));
        leftPanel.add(txtUser);

        leftPanel.add(
                new JLabel("Repair Part"));
        leftPanel.add(txtRepair);

        leftPanel.add(
                new JLabel("Purchase Date"));
        leftPanel.add(txtPurchaseDate);

        leftPanel.add(
                new JLabel("Warranty Expiry"));
        leftPanel.add(txtWarrantyDate);

        leftPanel.add(
                new JLabel("Vendor"));
        leftPanel.add(txtVendor);

        leftPanel.add(
                new JLabel("Cost"));
        leftPanel.add(txtCost);

        leftPanel.add(
                new JLabel("Remarks"));
        leftPanel.add(txtRemarks);



        addBtn =
        createButton(
                "Add",
                new Color(52,152,219));

       exportBtn =
        createButton(
                "Export CSV",
                new Color(52,73,94));
        
        chartBtn =
        createButton(
                "Statistics",
                new Color(41,128,185));        

        refreshBtn =
        createButton(
                "Refresh",
                new Color(46,204,113));


        leftPanel.add(addBtn);
        leftPanel.add(exportBtn);
        leftPanel.add(chartBtn);
        leftPanel.add(refreshBtn);
        add(leftPanel,
        BorderLayout.WEST);

        // RIGHT PANEL
        JPanel rightPanel
                = new JPanel();

        rightPanel.setLayout(
                new GridLayout(12, 2, 5, 5));

        txtSearch
                = new JTextField();

        searchBtn =
        createButton(
                "Search",
                new Color(155,89,182));


        txtUpdateId
                = new JTextField();

        cmbUpdateStatus
                = new JComboBox<>(
                        new String[]{
                            "AVAILABLE",
                            "IN_USE",
                            "REPAIR"
                        });

        txtUpdateDept
                = new JTextField();

        txtUpdateUser
                = new JTextField();

        txtUpdateRepair
                = new JTextField();

        updateBtn =
        createButton(
                "Update",
                new Color(52,152,219));

        deleteBtn =
        createButton(
                "Delete",
                new Color(231,76,60));

        
        
                
             

        rightPanel.add(
                new JLabel("Search"));

        rightPanel.add(txtSearch);

        rightPanel.add(
                new JLabel(""));

        rightPanel.add(searchBtn);

        rightPanel.add(
                new JLabel("Component ID"));

        rightPanel.add(txtUpdateId);

        rightPanel.add(
                new JLabel("New Status"));

        rightPanel.add(cmbUpdateStatus);

        rightPanel.add(
                new JLabel("Department"));

        rightPanel.add(txtUpdateDept);

        rightPanel.add(
                new JLabel("Assigned User"));

        rightPanel.add(txtUpdateUser);

        rightPanel.add(
                new JLabel("Repair Part"));

        rightPanel.add(txtUpdateRepair);

        rightPanel.add(updateBtn);
        rightPanel.add(deleteBtn);

        add(rightPanel,
            BorderLayout.EAST);
        
        availableTable.getSelectionModel()
        .addListSelectionListener(e ->
{
    int row =
            availableTable.getSelectedRow();

    if(row >= 0)
    {
        txtUpdateId.setText(
                availableModel
                        .getValueAt(row,0)
                        .toString());

        cmbUpdateStatus.setSelectedItem(
                availableModel
                        .getValueAt(row,4)
                        .toString());

        txtUpdateDept.setText(
                String.valueOf(
                        availableModel
                                .getValueAt(row,5)));

        txtUpdateUser.setText(
                String.valueOf(
                        availableModel
                                .getValueAt(row,6)));

        txtUpdateRepair.setText(
                String.valueOf(
                        availableModel
                                .getValueAt(row,7)));
    }
});    
    addBtn.addActionListener(
            e -> addComponent());

     exportBtn.addActionListener(
         e -> exportCSV());  
        
     chartBtn.addActionListener(
         e -> showStatistics());   

    refreshBtn.addActionListener(
            e -> loadData());

    searchBtn.addActionListener(
            e -> searchComponent());

    updateBtn.addActionListener(
            e -> updateComponent());

    deleteBtn.addActionListener(
            e -> deleteComponent());



    loadData();
    loadLogs();
    loadWarrantyAlerts();

    setVisible(true);

    }   // <-- END OF CONSTRUCTOR

    void loadData()
    {
        availableModel.setRowCount(0);
        inUseModel.setRowCount(0);
        repairModel.setRowCount(0);

        try
        {
            Connection con =
                    getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM inventory");

            while(rs.next())
            {
                Object[] row =
                {
                    rs.getInt("id"),
                    rs.getString("component_name"),
                    rs.getString("component_type"),
                    rs.getString("serial_no"),
                    rs.getString("status"),
                    rs.getString("department"),
                    rs.getString("assigned_user"),
                    rs.getString("repair_part_required"),
                    rs.getDate("purchase_date"),
                    rs.getDate("warranty_expiry"),
                    rs.getString("vendor_name"),
                    rs.getDouble("cost"),
                    rs.getString("remarks")
                };

                String status =
                        rs.getString("status");

                if(status.equals("AVAILABLE"))
                    availableModel.addRow(row);

                else if(status.equals("IN_USE"))
                    inUseModel.addRow(row);

                else
                    repairModel.addRow(row);
            }

            con.close();

            updateDashboard();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }   
    
 void searchComponent()
 {
    availableModel.setRowCount(0);
    inUseModel.setRowCount(0);
    repairModel.setRowCount(0);

    try
    {
        Connection con =
                getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "SELECT * FROM inventory " +
                        "WHERE component_name LIKE ? " +
                        "OR serial_no LIKE ?");

        String keyword =
                "%" +
                txtSearch.getText() +
                "%";

        ps.setString(1, keyword);
        ps.setString(2, keyword);

        ResultSet rs =
                ps.executeQuery();

        while(rs.next())
        {
            Object[] row =
            {
                rs.getInt("id"),
                rs.getString("component_name"),
                rs.getString("component_type"),
                rs.getString("serial_no"),
                rs.getString("status"),
                rs.getString("department"),
                rs.getString("assigned_user"),
                rs.getString("repair_part_required"),
                rs.getDate("purchase_date"),
                rs.getDate("warranty_expiry"),
                rs.getString("vendor_name"),
                rs.getDouble("cost"),
                rs.getString("remarks")
            };

            String status =
                    rs.getString("status");

            if(status.equals("AVAILABLE"))
                availableModel.addRow(row);

            else if(status.equals("IN_USE"))
                inUseModel.addRow(row);

            else
                repairModel.addRow(row);
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

   void updateComponent()
{
    try
    {
        Connection con =
                getConnection();

        String sql =
                "UPDATE inventory " +
                "SET status=?,department=?,assigned_user=?,repair_part_required=? " +
                "WHERE id=?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(
                1,
                cmbUpdateStatus
                        .getSelectedItem()
                        .toString());

        ps.setString(
                2,
                txtUpdateDept.getText());

        ps.setString(
                3,
                txtUpdateUser.getText());

        ps.setString(
                4,
                txtUpdateRepair.getText());

        ps.setInt(
                5,
                Integer.parseInt(
                        txtUpdateId.getText()));

        ps.executeUpdate();

        addLog(
                Integer.parseInt(
                        txtUpdateId.getText()),
                "UPDATE",
                "Component Updated");

        JOptionPane.showMessageDialog(
                this,
                "Updated Successfully");

        con.close();

        loadData();
        loadWarrantyAlerts();
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage());
    }
     loadLogs();
 }
  void deleteComponent()
{
    try
    {
        int id =
                Integer.parseInt(
                        txtUpdateId.getText());

        Connection con =
                getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "DELETE FROM inventory WHERE id=?");

        ps.setInt(1, id);

        ps.executeUpdate();

        addLog(
                id,
                "DELETE",
                "Component Deleted");

        JOptionPane.showMessageDialog(
                this,
                "Deleted Successfully");

        con.close();

        loadData();
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage());
    }
 }

 

    DefaultTableModel createModel() {

        return new DefaultTableModel(
                new String[]{
                    "ID",
                    "Component",
                    "Type",
                    "Serial No",
                    "Status",
                    "Department",
                    "Assigned User",
                    "Repair Part",
                    "Purchase Date",
                    "Warranty Expiry",
                    "Vendor",
                    "Cost",
                    "Remarks"
                }, 0);
    }

    Connection getConnection() throws Exception {
        Class.forName(
                "com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }

    void updateDashboard() {
        try {
            Connection con
                    = getConnection();

            Statement st
                    = con.createStatement();

            ResultSet rs
                    = st.executeQuery(
                            "SELECT status,COUNT(*) total "
                            + "FROM inventory "
                            + "GROUP BY status");

            int available = 0;
            int inUse = 0;
            int repair = 0;

            while (rs.next()) {
                String status
                        = rs.getString("status");

                int total
                        = rs.getInt("total");

                if (status.equals("AVAILABLE")) {
                    available = total; 
                }else if (status.equals("IN_USE")) {
                    inUse = total; 
                }else if (status.equals("REPAIR")) {
                    repair = total;
                }
            }

            lblAvailable.setText(
                    "Available : " + available);

            lblInUse.setText(
                    "In Use : " + inUse);

            lblRepair.setText(
                    "Repair : " + repair);

            lblTotal.setText(
                    "Total : "
                    + (available + inUse + repair));

            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    void clearFields() {
        txtName.setText("");
        txtType.setText("");
        txtSerial.setText("");
        txtDept.setText("");
        txtUser.setText("");
        txtRepair.setText("");
        txtPurchaseDate.setText("");
        txtWarrantyDate.setText("");
        txtVendor.setText("");
        txtCost.setText("");
        txtRemarks.setText("");
    }

    void addLog(int inventoryId,
            String actionType,
            String description) {
        try {
            Connection con
                    = getConnection();

            PreparedStatement ps
                    = con.prepareStatement(
                            "INSERT INTO inventory_logs "
                            + "(inventory_id,action_type,action_description) "
                            + "VALUES(?,?,?)");

            ps.setInt(1, inventoryId);
            ps.setString(2, actionType);
            ps.setString(3, description);

            ps.executeUpdate();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addComponent() {
        try {
            Connection con
                    = getConnection();

            String sql
                    = "INSERT INTO inventory("
                    + "component_name,component_type,"
                    + "serial_no,status,department,"
                    + "assigned_user,repair_part_required,"
                    + "purchase_date,warranty_expiry,"
                    + "vendor_name,cost,remarks)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps
                    = con.prepareStatement(sql);

            ps.setString(1, txtName.getText());
            ps.setString(2, txtType.getText());
            ps.setString(3, txtSerial.getText());
            ps.setString(4,
                    cmbStatus.getSelectedItem().toString());

            ps.setString(5, txtDept.getText());
            ps.setString(6, txtUser.getText());
            ps.setString(7, txtRepair.getText());

            if (txtPurchaseDate.getText().isEmpty()) {
                ps.setNull(8, Types.DATE); 
            }else {
                ps.setDate(8,
                        java.sql.Date.valueOf(
                                txtPurchaseDate.getText()));
            }

            if (txtWarrantyDate.getText().isEmpty()) {
                ps.setNull(9, Types.DATE); 
            }else {
                ps.setDate(9,
                        java.sql.Date.valueOf(
                                txtWarrantyDate.getText()));
            }

            ps.setString(10,
                    txtVendor.getText());

            if (txtCost.getText().isEmpty()) {
                ps.setNull(11,
                        Types.DECIMAL); 
            }else {
                ps.setDouble(11,
                        Double.parseDouble(
                                txtCost.getText()));
            }

            ps.setString(12,
                    txtRemarks.getText());

            ps.executeUpdate();

            addLog(
                    0,
                    "ADD",
                    "Added "
                    + txtName.getText());

            JOptionPane.showMessageDialog(
                    this,
                    "Component Added");

            con.close();

            clearFields();

            loadData();
            loadWarrantyAlerts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    JLabel createDashboardCard(
        String text,
        Color color)
{
    JLabel lbl =
            new JLabel(
                    text,
                    SwingConstants.CENTER);

    lbl.setOpaque(true);
    lbl.setBackground(color);
    lbl.setForeground(Color.WHITE);

    lbl.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    18));

    return lbl;
 }

 
 JButton createButton(
        String text,
        Color color)
{
    JButton btn =
            new JButton(text);

    btn.setBackground(color);
    btn.setForeground(Color.pink);

    btn.setFocusPainted(false);

    btn.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14));

    return btn;
 }

 void loadLogs()
{
    logModel.setRowCount(0);

    try
    {
        Connection con =
                getConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT * FROM inventory_logs " +
                        "ORDER BY action_time DESC");

        while(rs.next())
        {
            Object[] row =
            {
                rs.getInt("log_id"),
                rs.getInt("inventory_id"),
                rs.getString("action_type"),
                rs.getString("action_description"),
                rs.getTimestamp("action_time")
            };

            logModel.addRow(row);
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

 void loadWarrantyAlerts()
{
    warrantyModel.setRowCount(0);

    try
    {
        Connection con =
                getConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT * FROM inventory " +
                        "WHERE warranty_expiry IS NOT NULL " +
                        "AND warranty_expiry <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)");

        while(rs.next())
        {
            Object[] row =
            {
                rs.getInt("id"),
                rs.getString("component_name"),
                rs.getString("component_type"),
                rs.getDate("warranty_expiry"),
                rs.getString("vendor_name")
            };

            warrantyModel.addRow(row);
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
 
  void exportCSV()
 {
    try
    {
        JFileChooser chooser =
                new JFileChooser();

        chooser.setSelectedFile(
                new java.io.File(
                        "inventory_report.csv"));

        int result =
                chooser.showSaveDialog(this);

        if(result != JFileChooser.APPROVE_OPTION)
        {
            return;
        }

        FileWriter writer =
                new FileWriter(
                        chooser.getSelectedFile());

        writer.write(
                "ID,Component,Type,Serial,Status\n");

        Connection con =
                getConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT * FROM inventory");

        while(rs.next())
        {
            writer.write(
                    rs.getInt("id") + "," +
                    rs.getString("component_name") + "," +
                    rs.getString("component_type") + "," +
                    rs.getString("serial_no") + "," +
                    rs.getString("status") +
                    "\n");
        }

        writer.close();
        con.close();

        JOptionPane.showMessageDialog(
                this,
                "CSV Export Successful");
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(
                this,
                ex.getMessage());
    }
 }

 void showStatistics()
{
    try
    {
        Connection con =
                getConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT status, COUNT(*) total " +
                        "FROM inventory " +
                        "GROUP BY status");

        StringBuilder stats =
                new StringBuilder();

        while(rs.next())
        {
            stats.append(
                    rs.getString("status"))
                 .append(" : ")
                 .append(
                    rs.getInt("total"))
                 .append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                stats.toString(),
                "Inventory Statistics",
                JOptionPane.INFORMATION_MESSAGE);

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
    try
    {
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    SwingUtilities.invokeLater(() ->
    {
        new InventoryManagement();
    });
}
}
