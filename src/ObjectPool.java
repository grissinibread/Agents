import java.util.concurrent.locks.Lock; // lock thread execution
import java.util.concurrent.locks.ReentrantLock; // thread to acquire same lock multiple times
import java.util.concurrent.locks.Condition;    // manage thread waiting

public class ObjectPool implements ObjectPool_IF {
    // private Object lockObject = new Object(); // lock object for synchronization
    private Object[] pool; // pool to hold objects
    private ObjectCreation_IF creator; // factory object creator

    private int size; // amount of free objects
    private int instanceCount; // objects created
    private int maxInstances; // maximum objects allowed in pool

    private Lock lock = new ReentrantLock(); // lock for thread safety
    private Condition notEmpty = lock.newCondition(); // condition for waiting

    // Constructor
    private ObjectPool(ObjectCreation_IF c, int max) {
        pool = new Object[max];
        creator = c;
        size = 0;
        instanceCount = 0;
        maxInstances = max; // set maximum instances
    }

    // Instance of ObjectPool
    public synchronized static ObjectPool getPoolInstance(ObjectCreation_IF c, int max) {
        return new ObjectPool(c, max);
    }

    @Override
    public int getSize() {
        lock.lock();
        try {
            return size;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getCapacity() {
        return maxInstances;
    }

    @Override
    public void setCapacity(int newCapacity) {
        lock.lock();
        try {
            maxInstances = newCapacity;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Object getObject() {
        lock.lock();
        try {
            if (size > 0) {
                return removeObject();
            }
            return createObject();
        } finally {
                lock.unlock();
        }
    }


    @Override
    public Object waitForObject() {
        lock.lock();
        try {
            while (size == 0) {
                notEmpty.await(); // wait until object is available
            }
            return removeObject();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    private Object removeObject() {
        if (size > 0) {
            Object obj = pool[size - 1];
            pool[size] = null; // remove object from pool
            return obj;
        }
        return null;

    }

    @Override
    public void release(Object obj) {
        lock.lock();
        try {
            if (size < maxInstances) {
                pool[size++] = obj; // add object to pool
                notEmpty.signal(); // signal that object is available
            }
        } finally {
            lock.unlock();
        }
    }

    private Object createObject() {
        if (instanceCount < maxInstances) {
            Object newObject = creator.create(); // create object
            if (newObject != null) {
                instanceCount++;
            }
            return newObject;
        }
        return null;
    }
    public int getInstanceCount() {
        return instanceCount;
    }

}
