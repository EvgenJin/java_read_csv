/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evg.csv;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class main extends JFrame {
//    JTextField table_name_label;
    String full_path;
    
    public main() {
        super("Тестовое окно");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        
        final JLabel label_table = new JLabel("Table name");
        final JTextField table_name_label = new JTextField(15);
        
        JButton button_start = new JButton("START");
               
        final JLabel label = new JLabel("Selected CSV");
        label.setAlignmentX(CENTER_ALIGNMENT);
 
        JButton button = new JButton("Select CSV");
        button.setAlignmentX(CENTER_ALIGNMENT);
 
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();             
                int ret = fileopen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    label.setText(file.getName());
                    full_path = file.getAbsolutePath();
                }
            }
        });
        
        button_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println(table_name_label.getText());
                System.err.println(full_path);
                try {
                    CSVReader.generate_sql(table_name_label.getText(), full_path);
                } catch (Exception ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
 
        panel.add(label_table);
        panel.add(table_name_label);        
        panel.add(label);        
        panel.add(button);
        panel.add(button_start);
//        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);
 
        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new main();
            }
        });
    }
}