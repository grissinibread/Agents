public class ObjectPool implements ObjectPool_IF {
    private Object lockObject;
    private int size; // amount of free objects
    private int instanceCount; // objects created
    private int maxInstances; // maximum objects
    private Object[]  pool;

    private ObjectPool(ObjectCreation_IF c, int max) {
        // TODO: This function
        instanceCount = 0;
        maxInstances = max;
        pool = new Object[max];
    }

    public synchronized static ObjectPool getPoolInstance(ObjectCreation_IF c, int max) {
        // TODO: This function
        return new ObjectPool(c, max);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCapacity() {
        return maxInstances;
    }

    @Override
    public void setCapacity(int newCapacity) {
        maxInstances = newCapacity;
    }

    @Override
    public Object getObject() {
        // TODO: This function
        return null;
    }

    @Override
    public Object waitForObject() {
        // TODO: This function
        return null;
    }

    private Object removeObject() {
        // TODO: This function
        return null;
    }

    @Override
    public void release(Object obj) {

    }

    private Object createObject() {
        return null;
    }
}