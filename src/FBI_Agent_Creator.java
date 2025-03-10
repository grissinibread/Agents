public class FBI_Agent_Creator implements ObjectCreation_IF {
    private char[] footPrints = {'@', '#', '$', '*', '.', '?'};
    private int index;

    public Object create() {
        if (index >= footPrints.length) {
            index = 0; // Reset index
        }
        return new FBI_Agent("FBI Agent " + footPrints[index++]);
    }
}