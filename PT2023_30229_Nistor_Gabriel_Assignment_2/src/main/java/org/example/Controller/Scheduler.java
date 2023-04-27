package org.example.Controller;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
        private List<Server> servers;
        private int maxNoServers;
        private int maxTasksPerServer;
        private Strategy strategy;

        public Scheduler(int maxNoServers, int maxTasksPerServer) {
            //for maxNoServers
            // - create server object
            // - create thread with the object

            servers = new ArrayList<Server>();
            for(int i = 0; i < maxNoServers; i++) {
                servers.add(new Server(i));
            }

            this.maxNoServers = maxNoServers;
            this.maxTasksPerServer = maxTasksPerServer;
        }

        public void changeStrategy(SelectionPolicy policy) {
            //apply strategy patter to instantiate the strategy with the concrete strategy corresponding to policy
            if(policy == SelectionPolicy.SHORTEST_QUEUE) {
                strategy = new ConcreteStrategyQueue();
            }
            if(policy == SelectionPolicy.SHORTEST_TIME) {
                strategy = new ConcreteStrategyTime();
            }
        }

        public synchronized void dispatchTask(Task t) {
            //call the strategy addTask method
            if(strategy instanceof ConcreteStrategyQueue) {
                ((ConcreteStrategyQueue)strategy).addTask(servers, t);
            } else {
                ConcreteStrategyTime c = (ConcreteStrategyTime) strategy;
                c.addTask(servers, t);
            }
        }

        public List<Server> getServers() {
            return servers;
        }
}
