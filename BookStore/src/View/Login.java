
package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        txtEmailLogin.setFocusable(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEmailLogin = new javax.swing.JTextField();
        txtPassLogin = new javax.swing.JPasswordField();
        signIn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        backgroud = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Password:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("LOGIN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, -1, -1));

        txtEmailLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmailLogin.setForeground(new java.awt.Color(0, 0, 0));
        txtEmailLogin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmailLogin.setToolTipText("");
        txtEmailLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtEmailLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 250, 30));

        txtPassLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassLogin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPassLogin.setToolTipText("");
        txtPassLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtPassLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 250, 30));

        signIn.setBackground(new java.awt.Color(255, 255, 204));
        signIn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        signIn.setForeground(new java.awt.Color(0, 0, 0));
        signIn.setText("Sign In");
        signIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInActionPerformed(evt);
            }
        });
        getContentPane().add(signIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 350, -1));

        jLabel3.setForeground(new java.awt.Color(255, 153, 51));
        jLabel3.setText("Create an account");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 440, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Not registered? ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 440, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Email:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, -1, -1));

        backgroud.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backgroud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Beige Watercolor Book Day Facebook Post.png"))); // NOI18N
        backgroud.setMaximumSize(new java.awt.Dimension(600, 700));
        backgroud.setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().add(backgroud, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 788));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();
        Register rs = new Register();
        rs.setLocationRelativeTo(null);
        rs.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void signInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInActionPerformed
        String email = txtEmailLogin.getText().toString().trim();
        String password = txtPassLogin.getText().toString().trim();
        int check = 0;
        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Email or password cannot be blank", "Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
                String sql = "SELECT * FROM accounts WHERE email = '"+email+"' AND password = '"+password+"'";
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    check = 1;
                }
            if(check == 1) {
                JOptionPane.showMessageDialog(rootPane, "Logged in successfully", "Success" , JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                Home home = new Home();
                home.setLocationRelativeTo(null);
                home.setVisible(true);
                Login d = new Login();
                d.setVisible(false); 
                txtEmailLogin.setText("");
                txtPassLogin.setText("");
            }
            else {
                JOptionPane.showMessageDialog(rootPane, "Email or password is incorrect", "Failed" , JOptionPane.WARNING_MESSAGE);
                txtEmailLogin.setText("");
                txtPassLogin.setText("");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());    
        }
    }//GEN-LAST:event_signInActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login d = new Login();
                d.setLocationRelativeTo(null);
                d.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroud;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton signIn;
    private javax.swing.JTextField txtEmailLogin;
    private javax.swing.JPasswordField txtPassLogin;
    // End of variables declaration//GEN-END:variables
}
