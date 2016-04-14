package com.Natalman;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

/**
 * Created by TheKingNat on 4/7/16.
 */
public class GuiTicket extends JFrame {
    private JPanel rootPanel;
    private JLabel DescriptionLabel;
    private JTextField ProblemTextField;
    private JTextField ConsuterTextField;
    private JLabel ConsulterLabel;
    private JLabel PriorityLabel;
    private JComboBox PriorityCombobox;
    private JButton AddTicketButton;
    private JList SubmitList;
    private JButton deleteTciketButton;
    private JLabel AddLabel;
    private JLabel DeleteLabel;
    private JLabel selectionLabel;
    private JButton QuitButton;
    private Date ReportedDate;

    ArrayList<String> ResolvedTic = new ArrayList<String>();


    private static int staticTicketIDCounter = 1;

    private DefaultListModel<String> listModel;

    protected GuiTicket() throws IOException{
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));

        listModel = new DefaultListModel<String>();
        SubmitList.setModel(listModel);

        ConfigurPriority();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        AddTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = ProblemTextField.getText();
                description = description.trim();

                String consulter = ConsuterTextField.getText();

                ReportedDate = new Date();
                int TicketID = staticTicketIDCounter++;

                consulter = consulter.trim();

                String Priority = (String)PriorityCombobox.getSelectedItem();

                if (description.length() == 0 || consulter.length() == 0){
                    return;
                }

                listModel.addElement( "TicketID: " + TicketID + "; Problem: " + description + "; Reporter: " + consulter + "; Priority: "
                        + Priority + "; Reported Date: " + ReportedDate);

                ProblemTextField.setText("");
                ConsuterTextField.setText("");



            }
        });


        deleteTciketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = SubmitList.getSelectedIndex();
                if (selectedIndex != 1){
                    String delectedTicket = listModel.remove(selectedIndex);

                    ResolvedTic.add("Resolved Ticket: " + delectedTicket);

                    try {
                        FileWriter resolved = new FileWriter("Resolvedticket.txt");
                        BufferedWriter ResolvedOnes = new BufferedWriter(resolved);

                        ResolvedOnes.write(ResolvedTic.toString() + "\n");

                        ResolvedOnes.close();
                    }
                    catch (IOException EI){
                        System.out.println("");
                    }

                }
            }
        });
        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    FileWriter ticket = new FileWriter("myTicket.txt");
                    BufferedWriter ResTicket = new BufferedWriter(ticket);
                    ResTicket.write(listModel.toString() + "\n");
                    ResTicket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                System.exit(0);

            }
        });

    }
    private void ConfigurPriority(){
        for (int x = 1; x <= 5; x++){
            if (x==1){
                PriorityCombobox.addItem(x + " very Low priority");
            }else if (x == 2){
                PriorityCombobox.addItem(x + " Low Priority");
            }else if(x == 3){
                PriorityCombobox.addItem(x + " Medium Priority");
            }else if (x == 4){
                PriorityCombobox.addItem(x + " High Priority");
            }else if (x==5){
                PriorityCombobox.addItem(x + " Very High Priority");
            }
        }
    }

}
