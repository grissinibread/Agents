public class CIA_Agent_Creator implements ObjectCreation_IF{
    private char[] footPrints = {'@', '#', '$', '*', '.', '?'};
    private int index;

    @Override
    public Object create() {
        if (index >= footPrints.length) {
            index = 0; // Reset index
        }
        return new CIA_Agent("CIA Agent " + footPrints[index++]);
    }
}
