/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Billing_System;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 *
 * @author Deshitha Hansajith
 */
public class printnow {
    public static Boolean printcard(final String bill){
        final PrinterJob job = PrinterJob.getPrinterJob();
        Printable contentToPrint = new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//                throw new UnsupportedOperationException("Not supported yet."); To change body of generated methods, choose Tools | Templates.
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
                g2d.setFont(new Font("Monospaced",Font.BOLD,10));
                
                String[] billz = bill.split(":");
                int y=15;
                for(int i=0;i<billz.length;i++){
                    graphics.drawString(billz[i], 5, y);
                    y += 15;
                    
                }
                if(pageIndex>1){return NO_SUCH_PAGE;}
                
                return PAGE_EXISTS;
            }
        };
        PageFormat pageformat = new PageFormat();
        pageformat.setOrientation(PageFormat.PORTRAIT);
        Paper paper =  pageformat.getPaper();
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
        pageformat.setPaper(paper);
        job.setPrintable(contentToPrint,pageformat);
        
        try{
            job.print();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
}
