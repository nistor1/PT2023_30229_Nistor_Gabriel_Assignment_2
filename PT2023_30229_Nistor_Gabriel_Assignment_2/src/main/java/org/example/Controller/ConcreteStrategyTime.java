package org.example.Controller;

import org.example.Model.Server;
import org.example.Model.Task;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        // TODO Auto-generated method stub
        Server server = servers.get(0);
        int index = 0;
        Integer minimumWaitingTime = server.getWaitingPeriod();
        index = 0;
        int i = 0;
        for(Server s: servers) {


            if (s.getWaitingPeriod() < minimumWaitingTime) {
                minimumWaitingTime = s.getWaitingPeriod();
                index = i;
            }
            i++;
        }
        servers.get(index).addTask(t);
    }
}
