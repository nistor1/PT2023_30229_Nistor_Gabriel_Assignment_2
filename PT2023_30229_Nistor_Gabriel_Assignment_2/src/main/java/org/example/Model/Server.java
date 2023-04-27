package org.example.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private int serverId;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Thread thread;
    private int currentTime;
    private volatile boolean stopThread = false;

    public Server(int serverId) {
            //Initialize queue and waitingPeriod
            this.serverId = serverId;
            tasks = new LinkedBlockingQueue<Task>();
            waitingPeriod = new AtomicInteger(0);
    }
    public void addTask(Task newTask) {
        // add task to queue
        // increment the waitingPeriod
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }
    public void stopThread() {
        stopThread = true;
    }

        public void run() {
            while(!stopThread) {
                //take next task from queue
                //stop the thread for a time equal with the task's processing time
                //decrement the waitingPeriod
                Task tempTask = tasks.peek();
                if(tempTask == null) {
                    continue;
                }
                try {
                       // System.out.println("ServerId=>" + serverId + " (" + tempTask.getIdTask() + " " + tempTask.getArrivalTime() + " " + tempTask.getServiceTime() + ")");

                        thread.sleep(1000);
                        currentTime++;
                        tempTask.setServiceTime(tempTask.getServiceTime() - 1);
                        //System.out.println("ServerId2=>" + serverId + " (" + tempTask.getIdTask() + " " + tempTask.getArrivalTime() + " " + tempTask.getServiceTime() + ")");

                        waitingPeriod.set(waitingPeriod.get() - 1);
                        if(tempTask.getServiceTime() == 0) {
                            tasks.remove();

                        }
                } catch (Exception e) {
                }
            }
        }

        public Task[] getTasks() {
            return tasks.toArray(new Task[0]);
        }
        public Integer getWaitingPeriod() {
            return waitingPeriod.get();
        }
        public Integer getNumberOfTasks() {
            return tasks.size();
        }
        public String queueToString() {
            String s = "";
            for(Task t : tasks) {
                s += "(";
                s += t.getIdTask();
                s += " ";
                s += t.getArrivalTime();
                s += " ";
                s += t.getServiceTime();
                s += ") ";
            }
            return s;
        }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public Thread getThread() {
        return thread;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public BlockingQueue<Task> getTasksQueue() {
        return tasks;
    }
}
