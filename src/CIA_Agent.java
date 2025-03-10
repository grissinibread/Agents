public class CIA_Agent implements Agent_IF, Runnable {
    private boolean workingInProgress;
    private String myFootPrint;
    private int taskID;

    CIA_Agent(String footPrint) {
        myFootPrint = footPrint;
        workingInProgress = false;
    }

    @Override
    public void run() {
        startTask();
        processing();
        stopTask();
    }
    private void processing() {
        System.out.println("CIA Agent: " + myFootPrint + " is processing task: " + taskID);
    }
    @Override
    public void startTask() {
        workingInProgress = true;
        System.out.println("CIA Agent: " + myFootPrint + " is starting task: " + taskID);
    }
    @Override
    public void stopTask() {
        workingInProgress = false;
        System.out.println("CIA Agent: " + myFootPrint + " is stopping task: " + taskID);
    }
    @Override
    public void setTask(int id) {
        taskID = id;
    }
}
