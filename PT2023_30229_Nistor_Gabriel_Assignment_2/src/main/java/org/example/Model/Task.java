package org.example.Model;

public class Task {
    private int idTask;
    private int arrivalTime;
    private int serviceTime;
    public Task(int idTask, int arrivalTime, int serviceTime) {
        this.idTask = idTask;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getIdTask() {
        return idTask;
    }
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
