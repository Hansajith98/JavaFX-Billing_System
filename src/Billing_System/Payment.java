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
import sun.security.util.Length;
/**
 *
 * @author Deshitha Hansajith
 */
public class Payment extends javax.swing.JFrame {
    Connection c = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    static double subtotal =0;
    static double balance = -1;
    static String paymethod;
    static int methodid = 0;
    static String receiptid;
    static String cashier;
    static String date;
    static String time;
    static boolean loanset = false;
    static boolean cardset = false;
    static boolean cardlen = false;
    static double loan_limit = 50000;
    static String billprint;
    static String month ;
    static String year ;
    static String day ;
    static String second; 
    static String minutes; 
    static String hour;
    
    
    
        

    /**
     * Creates new form Payment
     */
    public Payment() {
        initComponents();
        c = DB.connecrDB();
        fillcombo();
        getReceiptId();
        get_sum();
        currentdatetime();
        //cmd_printbill.setVisible(false);
        txt_cardnumber.setVisible(false);
        jLabel3.setVisible(false);
        set_print();
           
    }
    public Payment(String fromuser) {
        initComponents();
        c = DB.connecrDB();
        fillcombo();
        getReceiptId();
        get_sum();
        currentdatetime();
        cmd_printbill.setVisible(false);
        txt_cardnumber.setVisible(false);
        jLabel3.setVisible(false);
        cashier = fromuser;
        set_print();
        
        
    }
    public void set_print(){
        try{
            Thread.sleep(100);
            String header ="";
            String footer ="";
            String amount="";
            String sql = "select * from settings" ;
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                header = rs.getString("header");
                footer = rs.getString("footer");
            }
            sql = "select sum(amount) from paymethod where receiptid = ? ";
            pst = c.prepareStatement(sql);
            pst.setString(1,txt_receiptid.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                amount = (rs.getString("sum(amount)"));
            }
            String billprint =      "*****************************************\n";
            billprint = billprint + "*************"+ header +"************\n";
            System.out.println(header);
            billprint = billprint + "*****************************************\n";
            billprint = billprint + "Time "+time+"\t Date :"+date+"\n";
            billprint = billprint + "Cashier :\t"+cashier+"\n";
            billprint = billprint + "----------------------------------------------------\n";
            billprint = billprint + "|Quantity\t"+"|Price\t"+"|Total\n";
            billprint = billprint + "----------------------------------------------------\n";
            sql = "select id, name, price, quantity,total from currentbill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                billprint = billprint +rs.getString("name") + "\n";
                billprint = billprint + rs.getString("quantity") + "\t"+ rs.getString("Price") + "\t"+ rs.getString("total")+"\n";

            }
            billprint = billprint + "----------------------------------------------------\n";
            billprint = billprint + "Received Amount :\t"+amount+"\n";
            billprint = billprint + "Balance :\t"+txt_balance.getText()+"\n";
            billprint = billprint + "----------------------------------------------------\n";
            billprint = billprint + "***********"+ footer +"**************\n\n";
            billprint = billprint + "Software By @EvrysoftWR\n";
            area.setText(billprint);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
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
    
    private void set_bill_table(){
        try{
            Statement statement = c.createStatement();
            statement.executeUpdate("DELETE FROM currentbill");
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
             //////e.printStackTrace();
        }
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
                        date = year + "/" + (month + 1) + "/" + day;
                        time =  hour + ":" + minutes + ":" + second;
                        sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(login_frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        clock.start();
    }
    
    private void getReceiptId(){
        try{
            String sql = "select max(receiptid) from bill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                int bill_number = Integer.valueOf(rs.getString("max(receiptid)"));
                bill_number += 1;
                txt_receiptid.setText(bill_number+"");
                receiptid = bill_number+"";
            }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
        }
    }

    
     private void fillcombo(){
        cbox_paymethod.addItem("Cash");
        cbox_paymethod.addItem("Card Payment");
        cbox_paymethod.addItem("Loan");

     }
     
     private void update_table(){
        try{
            String sql = "SELECT * FROM paymethod WHERE receiptid = ?";
            pst = c.prepareStatement(sql);
            pst.setString(1,(txt_receiptid.getText()));
            rs = pst.executeQuery();
            tble_paymethod.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            //e.printStackTrace();
        }
     }
     
     private void get_sum(){
        try{
            String sql = "select sum(total) from currentbill";
            pst = c.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txt_mustpay.setText(rs.getString("sum(total)") );
                subtotal = Double.valueOf(rs.getString("sum(total)"));
                
            }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
             e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_receiptid = new javax.swing.JLabel();
        cbox_paymethod = new javax.swing.JComboBox();
        txt_leftpay = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_mustpay = new javax.swing.JLabel();
        txt_payed = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tble_paymethod = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_cardnumber = new javax.swing.JTextField();
        cmd_printbill = new javax.swing.JButton();
        txt_balance = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));

        jLabel1.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N
        jLabel1.setText("Bill Number  :");

        txt_receiptid.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N

        cbox_paymethod.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbox_paymethodPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txt_leftpay.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N
        txt_leftpay.setText("left To Pay : ");

        jLabel2.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N
        jLabel2.setText("Amount  :");

        txt_mustpay.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N

        txt_payed.setFont(new java.awt.Font("Square721 BdEx BT", 1, 14)); // NOI18N
        txt_payed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_payedActionPerformed(evt);
            }
        });
        txt_payed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_payedKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_payedKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbox_paymethod, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_receiptid, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 337, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_payed, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_leftpay, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_mustpay, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)))
                .addGap(37, 37, 37))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_receiptid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_leftpay, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_mustpay, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_payed, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(cbox_paymethod, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 969, -1));

        tble_paymethod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
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
        jScrollPane1.setViewportView(tble_paymethod);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 104, 969, 210));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Enter Last Four Digit of Card Number :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 463, 250, 27));

        txt_cardnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cardnumberKeyReleased(evt);
            }
        });
        jPanel1.add(txt_cardnumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 81, 27));

        cmd_printbill.setBackground(new java.awt.Color(255, 0, 0));
        cmd_printbill.setFont(new java.awt.Font("Trajan Pro 3", 1, 14)); // NOI18N
        cmd_printbill.setText("Print Bill");
        cmd_printbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_printbillActionPerformed(evt);
            }
        });
        jPanel1.add(cmd_printbill, new org.netbeans.lib.awtextra.AbsoluteConstraints(803, 434, 140, 50));

        txt_balance.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txt_balance.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(txt_balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, 520, 60));

        area.setColumns(20);
        area.setRows(5);
        jScrollPane2.setViewportView(area);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 240, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(100, 100, 1254, 547);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_payedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_payedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_payedActionPerformed

    private void cbox_paymethodPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbox_paymethodPopupMenuWillBecomeInvisible
        paymethod = (String) cbox_paymethod.getSelectedItem();
        if("Loan" == paymethod){
            txt_payed.setText(txt_mustpay.getText());
            loanset = true;
            loan s = new loan();
            s.setVisible(true);
        }else if("Card Payment" == paymethod){
            txt_cardnumber.requestFocusInWindow();
            txt_payed.setText(txt_mustpay.getText());
            cardset = true;
            txt_cardnumber.setVisible(true);
            jLabel3.setVisible(true);
        }
    }//GEN-LAST:event_cbox_paymethodPopupMenuWillBecomeInvisible

    private void txt_payedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_payedKeyReleased
        String s = txt_payed.getText();
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
        txt_payed.setText(s.toString());
    }//GEN-LAST:event_txt_payedKeyReleased

    private void txt_payedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_payedKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            
            double payed =0;
            double paying = Double.valueOf(txt_payed.getText());
            
            try{
                String sql = "Insert into paymethod(receiptid,paymethod,amount,customerid,paymethodid) values(?,?,?,?,?)";
                pst = c.prepareStatement(sql);
                pst.setString(1,(txt_receiptid.getText()));
                pst.setString(2,((String) cbox_paymethod.getSelectedItem()));
                pst.setString(3,txt_payed.getText());
                pst.setString(4,loan.cusid);
                pst.setString(5,methodid+"");
                pst.execute(); 
                
                sql = "select sum(amount) from paymethod where receiptid = ? ";
                pst = c.prepareStatement(sql);
                pst.setString(1,txt_receiptid.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    payed = Double.valueOf(rs.getString("sum(amount)"));
                }
                
                balance = payed-subtotal;
                //System.out.println(balance +" "+ payed +" "+ subtotal);
                if(loanset == true){
                    sql = "select sum(loan) from customer where customerid = ? ";
                    pst = c.prepareStatement(sql);
                    pst.setString(1,loan.cusid);
                    rs = pst.executeQuery();
                    if(rs.next()){
                        double cusloan = Double.valueOf(rs.getString("sum(loan)"));
                        if(cusloan > loan_limit){
//                            System.out.println(payed);
                            loanset = false;
                            txt_payed.setText("");
                            JOptionPane.showMessageDialog(null, "This Customer Exceeded Loan Limit !");
                            cbox_paymethod.addItem("Cash");
                            cbox_paymethod.addItem("Card Payment");
                            
                        }else if(cusloan < loan_limit){
                            cusloan += Double.valueOf(txt_payed.getText());
                            String add1 = cusloan + "";
                            sql = "update customer set loan = '"+add1+"'  where customerid = '"+loan.cusid+"'" ;
                            pst = c.prepareStatement(sql);
                            pst.execute(); 
//                            System.out.println(cusloan);
                            String s = "This Customer's current Loan :" + cusloan;
                            JOptionPane.showMessageDialog(null, s);
                            
                        }
                    }
                }
                if(subtotal>payed){
                    String mustpay = (subtotal-payed)+"";
                    txt_mustpay.setText(mustpay);
//                    System.out.println(mustpay);
                    

                }else if(subtotal<=payed){
//                    System.out.println(payed-subtotal);
//                    System.out.println(cardset);
                    if(cardset == true){
                        if(cardlen==true){
                            cmd_printbill.setVisible(true);
                        }
                            
                    }else if(cardset == false){
                        cmd_printbill.setVisible(true); 
                        txt_balance.setText("Balance   :"+(balance));
                        txt_payed.setVisible(false);
                        txt_leftpay.setVisible(false);
                        jLabel2.setVisible(false);
                        txt_mustpay.setVisible(false);
                        cbox_paymethod.setVisible(false);
                    }
                    
                     

                }
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                 e.printStackTrace();
            }
            
            update_table();
            set_print();
            txt_payed.setText("");
            
            

        }  
    }//GEN-LAST:event_txt_payedKeyPressed

    private void txt_cardnumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cardnumberKeyReleased
        String s = txt_cardnumber.getText();
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
        txt_cardnumber.setText(s.toString());
        
        String cardnumber = txt_cardnumber.getText();
                        if(4 == (cardnumber.length())){
                            if(balance>=0){
                                cmd_printbill.setVisible(true);
                            }
                            
                            cardlen = true;
                            txt_payed.requestFocusInWindow();
                        }
    }//GEN-LAST:event_txt_cardnumberKeyReleased

    private void cmd_printbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_printbillActionPerformed
        try{
            
            
            area.setText(billprint);
            area.print();
            
//            String amt = "Date : "+date +"%7s"+ " Time : " + time+"/n;";
//            printnow p = new printnow();
//            printnow.printcard(area.getText());
//        System.out.println(""+DbUtils.resultSetToTableModel(rs));
        JOptionPane.showMessageDialog(null,"Bill Create Successfuly!");
        dispose();
        set_bill_table();
        set_pay_method();
        new makebill(cashier).setVisible(true);
        }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                 e.printStackTrace();
            }
            
        
        
        
    }//GEN-LAST:event_cmd_printbillActionPerformed

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
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JComboBox cbox_paymethod;
    private javax.swing.JButton cmd_printbill;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tble_paymethod;
    private javax.swing.JLabel txt_balance;
    private javax.swing.JTextField txt_cardnumber;
    private javax.swing.JLabel txt_leftpay;
    private javax.swing.JLabel txt_mustpay;
    private javax.swing.JTextField txt_payed;
    private javax.swing.JLabel txt_receiptid;
    // End of variables declaration//GEN-END:variables
}
