package org.example.View;

import javax.swing.*;
import java.awt.*;
public class SimulationFrame extends View {
    private JButton buttonAvrWaitingTime;
    private JButton buttonAvrServiceTime;
    private JButton buttonPeak;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JRadioButton buttonTimeStrategy;
    private JRadioButton buttonQueueStrategy;
    private JButton startButton;
    private String string;
    private JTextArea textArea;
    public SimulationFrame() {
        this.setTitle("Server-Clients");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(275, 10); //set frame location in the center of the screen
        this.setSize(1000, 800);
        this.setLayout(new BorderLayout());

        this.getContentPane().setBackground(Color.getColor("F5F5F5"));

        JPanel panelNorth = createPanelNorth();
        JPanel panelCenter = createPanelCenter();
        JPanel panelSouth = createPanelSouth();

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public JPanel createPanelSouth() {
        //Aceasta metoda suprascrie metoda din clasa parinte, si implementeaza butonul clear
        JPanel clear = new JPanel();
        clear.setLayout(new FlowLayout(FlowLayout.CENTER));
        startButton = newButton("START");
        clear.add(startButton);
        return clear;
    }

    public JPanel createPanelNorth() {
        buttonAvrWaitingTime = newButton("Average waiting time");
        buttonAvrServiceTime = newButton("Average service time");
        buttonPeak = newButton("Peak");

        JLabel labelTextField1 = new JLabel("Clients number");
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(100, 30));
        textField1.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField2 = new JLabel("Min processing time");
        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(100, 30));
        textField2.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField3 = new JLabel("Max processing time");
        textField3 = new JTextField();
        textField3.setPreferredSize(new Dimension(100, 30));
        textField3.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField4 = new JLabel("Min arrival time");
        textField4 = new JTextField();
        textField4.setPreferredSize(new Dimension(100, 30));
        textField4.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField5 = new JLabel("Max arrival time");
        textField5 = new JTextField();
        textField5.setPreferredSize(new Dimension(100, 30));
        textField5.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField6 = new JLabel("Time limit");
        textField6 = new JTextField();
        textField6.setPreferredSize(new Dimension(100, 30));
        textField6.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelTextField7 = new JLabel("Servers");
        textField7 = new JTextField();
        textField7.setPreferredSize(new Dimension(100, 30));
        textField7.setFont(new Font("Serif", Font.PLAIN, 20));

        textField8 = new JTextField();
        textField8.setPreferredSize(new Dimension(100, 30));
        textField8.setFont(new Font("Serif", Font.PLAIN, 20));
        textField8.setText("Result");
        textField8.setEditable(false);

        buttonTimeStrategy = new JRadioButton("time strategy");
        buttonQueueStrategy = new JRadioButton("queue strategy");

        ButtonGroup group = new ButtonGroup();
        group.add(buttonTimeStrategy);
        group.add(buttonQueueStrategy);

        JPanel panel = new JPanel();
        panel.setBackground(Color.getColor("F5F5F5"));
        panel.setPreferredSize(new Dimension(200, 230));
        panel.setLayout(new GridLayout(6, 4));

        panel.add(buttonAvrWaitingTime);
        panel.add(buttonAvrServiceTime);
        panel.add(buttonPeak);
        panel.add(textField8);

        panel.add(labelTextField1);
        panel.add(textField1);
        panel.add(labelTextField2);
        panel.add(textField2);
        panel.add(labelTextField3);
        panel.add(textField3);
        panel.add(labelTextField4);
        panel.add(textField4);
        panel.add(labelTextField5);
        panel.add(textField5);
        panel.add(labelTextField6);
        panel.add(textField6);
        panel.add(labelTextField7);
        panel.add(textField7);
        panel.add(buttonTimeStrategy);
        panel.add(buttonQueueStrategy);

        return panel;
    }
    public JPanel createPanelCenter() {

        JPanel panel = new JPanel();

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Append text to the JTextArea
        textArea.append(string+"\n");

        JScrollPane scrollPane = new JScrollPane(textArea); // Add scrollbars to the JTextArea
        scrollPane.setPreferredSize(new Dimension(900, 400));
        panel.add(scrollPane);
        return panel;
    }
    public JButton newButton(String text) {
        //Functia generica de creare a unui buton.
        JButton button = new JButton(text);
        Font font = button.getFont();
        int newSize = 15;
        button.setFont(font.deriveFont((float) newSize));
        button.setFocusable(false);
        button.setBackground(Color.lightGray);
        button.setBorderPainted(false);
        button.setSize(50,50);

        return button;
    }
    public void setTextField(String str, int textFieldNumber){
        if(textFieldNumber == 0) {
            textField1.setText(str);
        } else if(textFieldNumber == 1) {
            textField2.setText(str);
        } else if(textFieldNumber == 2) {
            textField3.setText(str);
        }
    }
    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
        textArea.append(string+"\n");
    }
    public JButton getButtonAvrWaitingTime() {
        return buttonAvrWaitingTime;
    }

    public JButton getButtonAvrServiceTime() {
        return buttonAvrServiceTime;
    }

    public JButton getButtonPeak() {
        return buttonPeak;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public JTextField getTextField6() {
        return textField6;
    }

    public JTextField getTextField7() {
        return textField7;
    }

    public JTextField getTextField8() {
        return textField8;
    }

    public JRadioButton getButtonTimeStrategy() {
        return buttonTimeStrategy;
    }

    public JRadioButton getButtonQueueStrategy() {
        return buttonQueueStrategy;
    }

    public JButton getStartButton() {
        return startButton;
    }
    public JTextArea getTextArea() {
        return textArea;
    }
}
