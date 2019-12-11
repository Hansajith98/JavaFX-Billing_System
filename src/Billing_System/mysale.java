package Billing_System;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*; 
import java.awt.event.*;
import static java.lang.Thread.sleep;
import net.proteanit.sql.DbUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deshitha Hansajith
 */

public class mysale extends javax.swing.JFrame {
    Connection c = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    static String month ;
    static String year ;
    static String day ;
    static String second; 
    static String minutes; 
    static String hour;
    static String cashier  = "admin";
    static boolean useracces = false;
    

    /**
     * Creates new form mysale
     */
    public mysale() {
        initComponents();
        c = DB.connecrDB();
        currentdatetime();
        update_table();
        set_sale();
        fillcombo();
        set_cbox();
    }
    
    
    public mysale(String fromuser) {
        initComponents();
        c = DB.connecrDB();
        cashier = fromuser;
        currentdatetime();
        update_table();
        set_sale();
        fillcombo();
        set_cbox();
    }
    
    public void set_cbox(){
        try{
            String sql = "select acces_type from employee where username='"+cashier+"' ";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            String acces;
            if(rs.next()){
                acces = rs.getString("acces_type");
                if(acces.equals("user")){
                    useracces = false;
                    cbox_username.setVisible(false);
                }
            }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
        }
    }
    
    private void fillcombo(){
        
        try{
            String name= "admin";
            String sql = "select username from employee ";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                cbox_username.addItem(rs.getString("username"));
                //System.out.println(rs.getString("username"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
        } 

     }
    
    
    public void currentdatetime() {
        Thread clock;
        clock = new Thread() {
            public void run() {
                try {
                    
                    while (true) {
                        Calendar cal = new GregorianCalendar();
                        month = (1+cal.get(Calendar.MONTH))+"";
                        year = cal.get(Calendar.YEAR)+"";
                        day = cal.get(Calendar.DAY_OF_MONTH)+"";
                        second = cal.get(Calendar.SECOND)+"";
                        minutes = cal.get(Calendar.MINUTE)+"";
                        hour = cal.get(Calendar.HOUR)+"";
                        if(day.length()==1){
                            day ="0"+day;
                        }
                        if( month.length()==1){
                            month ="0"+month;
                        }
                        if(year.length()==1){
                            year="0" + year;
                        }
                        if(second.length()==1){
                            second ="0"+second;
                        }
                        if(hour.length()==1){
                            hour="0"+hour;
                        }
                        if(minutes.length()==1){
                            minutes="0"+minutes;
                        }
                        sleep(100);
                        txt_date.setText(year + "-" + (month) + "-" + day);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(login_frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        clock.start();
    }
    
    private void set_sale(){
        try{
            String date = year + "-" + (month) + "-" + day ;
            //System.out.println(year + "-" + (month) + "-" + day);
            java.sql.Date date1 = java.sql.Date.valueOf(date);
            //System.out.println(date1);
            if(useracces = false){
                String sql = "select sum(price) from bill where date = ?";
                pst = c.prepareStatement(sql);
                pst.setString(1,date.format(date));
            }else{
                String sql = "select sum(price) from bill where date = ? and cashier = ?";
                pst = c.prepareStatement(sql);
                pst.setString(1,date.format(date));
                pst.setString(2,cashier);
            }
            rs = pst.executeQuery();
            if(rs.next()){
                //System.out.println(rs.getString("sum(price)"));
                txt_totalsale.setText(rs.getString("sum(price)"));
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
        }
    }
    
    private void update_table(){
        try{
            Thread.sleep(1000);
            String date = year + "-" + (month) + "-" + day ;
            //System.out.println(year + "-" + (month) + "-" + day);
            java.sql.Date date1 = java.sql.Date.valueOf(date);
            
            String sql = "select idmakebill,receiptid,productid,quantity,price from bill where date = ? and cashier =?";
            pst = c.prepareStatement(sql);
            pst.setString(1,date.format(date));
            pst.setString(2,cashier);
            rs = pst.executeQuery();
            table_bill.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
        }
     }
    
    private void get_select(){
        
        try{
            int row = table_bill.getSelectedRow();
            String table_click = (table_bill.getModel().getValueAt(row, 0).toString());
            String sql = "select * from bill where idmakebill = '"+table_click+"' " ;
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                txt_quantity.setText(rs.getString("quantity"));
                txt_time.setText(rs.getString("time"));
                txt_total.setText(rs.getString("price"));
                String billid = rs.getString("receiptid");
                sql = "select * from product where barcode = ? " ;
                pst = c.prepareStatement(sql);
                pst.setString(1,rs.getString("productid"));
                rs = pst.executeQuery();
                if(rs.next()){
                    String add = rs.getString("product_name");
                    txt_productame.setText(add);
                }
                sql = "select sum(price) from bill where receiptid = ? " ;
                pst = c.prepareStatement(sql);
                pst.setString(1,billid);
                rs = pst.executeQuery();
                if(rs.next()){
                    String add = rs.getString("sum(price)");
                    txt_billtotal.setText(add);
                }
                sql = "select customerid from paymethod where receiptid = ? " ;
                pst = c.prepareStatement(sql);
                pst.setString(1,billid);
                rs = pst.executeQuery();
                if(rs.next()){
                    if(rs.getString("customerid").equals("0")){
                        txt_customername.setText("No Customer Information! ");
                    }
                    sql = "select * from customer where customerid = ? " ;
                    pst = c.prepareStatement(sql);
                    pst.setString(1,rs.getString("customerid"));
                    rs = pst.executeQuery();
                    if(rs.next()){
                        txt_customername.setText(rs.getString("firstname")+" "+rs.getString("lastname"));
                    }else{
                        
                    }
                    
                }
                
                
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            //e.printStackTrace();
        }
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
        jPanel4 = new javax.swing.JPanel();
        txt_getreport = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_totalsale = new javax.swing.JLabel();
        txt_date = new javax.swing.JLabel();
        cbox_username = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_bill = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txt_productame = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JLabel();
        txt_total = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_billtotal = new javax.swing.JLabel();
        txt_customername = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 178, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        txt_getreport.setBackground(new java.awt.Color(204, 0, 0));
        txt_getreport.setForeground(new java.awt.Color(255, 255, 255));
        txt_getreport.setText("Get Report");
        txt_getreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_getreportActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(1, 24, 97));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Sale  :");

        txt_totalsale.setForeground(new java.awt.Color(255, 255, 255));
        txt_totalsale.setText("0000");

        txt_date.setForeground(new java.awt.Color(255, 255, 255));

        cbox_username.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbox_usernamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_totalsale, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207)
                .addComponent(txt_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbox_username, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbox_username)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_totalsale)
                            .addComponent(txt_date))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );

        table_bill.setBackground(new java.awt.Color(102, 255, 102));
        table_bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_billMouseClicked(evt);
            }
        });
        table_bill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_billKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_bill);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        txt_productame.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_productame.setForeground(new java.awt.Color(255, 255, 255));
        txt_productame.setText("Item Name");

        txt_quantity.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_quantity.setForeground(new java.awt.Color(255, 255, 255));
        txt_quantity.setText("Quantity");

        txt_total.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_total.setForeground(new java.awt.Color(255, 255, 255));
        txt_total.setText("Total");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Search...");

        txt_billtotal.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_billtotal.setForeground(new java.awt.Color(255, 255, 255));
        txt_billtotal.setText("Bill Total");

        txt_customername.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_customername.setForeground(new java.awt.Color(255, 255, 255));
        txt_customername.setText("Customer Name");

        txt_time.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_time.setForeground(new java.awt.Color(255, 255, 255));
        txt_time.setText("Time");

        txt_search.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_productame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(201, 201, 201))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_billtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_customername)
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(168, 168, 168)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_productame, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_billtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_customername, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_getreport, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(6, 6, 6))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_getreport, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(250, 75, 883, 576);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_getreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_getreportActionPerformed
        //TODO add your handling code here:
    }//GEN-LAST:event_txt_getreportActionPerformed

    private void table_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_billMouseClicked
        get_select();
    }//GEN-LAST:event_table_billMouseClicked

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        try{
            String date = year + "-" + (month) + "-" + day ;
            //System.out.println(year + "-" + (month) + "-" + day);
            java.sql.Date date1 = java.sql.Date.valueOf(date);
            if(useracces = false){
                String sql = "select idmakebill,receiptid,productid,quantity,price from bill  where date = ? and receiptid = ?";
                pst = c.prepareStatement(sql);
                pst.setString(1,date.format(date));
                pst.setString(2,txt_search.getText());;
            }else{
                String sql = "select idmakebill,receiptid,productid,quantity,price from bill  where date = ? and cashier = ? and receiptid = ?";
                pst = c.prepareStatement(sql);
                pst.setString(1,date.format(date));
                pst.setString(2,cashier);
                pst.setString(3,txt_search.getText());
            }
            rs = pst.executeQuery();
            table_bill.setModel(DbUtils.resultSetToTableModel(rs));
            if(txt_search.getText().length()==0){
                update_table();
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            //e.printStackTrace();
        }
    }//GEN-LAST:event_txt_searchKeyReleased

    private void table_billKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_billKeyReleased
        if((evt.getKeyCode()==KeyEvent.VK_UP) || (evt.getKeyCode()==KeyEvent.VK_DOWN)){
            get_select();
        }
    }//GEN-LAST:event_table_billKeyReleased

    private void cbox_usernamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbox_usernamePopupMenuWillBecomeInvisible
        cashier = (String) cbox_username.getSelectedItem();
        update_table();
    }//GEN-LAST:event_cbox_usernamePopupMenuWillBecomeInvisible

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
            java.util.logging.Logger.getLogger(mysale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mysale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mysale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mysale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mysale().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbox_username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_bill;
    private javax.swing.JLabel txt_billtotal;
    private javax.swing.JLabel txt_customername;
    private javax.swing.JLabel txt_date;
    private javax.swing.JButton txt_getreport;
    private javax.swing.JLabel txt_productame;
    private javax.swing.JLabel txt_quantity;
    private javax.swing.JTextField txt_search;
    private javax.swing.JLabel txt_time;
    private javax.swing.JLabel txt_total;
    private javax.swing.JLabel txt_totalsale;
    // End of variables declaration//GEN-END:variables
}
