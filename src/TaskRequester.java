public class TaskRequester {
    private ObjectPool server;

    TaskRequester(ObjectPool p) {
        // TODO: This function
    }

    public void run() {
        Agent_IF agent = (Agent_IF) server.waitForObject();
        agent.startTask();

    }
}
