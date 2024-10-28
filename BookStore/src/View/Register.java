
package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Register extends javax.swing.JFrame {

    public Register() {
        initComponents();
        txtEmailSignUp.setFocusable(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEmailSignUp = new javax.swing.JTextField();
        txtPassSignUp = new javax.swing.JPasswordField();
        SignUp = new javax.swing.JButton();
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
        jLabel2.setText("SIGN UP");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, -1, -1));

        txtEmailSignUp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmailSignUp.setForeground(new java.awt.Color(0, 0, 0));
        txtEmailSignUp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmailSignUp.setToolTipText("");
        txtEmailSignUp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtEmailSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 250, 30));

        txtPassSignUp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassSignUp.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPassSignUp.setToolTipText("");
        txtPassSignUp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtPassSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 250, 30));

        SignUp.setBackground(new java.awt.Color(255, 255, 204));
        SignUp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        SignUp.setForeground(new java.awt.Color(0, 0, 0));
        SignUp.setText("Sign up");
        SignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpActionPerformed(evt);
            }
        });
        getContentPane().add(SignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 350, -1));

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

    private void SignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpActionPerformed

        int check = 0;
        String email = txtEmailSignUp.getText().toString().trim();
        String password = txtPassSignUp.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Email or password cannot be blank", "Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Connection con = DriverManager.getConnection(CommonDb.URL, CommonDb.USERNAME, CommonDb.PASSWORD);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM accounts WHERE email = '"+email+"'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                check = 1;
            }
            if(check == 0) {
                String insert = "INSERT INTO accounts (email, password) VALUES ('"+email+"', '"+password+"')";
                st.executeUpdate(insert);
                JOptionPane.showMessageDialog(rootPane, "Account successfully created", "Success" , JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                Login d = new Login();
                d.setLocationRelativeTo(null);
                d.setVisible(true); 
                txtEmailSignUp.setText("");
                txtPassSignUp.setText("");
            }
            else {
                JOptionPane.showMessageDialog(rootPane, "Account already exists", "Failed" , JOptionPane.WARNING_MESSAGE);
                txtEmailSignUp.setText("");
                txtPassSignUp.setText("");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());    
        }
    }//GEN-LAST:event_SignUpActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Register d = new Register();
                d.setLocationRelativeTo(null);
                d.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SignUp;
    private javax.swing.JLabel backgroud;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtEmailSignUp;
    private javax.swing.JPasswordField txtPassSignUp;
    // End of variables declaration//GEN-END:variables
}
