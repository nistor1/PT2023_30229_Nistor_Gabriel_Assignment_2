package org.example.Controller;

import org.example.Model.Server;
import org.example.Model.Task;
import org.example.View.SimulationFrame;
import org.example.View.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable{
    //data read from UI
    private int timeLimit = 10;
    private int maxProcessingTime = 10;
    private int minProcessingTime = 2;
    private int numberOfServers = 4;
    private int numberOfClients = 10;
    private int minArrivalTime;

    private int maxArrivalTime;
    private double averageWaitingTime;
    private double averageServiceTime;
    private int peak;
    private int peakHour;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    private List<Thread> threadList;
    private int taskCnt = 0;
    private View sim;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime,
                             int numberOfServers, int numberOfClients, int minArrivalTime,
                             int maxArrivalTime, SelectionPolicy selectionPolicy, View view) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.selectionPolicy = selectionPolicy;
        this.sim = view;
        //initialize the scheduler
        //    => create and start numberOfServers threads
        //    => initialize selection strategy => createStrategy
        // initialize frame to display simulation
        // generate numberOfClients clients using generateNRandomTasks() and store them to generatedTasks
        scheduler = new Scheduler(numberOfServers, 1000);
        generatedTasks = new ArrayList<>();
        threadList = new ArrayList<>();
    }

    public void generateNRandomTasks() {
        // generate N random tasks:
        // - random processing time
        // minProcessingTime < processingTime < maxProcessingTime
        // random arrivalTime
        // sort list with respect to arrivalTime
        Random random = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            int processingTime = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            int arrivalTime = random.nextInt(Math.min(maxArrivalTime, timeLimit) - minArrivalTime + 1) + minArrivalTime;
            Task task = new Task(i, arrivalTime, processingTime);
            generatedTasks.add(task);
            averageServiceTime += task.getServiceTime();
        }
        averageServiceTime /= numberOfClients;
        Collections.sort(generatedTasks, Comparator.comparing(Task::getArrivalTime));
    }

    public String displayDataFromQueue(int currentTime, Scheduler scheduler) {

        String displayData = "\n";
        displayData += currentTime + "==>\n";


        //System.out.println(currentTime + "==>");
        int i = 1;
        int tempPeak = 0;
        for(Server s : scheduler.getServers()) {
            tempPeak += s.getNumberOfTasks();
            if(tempPeak > peak) {
                peak = tempPeak;
                peakHour = currentTime;
            }
            displayData += "Queue_" + i + ":";
            i++;
            displayData += s.queueToString() + "\n";
            //System.out.println(s.queueToString());
        }
        return displayData;
    }
    void writeInFile(String displayData, int peak, double averageWaitingTime, double averageServiceTime) {
        String filePath = "displayEvents.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("Peak:" + peak + "\nAverageWaitingTime:" + averageWaitingTime + "\nAverageServiceTime:" + averageServiceTime + "\n" +displayData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        this.generateNRandomTasks();
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            List <Server> servers = scheduler.getServers();
            Server server = servers.get(i);
            Thread thread = new Thread(server);
            server.setThread(thread);
            thread.start();
            thread.setName("ThreadQueue-" + i);
            threadList.add(i, thread);
        }
        int currentTime = 0;
        while(currentTime < timeLimit) {
            Integer lock = currentTime;
                scheduler.changeStrategy(selectionPolicy);
                String displayData = "Waiting list:";
                for(int i = taskCnt; i < generatedTasks.size(); i++) {
                    if(generatedTasks.get(i).getArrivalTime() <= currentTime) {
                        scheduler.dispatchTask(generatedTasks.get(i));
                        taskCnt++;
                    } else {
                        averageWaitingTime++;
                        displayData += "(" + generatedTasks.get(i).getIdTask() + " " + generatedTasks.get(i).getArrivalTime() + " " + generatedTasks.get(i).getServiceTime()+ ")";
                    }
                }

                displayData += displayDataFromQueue(currentTime, scheduler);
                writeInFile(displayData, peakHour, averageWaitingTime, averageServiceTime);
                sim.setStringView(displayData);

            currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
        }
        averageWaitingTime /= numberOfClients;

        for(Server s : scheduler.getServers()) {
            s.stopThread();
        }
        for(Thread t : threadList) {
            t.interrupt();
        }
    }

    public View getSim() {
        return sim;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public double getAverageServiceTime() {
        return averageServiceTime;
    }

    public void setAverageServiceTime(double averageServiceTime) {
        this.averageServiceTime = averageServiceTime;
    }

    public int getPeak() {
        return peakHour;
    }

    public void setPeak(int peak) {
        this.peakHour = peak;
    }
    public Scheduler getScheduler() {
        return scheduler;
    }


}
