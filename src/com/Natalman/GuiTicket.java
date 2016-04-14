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
    private JList ResolvedList;
    private JLabel NonLabel;
    private Date ReportedDate;

    ArrayList<String> ResolvedTic = new ArrayList<String>(); // array list that will save resoved ticket


    private static int staticTicketIDCounter = 1; // Counter for the ticket ID

    private DefaultListModel<String> listModel;
    private DefaultListModel<String> listModel2;


    // Setting up the GUI

    protected GuiTicket() throws IOException{
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(500, 500));

        listModel = new DefaultListModel<String>();   //Initializing the Jlist of current ticket
        SubmitList.setModel(listModel);

        listModel2 = new DefaultListModel<String>();   //Initializing the Jlist of resolved ticket
        ResolvedList.setModel(listModel2);


        ConfigurPriority();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        AddTicketButton.addActionListener(new ActionListener() {   // This will help us to add our component to the current ticket list
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = ProblemTextField.getText();
                description = description.trim();

                String consulter = ConsuterTextField.getText();

                ReportedDate = new Date();                    // Initializing date
                int TicketID = staticTicketIDCounter++;

                consulter = consulter.trim();

                String Priority = (String)PriorityCombobox.getSelectedItem();

                if (description.length() == 0 || consulter.length() == 0){
                    return;
                }


                // Adding components to the current list of ticket

                listModel.addElement( "TicketID: " + TicketID + "; Problem: " + description + "; Reporter: " + consulter + "; Priority: "
                        + Priority + "; Reported Date: " + ReportedDate);

                ProblemTextField.setText("");
                ConsuterTextField.setText("");



            }
        });



        //Deleting ticket from the current list, adding them to the resolved list, and to a text file
        deleteTciketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = SubmitList.getSelectedIndex();
                if (selectedIndex != 1){
                    String delectedTicket = listModel.remove(selectedIndex); //Deleting ticket

                    listModel2.addElement(listModel.remove(selectedIndex)); //Adding ticket to the resolved list


                    ResolvedTic.add("Resolved Ticket: " + delectedTicket);


                    //Adding deleting ticket to a text file
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

        //Quiting the program and saving current ticket to a txt file
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

    // Creating a priority selecting box that helps the user to select the priority of the ticket.
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
