/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author gayeu
 */
public class Room extends javax.swing.JFrame {

    /**
     * Creates new form sohbet
     */
    public static Room ThisSohbet;
    public static File file = null;
    public static boolean selectFile = false;
    public static JFileChooser fileChooser = new JFileChooser();

    public Room() {
        initComponents();
        ThisSohbet = this;
        lbl_name.setText(client.Client.kisi_ad2);
    }

    public Room(String person) throws InterruptedException {
        initComponents();
        ThisSohbet = this;
        lbl_name.setText(person);
        Message msg = new Message(Message.Message_Type.ChatPrivate);
        msg.content = person + "-" + application.anasayfa.user1;
        client.Client.Send(msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        lbl_name = new javax.swing.JLabel();
        txt_mess = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_mess = new javax.swing.JTextArea();
        btn_back = new javax.swing.JButton();
        btn_dosya = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(450, 640));
        setResizable(false);
        getContentPane().setLayout(null);

        lbl_name.setBackground(new java.awt.Color(255, 255, 255));
        lbl_name.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lbl_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_name.setOpaque(true);
        getContentPane().add(lbl_name);
        lbl_name.setBounds(140, 40, 290, 50);

        txt_mess.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_mess.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_messKeyReleased(evt);
            }
        });
        getContentPane().add(txt_mess);
        txt_mess.setBounds(20, 370, 300, 58);

        btn_send.setBackground(new java.awt.Color(255, 255, 255));
        btn_send.setText("Send");
        btn_send.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });
        getContentPane().add(btn_send);
        btn_send.setBounds(20, 440, 410, 58);

        list_mess.setColumns(20);
        list_mess.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        list_mess.setLineWrap(true);
        list_mess.setRows(5);
        list_mess.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jScrollPane1.setViewportView(list_mess);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 100, 410, 260);

        btn_back.setText("Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        getContentPane().add(btn_back);
        btn_back.setBounds(20, 40, 110, 50);

        btn_dosya.setText("Choose File");
        btn_dosya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dosyaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dosya);
        btn_dosya.setBounds(330, 370, 100, 60);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/image.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1000, 2000));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 550, 580);

        pack();
    }// </editor-fold>                        

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (selectFile == false) {
            try {
                list_mess.setText(list_mess.getText() + application.anasayfa.user1 + ":" + txt_mess.getText() + " \n");
                Message msg = new Message(Message.Message_Type.Mess);
                msg.content = lbl_name.getText() + "_" + list_mess.getText();
                Thread.sleep(300);
                client.Client.Send(msg);
                txt_mess.setText("");
            } catch (InterruptedException ex) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                list_mess.setText(list_mess.getText() + application.anasayfa.user1 + ": " + file.getName() + " File" + " \n");
                txt_mess.setText("");
                Message msg = new Message(Message.Message_Type.PrivateFileSender);
                msg.content = lbl_name.getText() + "_" + list_mess.getText();

                client.Client.Send(msg);

                Thread.sleep(100);
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileContentBytes = new byte[fileInputStream.available()];
                fileInputStream.read(fileContentBytes);
                fileInputStream.close();

                String fileContentBytes_string = Base64.getEncoder().encodeToString(fileContentBytes);
                Message msg4 = new Message(Message.Message_Type.File);
                msg4.content = lbl_name.getText() + "&" + file.getName() + "_" + fileContentBytes_string;
                client.Client.Send(msg4);
                file = null;

            } catch (FileNotFoundException e) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, e);
            } catch (InterruptedException e) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, e);
            }

        }

    }                                        

    private void txt_messKeyReleased(java.awt.event.KeyEvent evt) {                                     
        // TODO add your handling code here:

    }                                    

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        ThisSohbet.setVisible(false);
        anasayfa.ThisAnasayfaPage.setVisible(true);

        Message msg = new Message(Message.Message_Type.Back);
        msg.content = lbl_name.getText().toString();
        client.Client.Send(msg);
    }                                        

    private void btn_dosyaActionPerformed(java.awt.event.ActionEvent evt) {                                          

        int choose_File = fileChooser.showOpenDialog(this);

        if (choose_File == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            selectFile = true;

        } else {
            JOptionPane.showMessageDialog(fileChooser, "Try again");
        }
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
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Room.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Room().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_dosya;
    private javax.swing.JButton btn_send;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lbl_name;
    public javax.swing.JTextArea list_mess;
    private javax.swing.JTextField txt_mess;
    // End of variables declaration                   
}
