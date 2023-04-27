package org.example.Controller;

import org.example.View.SimulationFrame;
import org.example.View.View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener {
    private SimulationManager gen;
    private View view;

    public Controller() {

        this.gen = null;
        view = new View("");

        setCalculatorListeners(view.getView());
    }

    public void setCalculatorListeners(SimulationFrame view){

        view.getButtonPeak().addActionListener(this);
        view.getButtonAvrServiceTime().addActionListener(this);
        view.getButtonAvrWaitingTime().addActionListener(this);
        view.getButtonQueueStrategy().addActionListener(this);
        view.getButtonTimeStrategy().addActionListener(this);
        view.getTextField1().addActionListener(this);
        view.getTextField2().addActionListener(this);
        view.getTextField3().addActionListener(this);
        view.getTextField4().addActionListener(this);
        view.getTextField5().addActionListener(this);
        view.getTextField6().addActionListener(this);
        view.getTextField7().addActionListener(this);
        view.getTextField8().addActionListener(this);
        view.getStartButton().addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getView().getStartButton()) {
            String strTimeLimit;
            String strMaxProcessingTime;
            String strMinProcessingTime;
            String strNumberOfServers;
            String strNumberOfClients;
            String strMinArrivalTime;
            String strMaxArrivalTime;
            String strServers;
            SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            if(e.getSource() == view.getView().getButtonQueueStrategy()) {
                selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            } else if(e.getSource() == view.getView().getButtonTimeStrategy()){
                selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            }

            strNumberOfClients = view.getView().getTextField1().getText();
            strMinProcessingTime = view.getView().getTextField2().getText();
            strMaxProcessingTime = view.getView().getTextField3().getText();
            strMinArrivalTime = view.getView().getTextField4().getText();
            strMaxArrivalTime = view.getView().getTextField5().getText();
            strTimeLimit =  view.getView().getTextField6().getText();
            strServers = view.getView().getTextField7().getText();
            int timeLimit = Integer.parseInt(strTimeLimit);
            int maxProcessingTime = Integer.parseInt(strMaxProcessingTime);
            int minProcessingTime = Integer.parseInt(strMinProcessingTime);
            int numberOfServers = Integer.parseInt(strServers);
            int numberOfClients = Integer.parseInt(strNumberOfClients);
            int minArrivalTime = Integer.parseInt(strMinArrivalTime);
            int maxArrivalTime = Integer.parseInt(strMaxArrivalTime);

            String filePath = "displayEvents.txt";
            File file = new File(filePath);
            if (file.exists()) {
                boolean isDeleted = file.delete();
            }

            gen = new SimulationManager(
                    timeLimit,
                    maxProcessingTime,
                    minProcessingTime,
                    numberOfServers,
                    numberOfClients,
                    minArrivalTime,
                    maxArrivalTime,
                    selectionPolicy,
                    view
            );
            Thread t = new Thread(gen);
            t.start();
        } else if(e.getSource() == view.getView().getButtonAvrWaitingTime()){
            if(gen == null) {
                view.getView().getTextField8().setText("N-am ce sa afisez");
            } else {
                Double a = gen.getAverageWaitingTime();
                view.getView().getTextField8().setText(a.toString());
            }
        } else if(e.getSource() == view.getView().getButtonAvrServiceTime()){
            if(gen == null) {
                view.getView().getTextField8().setText("Apasa pe primul buton");
            } else {
                Double a = gen.getAverageServiceTime();
                view.getView().getTextField8().setText(a.toString());
            }
        } else if(e.getSource() == view.getView().getButtonPeak()){
            if(gen == null) {
                view.getView().getTextField8().setText("Nasol");
            } else {
                Integer a = gen.getPeak();
                view.getView().getTextField8().setText(a.toString());
            }
        }
    }
}
