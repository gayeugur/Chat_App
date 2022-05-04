 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author busra
 */
public class anasayfa extends javax.swing.JFrame {

    /**
     * Creates new form anasayfa
     */
    //framedeki komponentlere erişim için statik oyun değişkeni
    public static anasayfa ThisAnasayfa;
    //cevrimici kullanicilari tutmak icin
    public DefaultListModel users;

    public anasayfa() {
        initComponents();
        ThisAnasayfa = this;
        ThisAnasayfa.setPreferredSize(new Dimension(450, 640));
        users = new DefaultListModel();
        online_users.setModel(users);
    }

    public anasayfa(String s) {
        initComponents();
        ThisAnasayfa = this;
        ThisAnasayfa.setPreferredSize(new Dimension(450, 640));
        ThisAnasayfa.lbl_hesapadi2.setText(s);
        users = new DefaultListModel();
        online_users.setModel(users);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        online_users = new javax.swing.JList<String>();
        btn_sohbetBaslat = new javax.swing.JButton();
        lbl_hesapadi2 = new javax.swing.JLabel();
        lbl_hesapadi = new javax.swing.JLabel();
        btn_grupOlustur = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(450, 640));
        getContentPane().setLayout(null);

        online_users.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        online_users.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        online_users.setForeground(new java.awt.Color(0, 51, 153));
        jScrollPane1.setViewportView(online_users);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 90, 430, 430);

        btn_sohbetBaslat.setBackground(new java.awt.Color(0, 51, 153));
        btn_sohbetBaslat.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_sohbetBaslat.setForeground(new java.awt.Color(255, 255, 255));
        btn_sohbetBaslat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/user.png"))); // NOI18N
        btn_sohbetBaslat.setText("Sohbeti Başlat ");
        btn_sohbetBaslat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sohbetBaslatActionPerformed(evt);
            }
        });
        getContentPane().add(btn_sohbetBaslat);
        btn_sohbetBaslat.setBounds(210, 520, 220, 70);

        lbl_hesapadi2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lbl_hesapadi2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lbl_hesapadi2);
        lbl_hesapadi2.setBounds(20, 10, 210, 30);

        lbl_hesapadi.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lbl_hesapadi);
        lbl_hesapadi.setBounds(30, 20, 130, 0);

        btn_grupOlustur.setBackground(new java.awt.Color(0, 51, 153));
        btn_grupOlustur.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_grupOlustur.setForeground(new java.awt.Color(255, 255, 255));
        btn_grupOlustur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/grup.png"))); // NOI18N
        btn_grupOlustur.setText(" Grup Oluştur");
        btn_grupOlustur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grupOlusturActionPerformed(evt);
            }
        });
        getContentPane().add(btn_grupOlustur);
        btn_grupOlustur.setBounds(0, 520, 210, 70);

        jLabel1.setBackground(new java.awt.Color(0, 102, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("     Çevrimiçi Kullanıcılar ");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 430, 90);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sohbetBaslatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sohbetBaslatActionPerformed
        // TODO add your handling code here:
        //secili kisi ile bireysel mesajlasma frame'ini olusturur
        String kisi = online_users.getSelectedValue().toString();
        ThisAnasayfa.setVisible(false);
        try {
            Thread.sleep(300);
            new sohbet(kisi).setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sohbetBaslatActionPerformed

    private void btn_grupOlusturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grupOlusturActionPerformed
        //Secili kisiler ile grup mesajlasmasi icin frame olusturur
        try {
            ThisAnasayfa.setVisible(false);
            Thread.sleep(300);
            new grupOlustur(users).setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_grupOlusturActionPerformed

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
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(anasayfa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new anasayfa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_grupOlustur;
    private javax.swing.JButton btn_sohbetBaslat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_hesapadi;
    private javax.swing.JLabel lbl_hesapadi2;
    public javax.swing.JList<String> online_users;
    // End of variables declaration//GEN-END:variables

}
