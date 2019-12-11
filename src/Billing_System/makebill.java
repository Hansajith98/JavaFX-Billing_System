package Billing_System;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import sun.security.util.Length;
/**
 *
 * @author Deshitha Hansajith
 */
public class makebill extends javax.swing.JFrame {
    static String table_click;
    static String cashier;
    static int billnumber;
    static int itemnumber = 0;
    Connection c = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    static String month ;
    static String year ;
    static String day ;
    static String second; 
    static String minutes; 
    static String hour;
    ArrayList name = new ArrayList();

    /**
     * Creates new form makebill
     */
    public makebill() {
        initComponents();
        c = DB.connecrDB();
        set_bill_table();
        set_pay_method();
        update_table();
        currentdatetime();
        setautocomplete();
        set_bill_number();
        cashier = "admin";
        setExtendedState(MAXIMIZED_BOTH);
        
    }
    
    public makebill(String fromuser) {
        initComponents();
        c = DB.connecrDB();
        set_bill_table();
        set_pay_method();
        update_table();
        set_bill_number();
        currentdatetime();
        cashier = fromuser;
        setautocomplete();
        txt_user.setText("Cashier Name  : "+cashier + "  ");
        setExtendedState(MAXIMIZED_BOTH);
        
    }
    public void currentdatetime() {
        Thread clock = new Thread() {
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
                        txt_datetime.setText("Date : " + year + "/" + (month) + "/" + day + "  Time : " + hour + ":" + minutes + ":" + second + "  ");
                        sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(login_frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        clock.start();
    }
    
    public void setautocomplete(){
        try{
            String sql = "select * from product" ;
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
//                System.out.println(rs.getString("product_name"));
                name.add(rs.getString("product_name"));
            }
        }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e);
                 e.printStackTrace();
            }
        
        
    }
    
   
    
    public void autocomplete(String txt){
//        System.out.println(txt);
        String complete ="";
        int start = txt.length();
        int last = txt.length();
        for(int i = 0;i<name.size();i++){
            if(name.get(i).toString().startsWith(txt)){
                complete = name.get(i).toString();
//                System.out.println(complete);
                last = complete.length();
                break;
            }
        }
        if(last>start){
            txt_productname.setText(complete);
            txt_productname.setCaretPosition(last);
            txt_productname.moveCaretPosition(start);
        }
    }
    
    public void close(){
        WindowEvent windowclose = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowclose);
    }
    
    private void set_bill_table(){
        try{
            Statement statement = c.createStatement();
            statement.executeUpdate("DELETE FROM currentbill");
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
        }
    }
    
    private void get_select(){
        try{
            int row = table_bill.getSelectedRow();
            table_click = (table_bill.getModel().getValueAt(row, 0).toString());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             ////e.printStackTrace();
        }
    }
    
    private void set_pay_method(){
        try{
            Statement statement = c.createStatement();
            statement.executeUpdate("DELETE FROM paymethod");
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
        }
    }
    
    private void update_table(){
        try{
            String sql = " select id as 'Number' , name as 'Product Name' , price as 'Price', quantity as 'Quantity', total as 'Total'  from currentbill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            table_bill.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
     }
    private void get_sum(){
        try{
            String sql = "select sum(total) from currentbill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txt_subtotal.setText(rs.getString("sum(total)"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
        }
        
    }

     
        private void set_bill_number(){
        try{
            String sql = "select max(receiptid) from bill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                billnumber = Integer.valueOf(rs.getString("max(receiptid)")) + 1;
                txt_billnumber.setText("  Bill Number  : "+billnumber + "  ");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jToolBar2 = new javax.swing.JToolBar();
        txt_user = new javax.swing.JLabel();
        txt_billnumber = new javax.swing.JLabel();
        txt_datetime = new javax.swing.JLabel();
        cmd_payloan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cmd_pay = new javax.swing.JButton();
        txt_subtotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_bill = new javax.swing.JTable();
        cmd_delete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_productid = new javax.swing.JTextField();
        txt_productname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_productprice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_itemcount = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jToolBar2.setBackground(new java.awt.Color(0, 0, 0));
        jToolBar2.setRollover(true);

        txt_user.setFont(new java.awt.Font("Castellar", 1, 12)); // NOI18N
        jToolBar2.add(txt_user);

        txt_billnumber.setFont(new java.awt.Font("Castellar", 1, 12)); // NOI18N
        jToolBar2.add(txt_billnumber);

        txt_datetime.setFont(new java.awt.Font("Castellar", 1, 12)); // NOI18N
        txt_datetime.setText("    ");
        jToolBar2.add(txt_datetime);

        cmd_payloan.setBackground(new java.awt.Color(0, 0, 0));
        cmd_payloan.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        cmd_payloan.setForeground(new java.awt.Color(255, 0, 0));
        cmd_payloan.setText("Pay Loan");
        cmd_payloan.setFocusable(false);
        cmd_payloan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmd_payloan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmd_payloan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_payloanActionPerformed(evt);
            }
        });
        jToolBar2.add(cmd_payloan);

        jButton1.setFont(new java.awt.Font("Engravers MT", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("Log Out");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton1);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmd_pay.setFont(new java.awt.Font("Bookman Old Style", 1, 24)); // NOI18N
        cmd_pay.setText("Pay");
        cmd_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_payActionPerformed(evt);
            }
        });
        jPanel3.add(cmd_pay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 550, 170, 70));

        txt_subtotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_subtotal.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 510, 130, 35));

        jPanel2.setBackground(new java.awt.Color(51, 0, 51));

        table_bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "name", "price", "quantity", "total"
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 133, 1350, 310));

        cmd_delete.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        cmd_delete.setText("Delete");
        cmd_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_deleteActionPerformed(evt);
            }
        });
        jPanel3.add(cmd_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, -1));

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total   :");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 510, 104, 35));

        jPanel1.setBackground(new java.awt.Color(0, 0, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bar Code");

        txt_productid.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        txt_productid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_productidActionPerformed(evt);
            }
        });
        txt_productid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_productidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_productidKeyReleased(evt);
            }
        });

        txt_productname.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        txt_productname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_productnameKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_productnameKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Price");

        txt_productprice.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        txt_productprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_productpriceKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_quantityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_quantityKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_productid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_productname, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_productprice, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_productid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_productname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_productprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 1340, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Billing_System/FormatFactory035-shopping-cart.png"))); // NOI18N
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 150, 130));

        txt_itemcount.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        txt_itemcount.setForeground(new java.awt.Color(255, 255, 255));
        txt_itemcount.setText("Number Of Item    : 0");
        jPanel3.add(txt_itemcount, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 190, 30));

        jMenu1.setText("File");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("New");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Exit");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Clear All");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1371, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 1387, 744);
    }// </editor-fold>//GEN-END:initComponents

    
    private void cmd_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_deleteActionPerformed
        if(!deleteconfirm.confoirm){
//            System.out.println("***");
            JOptionPane.showMessageDialog(null, "Press Again \"Delete\" For Delete,After Admin Verification ! ");
            deleteconfirm s = new deleteconfirm();
            s.setVisible(true);
            
            }
        if(deleteconfirm.confoirm){
            deleteconfirm.confoirm = false;
            int p = JOptionPane.showConfirmDialog(null,"Do You Really Want To Delete?","Delete",JOptionPane.YES_NO_OPTION);
            if(p == 0){
            try{
//                System.out.println("12345");
                String sql = "select name,quantity from currentbill where id = '"+table_click+"'" ;
                pst = c.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    String name = rs.getString("name");
                    String stock = rs.getString("quantity");
                    sql = "select product_stock,barcode from product where product_name= '"+name+"' " ;
                    pst = c.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()){
                        stock = Double.valueOf(stock) + Double.valueOf(rs.getString("product_stock")) + "";
                        sql = "update product set product_stock= '"+stock+"' where barcode= '"+rs.getString("barcode")+"'" ;
                        pst = c.prepareStatement(sql);
                        pst.execute(); 
                    }
                

                    
                    sql = "delete from currentbill where id = '"+table_click+"' ";
                    pst = c.prepareStatement(sql);
                    pst.execute(); 
                }
                
                


            }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e);
                 e.printStackTrace();
            }
            get_sum();
            update_table();
            txt_itemcount.setText("Number Of Item    : "+table_bill.getRowCount());
        }
        }
        
    }//GEN-LAST:event_cmd_deleteActionPerformed

    private void table_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_billMouseClicked
        get_select();
    }//GEN-LAST:event_table_billMouseClicked

    private void cmd_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_payActionPerformed
        close();
        new Payment(cashier).setVisible(true);
    }//GEN-LAST:event_cmd_payActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void cmd_payloanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_payloanActionPerformed
        loansettlement s = new loansettlement();
        s.setVisible(true);
    }//GEN-LAST:event_cmd_payloanActionPerformed

    private void table_billKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_billKeyReleased
        if((evt.getKeyCode()==KeyEvent.VK_UP) || (evt.getKeyCode()==KeyEvent.VK_DOWN)){
            get_select();
        }
    }//GEN-LAST:event_table_billKeyReleased

    private void txt_quantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_quantityKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            String value = txt_quantity.getText();
           if(value.length()!=0){
            double price = Double.valueOf(txt_productprice.getText());
            int quantity = Integer.valueOf(txt_quantity.getText());
            itemnumber += 1;

            try{

                String productid = txt_productid.getText();
                String name = txt_productname.getText();

                String date = year + "-" + (month) + "-" + day ;
                String time = hour + ":" + minutes + ":" + second;
//                System.out.println(date);
//                java.sql.Date date1 = java.sql.Date.valueOf(date);
//                java.sql.Time time1 = java.sql.Time.valueOf(time);
                DateFormat date1 = new SimpleDateFormat("yyyy-mm-dd");
                
                String stock ;
                String sql = "select product_stock from product where barcode= ?" ;
                pst = c.prepareStatement(sql);
                pst.setString(1,txt_productid.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    double nowstock = Double.valueOf(rs.getString("product_stock")) - Double.valueOf(txt_quantity.getText()) ;
                    if(nowstock<=0){
                        JOptionPane.showMessageDialog(null, "Out Of Stock, But Process Will Continue ! ");
                    }
                    stock =   (nowstock)+ "";
                    
                    sql = "update product set product_stock= '"+stock+"' where barcode= '"+productid+"'" ;
                    pst = c.prepareStatement(sql);
                    pst.execute(); 
                }
                
                sql = "select product_price from product where barcode= ?" ;
                pst = c.prepareStatement(sql);
                pst.setString(1,txt_productid.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    double oldprice = Double.valueOf(rs.getString("product_price")) ;
                    double newprice = Double.valueOf(txt_productprice.getText());
                    if(oldprice != newprice ){
                        int p = JOptionPane.showConfirmDialog(null,"Did You Want Update New Price?","Update Price",JOptionPane.YES_NO_OPTION);
                        if(p ==0 ){
                            sql = "update product set product_price = '"+txt_productprice.getText()+"' where barcode= '"+productid+"'" ;
                            pst = c.prepareStatement(sql);
                            pst.execute(); 
                            JOptionPane.showMessageDialog(null, "Updted Successfuly");
                        }
                    }
                }
                double productTotal = price*quantity;
                sql = "Insert into bill (receiptid,productid,quantity, price,date,time,cashier) values (?,?,?,?,?,?,?)";
                pst = c.prepareStatement(sql);
                pst.setString(1,billnumber+"");
                pst.setString(2,txt_productid.getText());
                pst.setString(3,txt_quantity.getText());
                pst.setString(4,(productTotal+""));
                pst.setString(5,date.format(date));
                pst.setString(6,time);
                pst.setString(7,cashier);
                pst.execute();
                sql = "Insert into currentbill (name,total,quantity, price,id) values (?,?,?,?,?)" ;
                pst = c.prepareStatement(sql);
                pst.setString(1,txt_productname.getText());
                pst.setString(3,txt_quantity.getText());
                pst.setString(2,(productTotal+""));
                pst.setString(4,txt_productprice.getText());
                pst.setString(5,itemnumber+"");
                pst.execute();

            }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
            }
            get_sum();
            update_table();
            txt_productid.setText("");
            txt_productname.setText("");
            txt_productprice.setText("");
            txt_quantity.setText("");
            txt_itemcount.setText("Number Of Item    : "+table_bill.getRowCount());
            txt_productid.requestFocusInWindow();

        }
      }
    }//GEN-LAST:event_txt_quantityKeyPressed

    private void txt_productnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productnameKeyReleased
//        String s = txt_productname.getText();
//        StringBuilder builder = new StringBuilder(s);
//        char c1;
//        for(int i = 0;i<s.length();i++){
//            c1 = s.charAt(i);
//            if(Character.isUpperCase(c1)){
//                builder.deleteCharAt(i);
//                s = builder.toString();
//            }
//        }
//        txt_productname.setText(s.toString());
//        
//        try{
//
//            String sql = "select * from product where product_name = ?" ;
//            pst = c.prepareStatement(sql);
//            pst.setString(1,txt_productname.getText());
//            rs = pst.executeQuery();
//            if(rs.next()){
//                String add1 = rs.getString("productid");
//                txt_productid.setText(add1);
//
//                String add2 = rs.getString("product_price");
//                txt_productprice.setText(add2);
//
//            }
//
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
//            ////e.printStackTrace();
//        }
    }//GEN-LAST:event_txt_productnameKeyReleased

    private void txt_productidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productidKeyReleased
        String s = txt_productid.getText();
        StringBuilder builder = new StringBuilder(s);
        char c1;
        for(int i = 0;i<s.length();i++){
            c1 = s.charAt(i);
            if(Character.isLowerCase(c1)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }if(Character.isUpperCase(c1)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }
        }
        txt_productid.setText(s.toString());
        
        try{
            String pid = txt_productid.getText();
            String sql = "select * from product where barcode = ?" ;
            pst = c.prepareStatement(sql);
            pst.setString(1,txt_productid.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                String add1 = rs.getString("product_name");
                txt_productname.setText(add1);

                String add2 = rs.getString("product_price");
                txt_productprice.setText(add2);
                
                txt_quantity.requestFocusInWindow();

            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            //e.printStackTrace();
        }
    }//GEN-LAST:event_txt_productidKeyReleased

    private void txt_productidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productidKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_productidKeyPressed

    private void txt_productidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_productidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_productidActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int p = JOptionPane.showConfirmDialog(null,"Do You Really Want To Log Out?","Log Out",JOptionPane.YES_NO_OPTION);
        if(p == 0){
            close();
            login_frame s = new login_frame();
            s.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_productpriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productpriceKeyReleased
       String s = txt_productprice.getText();
        StringBuilder builder = new StringBuilder(s);
        char c1;
        for(int i = 0;i<s.length();i++){
            c1 = s.charAt(i);
            if(Character.isLowerCase(c1)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }
            if(Character.isUpperCase(c1)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }
        }
        txt_productprice.setText(s.toString());
    }//GEN-LAST:event_txt_productpriceKeyReleased

    private void txt_quantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_quantityKeyReleased
        String s = txt_quantity.getText();
        StringBuilder builder = new StringBuilder(s);
        boolean hasnum = false;boolean hasupp = false;boolean haslow = false;char c;
        for(int i = 0;i<s.length();i++){
            c = s.charAt(i);
            if(Character.isLowerCase(c)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }if(Character.isUpperCase(c)){
                builder.deleteCharAt(i);
                s = builder.toString();
            }
        }
        txt_quantity.setText(s.toString());
    }//GEN-LAST:event_txt_quantityKeyReleased

    private void txt_productnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_productnameKeyPressed
        switch(evt.getKeyCode()){
            case KeyEvent.VK_BACK_SPACE:
                break;
            case KeyEvent.VK_ENTER:
                txt_productname.setText(txt_productname.getText());
                String s = txt_productname.getText();
                StringBuilder builder = new StringBuilder(s);
                char c1;
                for(int i = 0;i<s.length();i++){
                    c1 = s.charAt(i);
                    if(Character.isUpperCase(c1)){
                        builder.deleteCharAt(i);
                        s = builder.toString();
                    }
                }
                txt_productname.setText(s.toString());

                try{

                    String sql = "select * from product where product_name = ?" ;
                    pst = c.prepareStatement(sql);
                    pst.setString(1,txt_productname.getText());
                    rs = pst.executeQuery();
                    if(rs.next()){
                        String add1 = rs.getString("barcode");
                        txt_productid.setText(add1);

                        String add2 = rs.getString("product_price");
                        txt_productprice.setText(add2);
                        
                        txt_quantity.requestFocusInWindow();

                    }

                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    ////e.printStackTrace();
                }
                break;
            default:
                EventQueue.invokeLater(new Runnable(){
                    @Override
                    
                 public void run(){
                     String txt = txt_productname.getText();
                        autocomplete(txt);
                 }
                });
        }
    }//GEN-LAST:event_txt_productnameKeyPressed

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
            java.util.logging.Logger.getLogger(makebill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(makebill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(makebill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(makebill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new makebill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd_delete;
    private javax.swing.JButton cmd_pay;
    private javax.swing.JButton cmd_payloan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable table_bill;
    private javax.swing.JLabel txt_billnumber;
    private javax.swing.JLabel txt_datetime;
    private javax.swing.JLabel txt_itemcount;
    private javax.swing.JTextField txt_productid;
    private javax.swing.JTextField txt_productname;
    private javax.swing.JTextField txt_productprice;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JLabel txt_subtotal;
    private javax.swing.JLabel txt_user;
    // End of variables declaration//GEN-END:variables
}
