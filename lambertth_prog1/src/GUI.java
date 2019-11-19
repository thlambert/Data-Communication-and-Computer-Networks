
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author lambertth
 */
public class GUI extends javax.swing.JFrame {
    Socket s;
    BufferedReader input;
    DataOutputStream output;
    BufferedReader inFromUser;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConnect = new javax.swing.JButton();
        lblIP = new javax.swing.JLabel();
        lblPort = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtMsg = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComm = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Program 1 Knock Knock Client");
        setLocationByPlatform(true);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        lblIP.setText("IP Address");

        lblPort.setText("Port Number");

        jLabel1.setText("Message to Server");

        txtIP.setText("137.104.21.4");

        txtPort.setText("5764");

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel2.setText("Client/Server Communication");

        txtComm.setEditable(false);
        txtComm.setColumns(20);
        txtComm.setRows(5);
        jScrollPane1.setViewportView(txtComm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMsg, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIP)
                                .addGap(26, 26, 26)
                                .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPort)
                                .addGap(18, 18, 18)
                                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnConnect))
                            .addComponent(btnSend)
                            .addComponent(jLabel2))
                        .addGap(0, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIP)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPort)
                    .addComponent(btnConnect)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSend)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        try
        {
            if(btnConnect.getText().equals("Connect"))
            {
                if(!txtIP.getText().equals("") && !txtPort.getText().equals(""))
                {
                    inFromUser = new BufferedReader(new InputStreamReader
                            (System.in));
                    s = new Socket(txtIP.getText(), 
                            Integer.parseInt(txtPort.getText()));
                    txtComm.setText(txtComm.getText() 
                            +  "Connected to Server\n");
                    input = new BufferedReader(new InputStreamReader(
                            s.getInputStream()));
                    output = new DataOutputStream(s.getOutputStream());
                    btnConnect.setText("Disconnect");
                }
            }
            else
            {
                close();
            } 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        try
        {
            //int temp = input.read();
            if(btnConnect.getText().equals("Disconnect"))
            {
                if(txtMsg.getText().equals("quit"))
                {
                    txtMsg.setText("");
                    close();
                }
                else
                {
                    try 
                    {
                        String sentence = txtMsg.getText();
                        txtComm.setText(txtComm.getText() + 
                                "Client: " + sentence+ "\n");
                        txtMsg.setText("");
                        output.writeBytes(sentence + '\n');
                        String modifiedSentence = input.readLine();
                        txtComm.setText(txtComm.getText() + "Server: " + 
                                modifiedSentence + "\n");
                    }
                    catch (IOException ex) 
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void close()
    {
        try {
            s.close();
            input.close();
            output.close();
            btnConnect.setText("Connect");
            txtComm.setText(txtComm.getText() + "Disconnected!\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblPort;
    private javax.swing.JTextArea txtComm;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtMsg;
    private javax.swing.JTextField txtPort;
    // End of variables declaration//GEN-END:variables
}
