package Billing_System;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Billing_System.Addnewcustomer;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author Deshitha Hansajith
 */
public class home_admin extends javax.swing.JFrame {
    static String user;
    /**
     * Creates new form home_admin
     */
    public home_admin() {
        initComponents();
    }
    public home_admin(String fromuser){
        initComponents();
        user = fromuser;
        txt_banner.setText(fromuser);
        cmd_addnewuser.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        cmd_addnewuser = new javax.swing.JButton();
        cmd_addcustomer = new javax.swing.JButton();
        cmd_updatecustomer = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_banner = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        cmd_updateuser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Home");
        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmd_addnewuser.setBackground(new java.awt.Color(255, 0, 0));
        cmd_addnewuser.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        cmd_addnewuser.setText("Add New User");
        cmd_addnewuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_addnewuserActionPerformed(evt);
            }
        });
        jPanel2.add(cmd_addnewuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 195, 84));

        cmd_addcustomer.setBackground(new java.awt.Color(255, 0, 0));
        cmd_addcustomer.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        cmd_addcustomer.setText("Add New Customer");
        cmd_addcustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_addcustomerActionPerformed(evt);
            }
        });
        jPanel2.add(cmd_addcustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, 80));

        cmd_updatecustomer.setBackground(new java.awt.Color(255, 0, 0));
        cmd_updatecustomer.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        cmd_updatecustomer.setText("Update Customer");
        cmd_updatecustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_updatecustomerActionPerformed(evt);
            }
        });
        jPanel2.add(cmd_updatecustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(597, 159, 195, 80));

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        jButton1.setText("Make Bill");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 159, 195, 80));

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        jButton2.setText("Add New Product");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 187, 84));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Billing_System/FormatFactory047-house.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 397, -1, -1));

        jPanel1.setBackground(new java.awt.Color(143, 66, 244));

        txt_banner.setFont(new java.awt.Font("Trajan Pro 3", 3, 40)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Trajan Pro 3", 3, 40)); // NOI18N
        jLabel2.setText("Welcome  ");

        jButton3.setBackground(new java.awt.Color(0, 153, 0));
        jButton3.setFont(new java.awt.Font("Source Sans Pro Semibold", 1, 18)); // NOI18N
        jButton3.setText("Log Out");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_banner, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_banner, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, -1));

        jButton5.setBackground(new java.awt.Color(255, 0, 0));
        jButton5.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        jButton5.setText("Get Today Sale");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 190, 80));

        jButton4.setBackground(new java.awt.Color(255, 0, 0));
        jButton4.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        jButton4.setText("Settings");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, 190, 80));

        cmd_updateuser.setBackground(new java.awt.Color(255, 0, 0));
        cmd_updateuser.setFont(new java.awt.Font("Source Sans Pro Black", 1, 18)); // NOI18N
        cmd_updateuser.setText("Update User");
        cmd_updateuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_updateuserActionPerformed(evt);
            }
        });
        jPanel2.add(cmd_updateuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 190, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(170, 50, 889, 591);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        close();
        new makebill(user).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmd_addcustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_addcustomerActionPerformed
        Addnewcustomer s = new Addnewcustomer();
        s.setVisible(true);
    }//GEN-LAST:event_cmd_addcustomerActionPerformed

    private void cmd_updatecustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_updatecustomerActionPerformed
        updatecustome s = new updatecustome();
        s.setVisible(true);
    }//GEN-LAST:event_cmd_updatecustomerActionPerformed

    private void cmd_addnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_addnewuserActionPerformed
        addnewuser s = new addnewuser();
        s.setVisible(true);
    }//GEN-LAST:event_cmd_addnewuserActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        newproduct s = new newproduct();
        s.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int p = JOptionPane.showConfirmDialog(null,"Do You Really Want To Log Out?","Log Out",JOptionPane.YES_NO_OPTION);
        if(p == 0){
            close();
            login_frame s = new login_frame();
            s.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new mysale(user).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        settings s = new settings();
        s.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cmd_updateuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_updateuserActionPerformed
        updateuser s = new updateuser();
        s.setVisible(true);
    }//GEN-LAST:event_cmd_updateuserActionPerformed

    public void close(){
        WindowEvent windowclose = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowclose);
    }
    
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
            java.util.logging.Logger.getLogger(home_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd_addcustomer;
    private javax.swing.JButton cmd_addnewuser;
    private javax.swing.JButton cmd_updatecustomer;
    private javax.swing.JButton cmd_updateuser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel txt_banner;
    // End of variables declaration//GEN-END:variables
}
