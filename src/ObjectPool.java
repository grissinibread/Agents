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
    // TODO: This function
    public int getSize() {
        synchronized (this.pool) {
            return this.pool.length;
        }
    }

    @Override
    public int getCapacity() {
        return this.maxInstances;
    }

    @Override
    public void setCapacity(int newCapacity) {
        this.maxInstances = newCapacity;
    }

    @Override
    // TODO: This function
    public Object getObject() {
        synchronized (this.pool) {
            Object thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            }
        }
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

    // TODO: This function
    private Object createObject() {
        Object newObject  = null;
        this.instanceCount++;
        return newObject;
    }
}
