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
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author gayeu
 */
public class Group extends javax.swing.JFrame {

    /**
     * Creates new form GrupSohbet
     *
     */
    public static Group ThisGrupSohbet;
    public DefaultListModel kisilerListesi;
    public static String grup_adi;
    public static String kisiler;
    public static File fileToSend = null;

    public Group() {
        initComponents();
        ThisGrupSohbet = this;
        //  lbl_grupName.setText(anasayfa.groupName);
    }

    public Group(String s) throws InterruptedException {
        initComponents();
        ThisGrupSohbet = this;
        grup_mesaj_akisi.setEditable(true);

        Thread.sleep(500);
        String[] parts = s.split("_");
        grup_adi = parts[0];
        kisiler = parts[1];
        Thread.sleep(100);

        ThisGrupSohbet.lbl_grupName.setText("Grup Name: " + grup_adi);
        Message msg = new Message(Message.Message_Type.groupUsers);
        msg.content = grup_adi + "_" + kisiler;

        client.Client.Send(msg);

    }

    public Group(String s, int i) throws InterruptedException {
        initComponents();
        ThisGrupSohbet = this;
        grup_mesaj_akisi.setEditable(true);
        // ThisGrupSohbet.lbl_grupName.setText(anasayfa.groupName);   
        Thread.sleep(500);

        String[] parts3 = s.split("_");
        grup_adi = parts3[0];
        kisiler = parts3[1];
        ThisGrupSohbet.lbl_grupName.setText("Grup Name: " + grup_adi);
        Thread.sleep(100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        grup_mesaj_kutusu = new javax.swing.JTextField();
        btn_mesaj_gonder_grup = new javax.swing.JButton();
        btn_dosya = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        grup_mesaj_akisi = new javax.swing.JTextPane();
        lbl_grupName = new javax.swing.JLabel();
        btn_back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(450, 640));
        getContentPane().setLayout(null);

        grup_mesaj_kutusu.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        grup_mesaj_kutusu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));
        getContentPane().add(grup_mesaj_kutusu);
        grup_mesaj_kutusu.setBounds(20, 410, 320, 60);

        btn_mesaj_gonder_grup.setText("Send");
        btn_mesaj_gonder_grup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mesaj_gonder_grupActionPerformed(evt);
            }
        });
        getContentPane().add(btn_mesaj_gonder_grup);
        btn_mesaj_gonder_grup.setBounds(20, 480, 430, 60);

        btn_dosya.setText("Choose File");
        btn_dosya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dosyaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dosya);
        btn_dosya.setBounds(350, 410, 100, 60);

        grup_mesaj_akisi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        grup_mesaj_akisi.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(grup_mesaj_akisi);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 60, 430, 340);
        getContentPane().add(lbl_grupName);
        lbl_grupName.setBounds(120, 10, 330, 40);

        btn_back.setText("Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        getContentPane().add(btn_back);
        btn_back.setBounds(30, 10, 80, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/image.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 480, 550);

        pack();
    }// </editor-fold>                        

    private void btn_mesaj_gonder_grupActionPerformed(java.awt.event.ActionEvent evt) {                                                      

        if (fileToSend == null) {

            grup_mesaj_akisi.setText(grup_mesaj_akisi.getText() + application.anasayfa.user1 + ":\n" + grup_mesaj_kutusu.getText().toString() + "\n");
            grup_mesaj_kutusu.setText("");

            Message msg = new Message(Message.Message_Type.GroupFileSender);
            msg.content = kisiler + "_" + grup_mesaj_akisi.getText();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
            }
            client.Client.Send(msg);
            grup_mesaj_kutusu.setText("");
            //dosya gonderme durumu varsa
        } else if (fileToSend != null) {
            try {
                grup_mesaj_akisi.setText(grup_mesaj_akisi.getText() + application.anasayfa.user1 + ":\n" + "--- Dosya : " + fileToSend.getName() + " ---\n" + grup_mesaj_kutusu.getText() + "\n");
                grup_mesaj_kutusu.setText("");

                Message msg = new Message(Message.Message_Type.GroupFileSender);
                msg.content = kisiler + "_" + grup_mesaj_akisi.getText();
                Thread.sleep(100);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
                }
                client.Client.Send(msg);

                FileInputStream fileInputStream = new FileInputStream(fileToSend);
                String fileName = fileToSend.getName();
                byte[] fileContentBytes = new byte[fileInputStream.available()];
                fileInputStream.read(fileContentBytes);
                fileInputStream.close();
                fileToSend = null;

                String fileContentBytes_string = Base64.getEncoder().encodeToString(fileContentBytes);
                Message msg4 = new Message(Message.Message_Type.File);
                msg4.content = kisiler + "&" + fileName + "_" + fileContentBytes_string;
                client.Client.Send(msg4);
                Thread.sleep(300);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }                                                     

    private void btn_dosyaActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        //dosya cubugunu acar ve gondermek istenilen dosyayi secmeye yarar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose the file");
        int choose_File = fileChooser.showOpenDialog(this);

        if (choose_File == JFileChooser.APPROVE_OPTION) {
            fileToSend = fileChooser.getSelectedFile();

        } else {
            JOptionPane.showMessageDialog(fileChooser, "Try again");
        }
    }                                         

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {                                         
        ThisGrupSohbet.setVisible(false);
        anasayfa.ThisAnasayfaPage.setVisible(true);

        Message msg = new Message(Message.Message_Type.BackGroup);
        msg.content = lbl_grupName.getText();
        client.Client.Send(msg);
        
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
            java.util.logging.Logger.getLogger(Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Group().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_dosya;
    private javax.swing.JButton btn_mesaj_gonder_grup;
    public javax.swing.JTextPane grup_mesaj_akisi;
    private javax.swing.JTextField grup_mesaj_kutusu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_grupName;
    // End of variables declaration                   
}
