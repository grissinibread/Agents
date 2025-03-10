public class TaskRequester implements Runnable {
    private ObjectPool server;
    private int taskID;

    TaskRequester(ObjectPool p, int id) {
        server = p;
        taskID = id;
    }

    @Override
    public void run() {
        Agent_IF agent = (Agent_IF) server.waitForObject(); //Requesting agent from the pool
        if (agent != null) {
            agent.setTask(taskID);
            agent.startTask();  //Start the task
            agent.stopTask();  //Stop the task
            server.release(agent); //Release agent back to the pool
        }
    }
}
