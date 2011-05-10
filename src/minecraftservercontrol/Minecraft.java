/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Minecraft.java
 *
 * Created on Mar 27, 2011, 12:17:47 PM
 */

package minecraftservercontrol;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Philipp Sura
 */

public class Minecraft extends javax.swing.JFrame {

    BufferedWriter toServer = null;
    BufferedReader fromServer = null;
    PipedOutputStream pos = null;
    JSch jsch = null;
    Session session = null;
    Channel channel = null;
    boolean connected = false, get_dirs = false, ok = true, list = false, clear = true, bukkit = false, check_folders = false, add_world = false;
    List<String> treffer = new ArrayList<String>();
    public List<String> player_name = new ArrayList<String>();
    public List<String> player_ip = new ArrayList<String>();
    DefaultListModel dlm = new DefaultListModel();
    String[] compl_arr = {};
    String[] dirs = {};
    String[] settings = new String[7];
    int x = 0, position = 0, online_players = 0, port = 22, ram = 1024;
    String ip = "", username = "", password = "", path = "", directories = "", last_world = ""/*, server_prop = ""*/;
    Connect_frame connect = new Connect_frame(this);
    Commands command = new Commands(this);
    DefaultTableModel model = new DefaultTableModel();

    /** Creates new form Minecraft */
    public Minecraft() {
        initComponents();
        
        try { FileWriter fstream = new FileWriter("./log.txt");/*Clear logfile*/ } catch (Exception e) { System.out.println("Exception in write_logfile()@START...\n" + e); }

        write_logfile("Initializing program...\nReading items...");
        read_items();
        write_logfile("Reading settings...");
        read_settings();
        worlds.removeAllItems();
        write_logfile("Initializing clock...");
        new Thread(new time(), "time").start();
        command.names_ips.setModel(model);
        model.addColumn("Names"); model.addColumn("IPs");

        give_amount_sl.addChangeListener(new ChangeListener(){
          public void stateChanged(ChangeEvent e){
            sl_value.setText(Integer.toString(give_amount_sl.getValue()));
          }
        });

        Toolkit.getDefaultToolkit().getSystemEventQueue().push(
            new EventQueue() {
                @Override
                protected void dispatchEvent(AWTEvent event) {
                    if (event instanceof KeyEvent) {

                        KeyEvent keyEvent = (KeyEvent) event;

                        //if (KeyEvent.KEY_RELEASED == keyEvent.getID())
                        if (KeyEvent.KEY_PRESSED == keyEvent.getID() && KeyEvent.VK_ENTER == keyEvent.getKeyCode() && connected) {
                            String item = "";
                            StringBuilder itembuffer = new StringBuilder();
                            if(!items.isSelectionEmpty()) {
                                itembuffer.delete(0, itembuffer.length());
                                item = items.getSelectedValue().toString();
                                for(int x = 0; x < 4; x++) {
                                    if(item.charAt(x) != ' ') itembuffer.append(item.charAt(x));
                                    else break;
                                }
                                move_to_top(item, items.getSelectedIndex());
                            }
                            action("give " + player_give.getSelectedItem().toString() + " " + itembuffer.toString() + " " + give_amount_sl.getValue());
                        }
                    }
                    super.dispatchEvent(event);
                }
            }
        );

        search_item.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void removeUpdate(DocumentEvent e) {
                if(clear) {
                    dlm.clear();
                    if(e.getOffset() == 0) read_items();
                }
            }
            public void insertUpdate(DocumentEvent e) {
                if(clear) {
                    clear = false;
                    dlm.clear();
                }
                position = e.getOffset();
                if (position == 0) return;
                SwingUtilities.invokeLater(new CompletionTask(position + 1));
            }
        });
    }


    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        start_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        stop_btn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        restart_btn = new javax.swing.JButton();
        time_vals = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        player_1_tp = new javax.swing.JComboBox();
        set_time_btn = new javax.swing.JButton();
        player_2_tp = new javax.swing.JComboBox();
        TP_btn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        player_give = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        give_amount_sl = new javax.swing.JSlider();
        give_btn = new javax.swing.JButton();
        search_item = new javax.swing.JTextField();
        cur_time = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        items = new javax.swing.JList();
        clear_log = new javax.swing.JButton();
        worlds = new javax.swing.JComboBox();
        switch_btn = new javax.swing.JButton();
        players_online_label = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sl_value = new javax.swing.JLabel();
        about_btn = new javax.swing.JButton();
        jar_file = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        commands_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        start_btn.setText("Connect");
        start_btn.setToolTipText("Connect to the server, then start the minecraft server program");
        start_btn.setFocusable(false);
        start_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_btnActionPerformed(evt);
            }
        });

        log.setColumns(20);
        log.setEditable(false);
        log.setRows(5);
        log.setToolTipText("");
        log.setBorder(null);
        log.setFocusable(false);
        jScrollPane1.setViewportView(log);

        stop_btn.setText("Stop Server");
        stop_btn.setToolTipText("Stops the minecraft server");
        stop_btn.setEnabled(false);
        stop_btn.setFocusable(false);
        stop_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stop_btnActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 2, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Minecraft Server Control v1.0");

        restart_btn.setText("Restart Server");
        restart_btn.setToolTipText("Press here to restart the minecraft server program on the server side");
        restart_btn.setEnabled(false);
        restart_btn.setFocusable(false);
        restart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restart_btnActionPerformed(evt);
            }
        });

        time_vals.setMaximumRowCount(24);
        time_vals.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 h", "2 h", "3 h", "4 h", "5 h", "6 h", "7 h", "8 h", "9 h", "10 h", "11 h", "12 h", "13 h", "14 h", "15 h", "16 h", "17 h", "18 h", "19 h", "20 h", "21 h", "22 h", "23 h", "24 h" }));
        time_vals.setToolTipText("Select the time to set in the game");
        time_vals.setEnabled(false);
        time_vals.setFocusable(false);

        jLabel6.setText("Time:");

        player_1_tp.setMaximumRowCount(15);
        player_1_tp.setToolTipText("Teleport this player ...");
        player_1_tp.setEnabled(false);
        player_1_tp.setFocusable(false);

        set_time_btn.setText("Set");
        set_time_btn.setToolTipText("Set the time selected from the drop-down menu on the left");
        set_time_btn.setEnabled(false);
        set_time_btn.setFocusable(false);
        set_time_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_time_btnActionPerformed(evt);
            }
        });

        player_2_tp.setMaximumRowCount(15);
        player_2_tp.setToolTipText("... to this player");
        player_2_tp.setEnabled(false);
        player_2_tp.setFocusable(false);

        TP_btn.setText("Teleport");
        TP_btn.setToolTipText("Teleports Player 1 to Player 2");
        TP_btn.setEnabled(false);
        TP_btn.setFocusable(false);
        TP_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TP_btnActionPerformed(evt);
            }
        });

        jLabel4.setText("Player:");

        player_give.setMaximumRowCount(15);
        player_give.setEnabled(false);
        player_give.setFocusable(false);

        jLabel5.setText("Amount:");

        give_amount_sl.setMaximum(64);
        give_amount_sl.setMinimum(1);
        give_amount_sl.setValue(64);
        give_amount_sl.setFocusable(false);

        give_btn.setText("Give");
        give_btn.setToolTipText("1) Select an item from the list above\n2) Select a player from the drop-down menu on the left\n3) Set the amount\n4) Press this button to give the amount of the item to the player");
        give_btn.setEnabled(false);
        give_btn.setFocusable(false);
        give_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                give_btnActionPerformed(evt);
            }
        });

        search_item.setToolTipText("Search in here for an item from the itemlist above");
        search_item.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                search_itemFocusGained(evt);
            }
        });

        cur_time.setText("00:00:00");
        cur_time.setToolTipText("Current System Time");

        items.setToolTipText("");
        jScrollPane2.setViewportView(items);

        clear_log.setText("Clear Log");
        clear_log.setToolTipText("Clear the logwindow above");
        clear_log.setFocusable(false);
        clear_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_logActionPerformed(evt);
            }
        });

        worlds.setToolTipText("Holds the available worlds to play");
        worlds.setEnabled(false);

        switch_btn.setText("switch");
        switch_btn.setToolTipText("Switch the selected players from the drop-down menus");
        switch_btn.setEnabled(false);
        switch_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch_btnActionPerformed(evt);
            }
        });

        players_online_label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        players_online_label.setText("0");
        players_online_label.setToolTipText("Number of players connected to the server");

        jLabel1.setText("Online Players:");

        sl_value.setText("64");
        sl_value.setToolTipText("Amount");

        about_btn.setText("About");
        about_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                about_btnActionPerformed(evt);
            }
        });

        jar_file.setText("minecraft_server.jar");
        jar_file.setToolTipText("The name of the java executable file");

        jLabel2.setText("JAR file");

        commands_btn.setText("Commands");
        commands_btn.setEnabled(false);
        commands_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commands_btnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(cur_time)
                        .add(18, 18, 18)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(players_online_label, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 400, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel4))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(player_give, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(give_amount_sl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sl_value)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(give_btn))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(start_btn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(stop_btn)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(restart_btn)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(worlds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(layout.createSequentialGroup()
                                        .add(player_1_tp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(switch_btn)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(player_2_tp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                        .add(TP_btn))
                                    .add(layout.createSequentialGroup()
                                        .add(jLabel6)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(time_vals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(set_time_btn)))
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(37, 37, 37)
                                        .add(jar_file, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 172, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                        .add(17, 17, 17)
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                            .add(commands_btn)
                                            .add(layout.createSequentialGroup()
                                                .add(clear_log)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(about_btn)))
                                        .add(1, 1, 1))))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 826, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(search_item, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 15, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(start_btn)
                            .add(stop_btn)
                            .add(restart_btn)
                            .add(worlds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(about_btn)
                            .add(clear_log))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(player_1_tp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(player_2_tp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(switch_btn)
                            .add(TP_btn)
                            .add(commands_btn)))
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel6)
                        .add(time_vals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(set_time_btn))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(search_item, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2)
                        .add(jar_file, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(give_btn)
                        .add(sl_value))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel5)
                        .add(player_give, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel4))
                    .add(give_amount_sl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(cur_time)
                        .add(jLabel1)
                        .add(players_online_label)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void start_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_btnActionPerformed
        if(!connected) {
            ok = true;
            connect.setVisible(true);
        }
        else {
            start_btn.setEnabled(false);
            worlds.setEnabled(false);
            stop_btn.setEnabled(true);
            give_btn.setEnabled(true);
            restart_btn.setEnabled(true);
            set_time_btn.setEnabled(true);
            give_amount_sl.setEnabled(true);
            time_vals.setEnabled(true);
            //buttons im command fenster enablen
            command.help_btn.setEnabled(true);
            command.saveall_btn.setEnabled(true);
            command.kick_btn.setEnabled(true);
            command.ban_btn.setEnabled(true);
            command.banip_btn.setEnabled(true);
            command.pardonip_btn.setEnabled(true);
            command.op_btn.setEnabled(true);
            command.deop_btn.setEnabled(true);
            
            settings[5] = worlds.getSelectedItem().toString();
            settings[6] = jar_file.getText();
            try {
                FileWriter fstream = new FileWriter("./settings.txt");
                BufferedWriter out = new BufferedWriter(fstream);
                x = 0;
                while(x <= 6) {
                    out.write(settings[x] + "\n");
                    x++;
                }
                out.close();
            }catch (Exception e) { System.out.println("Exception in connectActionPerformed - write settingsfile\n" + e); }

            action("cd " + worlds.getSelectedItem().toString());
            action("java -jar -Xmx" + ram + "M -Xms" + ram + "M ../" + jar_file.getText());
            new Thread(new timer(), "timer").start();
        }
    }//GEN-LAST:event_start_btnActionPerformed

    private void stop_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stop_btnActionPerformed
        start_btn.setEnabled(true);
        stop_btn.setEnabled(false);
        give_btn.setEnabled(false);
        restart_btn.setEnabled(false);
        TP_btn.setEnabled(false);
        set_time_btn.setEnabled(false);
        player_1_tp.setEnabled(false);
        player_2_tp.setEnabled(false);
        player_give.setEnabled(false);
        give_amount_sl.setEnabled(false);
        switch_btn.setEnabled(false);
        time_vals.setEnabled(false);
        commands_btn.setEnabled(false);
        
        command.help_btn.setEnabled(false);
        command.saveall_btn.setEnabled(false);
        command.kick_btn.setEnabled(false);
        command.ban_btn.setEnabled(false);
        command.banip_btn.setEnabled(false);
        command.pardonip_btn.setEnabled(false);
        command.op_btn.setEnabled(false);
        command.deop_btn.setEnabled(false);
        command.send_btn.setEnabled(false);
            
        worlds.removeAllItems();
        worlds.setEnabled(false);
        player_1_tp.removeAllItems();
        player_2_tp.removeAllItems();
        player_give.removeAllItems();
        players_online_label.setText("0");
        start_btn.setText("Connect");

        action("stop");
        writelog("\n         --------------------------------------------------------------------------------\n");

        connected = false;
        channel.disconnect();
        channel = null;
        session.disconnect();
        session = null;
        try { jsch.removeAllIdentity(); } catch (JSchException ex) { System.out.println("stop_btnActionPerformed: " + ex); }
        jsch = null;
    }//GEN-LAST:event_stop_btnActionPerformed

    private void restart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restart_btnActionPerformed
        action("stop");
        player_1_tp.removeAllItems();
        player_2_tp.removeAllItems();
        player_give.removeAllItems();
        writelog("\n         --------------------------------------------------------------------------------\n");
        try {Thread.sleep(750);} catch (Exception e) {}
        action("java -jar -Xmx" + ram + "M -Xms" + ram + "M ../" + jar_file.getText());
    }//GEN-LAST:event_restart_btnActionPerformed

    private void give_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_give_btnActionPerformed
        String item = "";
        StringBuilder itembuffer = new StringBuilder();
        if(!items.isSelectionEmpty()) {
            itembuffer.delete(0, itembuffer.length());
            item = items.getSelectedValue().toString();
            for(int x = 0; x < 4; x++) {
                if(item.charAt(x) != ' ') itembuffer.append(item.charAt(x));
                else break;
            }
            move_to_top(item, items.getSelectedIndex());
        }
        action("give " + player_give.getSelectedItem().toString() + " " + itembuffer.toString() + " " + give_amount_sl.getValue());
}//GEN-LAST:event_give_btnActionPerformed

    private void TP_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TP_btnActionPerformed
        action("tp " + player_1_tp.getSelectedItem().toString() + " " + player_2_tp.getSelectedItem().toString());
    }//GEN-LAST:event_TP_btnActionPerformed

    private void set_time_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set_time_btnActionPerformed
        action("time set " + Integer.parseInt(time_vals.getSelectedItem().toString().replaceAll(" h", ""))*1000);
    }//GEN-LAST:event_set_time_btnActionPerformed

    private void search_itemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search_itemFocusGained
        search_item.selectAll();
    }//GEN-LAST:event_search_itemFocusGained

    private void clear_logActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_logActionPerformed
        log.setText("");
        write_logfile("\nClear log performed\n");
    }//GEN-LAST:event_clear_logActionPerformed

    private void switch_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch_btnActionPerformed
        int temp = player_1_tp.getSelectedIndex();
        player_1_tp.setSelectedIndex(player_2_tp.getSelectedIndex());
        player_2_tp.setSelectedIndex(temp);
        write_logfile("Switched players: " + player_1_tp.getSelectedItem().toString() + " <--> " + player_2_tp.getSelectedItem().toString() + "  @ TP");
    }//GEN-LAST:event_switch_btnActionPerformed

    private void about_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_about_btnActionPerformed
        JTextArea licensearea = new JTextArea(licensetext);
        licensearea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(licensearea);
        scrollPane.setPreferredSize(new Dimension(650,600));
        Object message = scrollPane;
        JOptionPane.showMessageDialog(null, message, "License", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_about_btnActionPerformed

    private void commands_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commands_btnActionPerformed
        command.setVisible(true);
    }//GEN-LAST:event_commands_btnActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Minecraft().setVisible(true);
            }
        });
    }
    
    public void connect() {
        ip = connect.ip_login.getText();
        username = connect.username_login.getText();
        port = Integer.parseInt(connect.port_login.getText());
        password = connect.pw_login.getText();
        path = connect.path_login.getText();
        ram = Integer.parseInt(connect.ram_login.getText());
        if(!path.endsWith("/")) path += "/";
        settings[0] = ip; settings[1] = username; settings[2] = Integer.toString(port); settings[3] = path; settings[4] = Integer.toString(ram);
        
        action("");
        if(connected) {
            writelog("Searching for worlds...");
            action("cd " + path);
            action("ls -d */");
            get_dirs = true;
            try {Thread.sleep(1000);} catch (Exception e) {}
            dirs = directories.split("\n");

            if(dirs.length > 0) {
                worlds.setEnabled(true);
                //prop_btn.setEnabled(true);
                check_folders = true;
                x = 0;
                while(x < dirs.length) {
                    dirs[x] = dirs[x].trim();
                    action("ls ./" + dirs[x].replaceAll(" ", "\\\\ "));
                    try { Thread.sleep(500); } catch (Exception e) {}
                    x++;
                }
                action("ls ./");
                try { Thread.sleep(500); } catch (Exception e) {}
                check_folders = false;
                worlds.setSelectedItem(last_world); //die welt auswählen, die das letzte mal gespielt wurde
                start_btn.setText("Start Server");
            }
            else writelog("No worlds found!");
            command.send_btn.setEnabled(true);
            commands_btn.setEnabled(true);
        }
        else writelog("Couldn't connect to the Server! Check IP, port, username, password and your internet connection!");
    }

    public void action(String command) {
        try {
            if(connected) {
                send(command);
            }
            else {
              StringBuilder write_to_logfile = new StringBuilder();
              write_to_logfile.append("Connecting to ").append(ip).append(" via SSH at port ").append(port).append(" with username ").append(username).append("\n");
              jsch = new JSch();

              session = jsch.getSession(username, ip, port);
              session.setPassword(password);

              UserInfo ui = new MyUserInfo();
              session.setUserInfo(ui);
              session.setTimeout(10000); //10 Sekunden Zeit um zum Server zu verbinden, default-wert war zu lange
              write_to_logfile.append("ConnectionTimeout set to 10 seconds.\n");
              session.connect();
              write_to_logfile.append("SSH Serverversion: ").append(session.getServerVersion()).append("\n");

              channel = session.openChannel("shell");
              pos = new PipedOutputStream();
              channel.setInputStream(new PipedInputStream(pos));
              fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));
              toServer = new BufferedWriter(new OutputStreamWriter(pos, "utf-8"));
              write_to_logfile.append("Created streams.\n");
              channel.connect();
              connected = true;
              new Thread(new reading(), "reading").start();
              write_to_logfile.append("Listening...\n");
              write_logfile(write_to_logfile.toString());
              writelog("Session started.");
            }
        } catch (Exception e) {
            connected = false;
            writelog(e.toString());
            System.out.println("connect-class: " + e);
        }
    }
    
    public void send(String command) throws IOException {
         toServer.write(command + "\n");
         toServer.flush();
         write_logfile("Sent to server: " + command);
     }

    public class time implements Runnable { //Zum Anzeigen der Uhrzeit
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        public void run() {
            while(true) {
                try { Thread.sleep(1000); } catch (Exception e) {System.out.println("TimeThread: " + e);}
                Calendar cal = Calendar.getInstance();
                cur_time.setText(sdf.format(cal.getTime()));
            }
        }
    }

    public class timer implements Runnable { //Zum Anzeigen der serveruptime
        short sec = 0;
        short min = 0;
        short hour = 0;

        public void run() {
            while(connected) {
                try { Thread.sleep(1000); } catch (Exception e) {}
                sec++;
                if(sec == 60) {
                    sec = 0;
                    min++;
                    if(min == 60) {
                        min = 0;
                        hour++;
                        if(hour == 24) hour = 0;
                    }
                }
                if(min%10 == 0 && sec == 0) {
                    if(hour == 0 && min != 0) writelog("\nCurrent serveruptime: " + min + " minutes\n"); //ersten 5x10minutes ausgeben
                    else if(hour == 1) writelog("\nCurrent serveruptime: " + hour + " hour " + min + " minutes\n"); //wenn erste stunde ist 5x10minutes ausgeben
                    else writelog("\nCurrent serveruptime: " + hour + " hours " + min + "minutes\n");
                }
            }
        }

    }

    public class reading implements Runnable {
        public void run() {
            StringBuilder reading = new StringBuilder();

            while(connected) {
                try {
                    reading.append(fromServer.readLine());
                    if(get_dirs && (reading.toString().contains("[0m") || reading.toString().contains("[33m") || reading.toString().contains("[1m") ||
                            reading.toString().contains("[32m") || reading.toString().contains("[34m") || reading.toString().contains("[01;34m"))) {
                        directories = reading.toString().replaceAll("\\[0m", "\n").replaceAll("\\[33m", "").replaceAll("\\[1m", "")
                        .replaceAll("\\[34m", "").replaceAll("\\[31m", "").replaceAll("\\[01;34m", "")/*.replaceAll("/", "")*/;
                        get_dirs = false;
                    }
                    else if(check_folders) {
                        // banned-ips.txt, banned-players.txt, ops.txt, server.properties, white-list.txt, world
                        if(reading.toString().contains("banned-ips.txt") || reading.toString().contains("banned-players.txt") || reading.toString().contains("ops.txt") ||
                           reading.toString().contains("server.properties") || reading.toString().contains("white-list.txt") || reading.toString().contains("world")) {
                                add_world = true;
                            }
                        else if(reading.toString().contains("ls ./") && add_world) {
                            worlds.addItem(dirs[x-1]);
                            add_world = false;
                        }
                    }
                    else if(reading.toString().startsWith("give ") || reading.toString().startsWith("tp ") || reading.toString().startsWith("[Lnet.minecraft.server.Statistic;@") ||
                            reading.toString().startsWith("[Lmp;@") || reading.toString().contains("cd " + path) || reading.toString().contains("ls -d */") ||
                            reading.toString().startsWith("null") || reading.toString().startsWith(">") ||
                            (reading.toString().contains("cd " + worlds.getSelectedItem()) && worlds.getItemCount() > 0)
                            ) { write_logfile("Not written to log: " + reading.toString()); }
                    else {
                        writelog(reading.toString());
                        log.setCaretPosition(log.getText().length());
                        if(reading.toString().contains("logged in with entity")) filter_name(reading, true);
                        else if(reading.toString().contains("lost connection") || reading.toString().contains("was kicked for floating too long!")) filter_name(reading, false);
                        else if(reading.toString().contains("craftbukkit") || reading.toString().contains("Craftbukkit") ||
                                reading.toString().contains("bukkit") || reading.toString().contains("Bukkit")) { bukkit = true; }
                    }
                    reading.delete(0, reading.length());
                } catch (Exception e) { System.out.println("if(other) in reading Thread: " + e); }
            }
        }
    }

    public void filter_name(StringBuilder reading, boolean status) {

        /**
         * BEISPIEL:
         *          27 Zeichen                 50 Zeichen: IP(min:1.1.1.1) und fixer Teil (':' + 5 stellen Port und logged in... bis zur ID-Nummer)
         *  -------------------------          ---------------------------------------------
         * |                         |        |                                             |
         * 2011-04-25 12:43:12 [INFO] desibug [/192.168.1.1:51304] logged in with entity id 192 at (-52.5, 88.62000000476837, 58.5)
         * 2011-04-01 16:55:00 [INFO] desibug lost connection: disconnect.quitting
         * 2011-04-25 12:57:21 [WARNING] desibug was kicked for floating too long!
         * 
         * BUKKIT:
         * 10:29:16 [INFO] Cleclsecht [/91.141.12.14:37385] logged in with entity id 128
         * 10:29:23 [INFO] Cleclsecht lost connection: disconnect.quitting
         */

        StringBuilder name = new StringBuilder();
        StringBuilder ip_address = new StringBuilder();
        int bis = 0, bis_ip = 0;
        
        
        if(status && !bukkit) { //@login wenn kein bukkit erkannt wurde
            for(x = 27; x < reading.length(); x++) { //27 vom start nach vorne bis zum SPACE vor dem ersten Buchstaben des Namen,
                if(reading.charAt(x) == '/') {
                        bis = x-2;
                        break;
                }
            }
            for(x = bis+3; x < reading.length(); x++) { //bis zum ":" nach der IP-adresse
                if(reading.charAt(x) == ':') {
                        bis_ip = x;
                        break;
                }
            }
            for(x = 27; x < bis; x++) { //namen speichern
                name.append(reading.charAt(x));
            }
            for(x = bis+3; x < bis_ip; x++) { //namen speichern
                ip_address.append(reading.charAt(x));
            }
            player_name.add(name.toString());
            player_ip.add(ip_address.toString());

            player_1_tp.addItem(name.toString());
            player_2_tp.addItem(name.toString());
            player_give.addItem(name.toString());
            player_1_tp.setEnabled(true);
            player_give.setEnabled(true);

            online_players++;
            if(online_players > 1) {
                player_2_tp.setEnabled(true);
                switch_btn.setEnabled(true);
                TP_btn.setEnabled(true);
            }
            if(online_players == 2) player_2_tp.setSelectedIndex(1);
            
            //namen und IPs zur table in Commands hinzufügen
            String[] values = new String[]{name.toString(), ip_address.toString()};
            model.addRow(values);
        }
        else if(status && bukkit) { //bukkit erkannt
            for(x = 16; x < reading.length(); x++) { //27 vom start nach vorne bis zum SPACE vor dem ersten Buchstaben des Namen,
                if(reading.charAt(x) == '/') {
                        bis = x-2;
                        break;
                }
            }
            for(x = bis+3; x < reading.length(); x++) { //bis zum ":" nach der IP-adresse
                if(reading.charAt(x) == ':') {
                        bis_ip = x;
                        break;
                }
            }
            for(x = 16; x < bis; x++) {
                name.append(reading.charAt(x));
            }
            for(x = bis+3; x < bis_ip; x++) { //namen speichern
                ip_address.append(reading.charAt(x));
            }
            player_name.add(name.toString());
            player_ip.add(ip_address.toString());

            player_1_tp.addItem(name.toString());
            player_2_tp.addItem(name.toString());
            player_give.addItem(name.toString());
            player_1_tp.setEnabled(true);
            player_give.setEnabled(true);

            online_players++;
            if(online_players > 1) {
                player_2_tp.setEnabled(true);
                switch_btn.setEnabled(true);
                TP_btn.setEnabled(true);
                player_2_tp.setSelectedIndex(online_players-1);
            }
            
            //namen und IPs zur table in Commands hinzufügen
            String[] values = new String[]{name.toString(), ip_address.toString()};
            model.addRow(values);
        }
        else if(!status && online_players >= 1) { //@logout
            for(x = 0; x < player_1_tp.getItemCount(); x++) {
                if(reading.toString().contains(player_1_tp.getItemAt(x).toString())) {
                    player_1_tp.removeItemAt(x);
                    player_2_tp.removeItemAt(x);
                    player_give.removeItemAt(x);

                    player_name.remove(x);
                    player_ip.remove(x);
                    model.removeRow(x);
                    
                    online_players--;
                    if(online_players == 1) {
                        switch_btn.setEnabled(false);
                        player_2_tp.setEnabled(false);
                        TP_btn.setEnabled(false);
                    }
                    break;
                }
            }
            
            if(online_players == 0) {
                player_1_tp.setEnabled(false);
                player_2_tp.setEnabled(false);
                player_give.setEnabled(false);
                switch_btn.setEnabled(false);
                TP_btn.setEnabled(false);
            }
        }
        players_online_label.setText(Integer.toString(online_players));
    }
    
    private static class MyUserInfo implements UserInfo {
         public void setPassword() {}
         public String getPassphrase() {return null;}
         public String getPassword() {return null;}
         public boolean promptPassword(String arg0) {return true;}
         public boolean promptPassphrase(String arg0) {return true;}
         public boolean promptYesNo(String arg0) {return true;}
         public void showMessage(String arg0) {System.out.println(arg0);}
     }
 
    public class CompletionTask implements Runnable {
        int position;
        String prefix = search_item.getText();
        String item = "";
 
        CompletionTask(int position) {
            this.position = position;
        }
 
        public void run() {
            int n = 0, count = 0;
            while(n < treffer.size()) {
                if(treffer.get(n).contains(prefix)) {
                    item = treffer.get(n);
                    dlm.addElement(item);
                }
                n++;
            }
            clear = false;
            if(dlm.get(0).toString().charAt(0) == search_item.getText().charAt(0)) { //Bei Zahleneingabe
                search_item.setText(dlm.get(0).toString());
                search_item.setCaretPosition(position + 1);
            }
            else { //Bei Buchstabeneingabe
                for(count = 0; count < 5; count++) { if(dlm.get(0).toString().charAt(count) == ' ') break; }
                search_item.setText(dlm.get(0).toString().substring(count + 1, dlm.get(0).toString().length()));
                search_item.setCaretPosition(position);
            }
            clear = true;
            search_item.moveCaretPosition(search_item.getText().length());
            items.setSelectedIndex(0);
        }
    }

    public void writelog(String message) {
        log.append(message + "\n");
        write_logfile(message);
    }

    public void read_items () {
        File file = new File("./items.txt");
        x = 0;
        treffer.clear();
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            String inhalt = "";
            while(true) {
                inhalt = dis.readLine();
                if(inhalt==null) break;
                dlm.insertElementAt(inhalt, x);
                treffer.add(inhalt);
                x++;
            }
            items.setModel(dlm);
            Collections.sort(treffer);
            if(log.getText().isEmpty()) writelog("Currently are " + dlm.size() + " items available!");
        }catch (Exception e) { System.out.println("Exception in read_items()!\n" + e); }
    }

    public void read_settings () {
        File file = new File("./settings.txt");
        
        x = 0;
        while(x <= 6) { //Arrayinitialisierung
            settings[x] = "";
            x++;
        }
        
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            String inhalt = ""; x = 0;
            while(true) {
                inhalt = dis.readLine();
                if(inhalt==null) break;
                settings[x] = inhalt;
                x++;
            }
        }catch (Exception e) { System.out.println("Exception in read_settings()!\n" + e); }
        
        if(!settings[0].toString().isEmpty()) {
            connect.ip_login.setText(settings[0]);
            connect.username_login.setText(settings[1]);
            connect.port_login.setText(settings[2]);
            connect.path_login.setText(settings[3]);
            connect.ram_login.setText(settings[4]);
            last_world = settings[5];
            jar_file.setText(settings[6].toString());
        }
    }

    public void write_logfile(String message) {
        try {
            FileWriter fstream = new FileWriter("./log.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(message + "\n");
            out.close();
        }catch (Exception e) { System.out.println("Exception in write_logfile()!\n" + e); }
    }

    public void move_to_top(String item, int index) {
        dlm.insertElementAt(item, 0);
        dlm.remove(index+1);
        items.setModel(dlm);
        items.setSelectedIndex(0);
        treffer.add(0, item);
        Collections.sort(treffer);
    }
    
    /*public void server_prop() {
        /**
         * #Minecraft server properties
         * #Thu Apr 21 20:40:57 CEST 2011
         * level-name=world
         * hellworld=false
         * spawn-monsters=true
         * online-mode=true
         * spawn-animals=true
         * max-players=20
         * server-ip=
         * pvp=true
         * level-seed=
         * server-port=25565
         * allow-flight=true
         * white-list=false
         * spawn-protection=16
         //
        action("cat ./" + worlds.getSelectedItem() + "/server.properties");
        
        
        //action("rm server.properties");//altes server.properties file löschen
        //action("touch server.properties");//neues server.properties file erstellen
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton TP_btn;
    private javax.swing.JButton about_btn;
    private javax.swing.JButton clear_log;
    private javax.swing.JButton commands_btn;
    private javax.swing.JLabel cur_time;
    private javax.swing.JSlider give_amount_sl;
    private javax.swing.JButton give_btn;
    private javax.swing.JList items;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jar_file;
    private javax.swing.JTextArea log;
    private javax.swing.JComboBox player_1_tp;
    private javax.swing.JComboBox player_2_tp;
    private javax.swing.JComboBox player_give;
    private javax.swing.JLabel players_online_label;
    private javax.swing.JButton restart_btn;
    public javax.swing.JTextField search_item;
    private javax.swing.JButton set_time_btn;
    private javax.swing.JLabel sl_value;
    private javax.swing.JButton start_btn;
    private javax.swing.JButton stop_btn;
    private javax.swing.JButton switch_btn;
    private javax.swing.JComboBox time_vals;
    private javax.swing.JComboBox worlds;
    // End of variables declaration//GEN-END:variables

    String licensetext = 
              "\nCopyright (C) 2011 Philipp Sura"
            + "\nAll rights reserved."
            + "\n"
            + "\nRedistribution in binary forms without modification is permitted,"
            + "\n"
            + "provided that the following condition is met:"
            + "\n"
            + "\n- Redistributions in binary form must reproduce the above copyright "
            + "\n"
            + " notice, this list of conditions and the following disclaimer in "
            + "\n the documentation and/or other materials provided with the distribution."
            + "\n"
            + "\nTHIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,"
            + "\nINCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND"
            + "\nFITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE"
            + "\nAUTHOR OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,"
            + "\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT"
            + "\nLIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,"
            + "\nOR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF"
            + "\nLIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING"
            + "\nNEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,"
            + "\nEVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."
            + "\n       "
            + "\n        "
            + "\nJSch 0.0.* was released under the GNU LGPL license.  Later, we have switched "
            + "\nover to a BSD-style license. "
            + "\n"
            + "\n------------------------------------------------------------------------------"
            + "\nCopyright (c) 2002-2010 Atsuhiko Yamanaka, JCraft,Inc. "
            + "\nAll rights reserved."
            + "\n"
            + "\nRedistribution and use in source and binary forms, with or without"
            + "\nmodification, are permitted provided that the following conditions are met:"
            + "\n"
            + "\n  1. Redistributions of source code must retain the above copyright notice,"
            + "\n     this list of conditions and the following disclaimer."
            + "\n"
            + "\n  2. Redistributions in binary form must reproduce the above copyright "
            + "\n     notice, this list of conditions and the following disclaimer in "
            + "\n     the documentation and/or other materials provided with the distribution."
            + "\n"
            + "\n  3. The names of the authors may not be used to endorse or promote products"
            + "\n     derived from this software without specific prior written permission."
            + "\n"
            + "\nTHIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,"
            + "\nINCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND"
            + "\nFITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,"
            + "\nINC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,"
            + "\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT"
            + "\nLIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,"
            + "\nOR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF"
            + "\nLIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING"
            + "\nNEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,"
            + "\nEVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n\n\n";
}
