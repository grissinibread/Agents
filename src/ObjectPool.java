import java.lang.reflect.Array;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ObjectPool implements ObjectPool_IF {
    private Object[] pool;
    private ObjectCreation_IF creator;
    private Object lockObject = new Object();

    private int size;
    private int instanceCount;
    private int maxInstances;

    private Lock lock = new ReentrantLock();

    private ObjectPool(ObjectCreation_IF creator, int max) {
        this.size = 0;
        this.creator = creator;
        this.maxInstances = max;
        //this.pool = (Object[]) Array.newInstance(, getCapacity());
        this.pool = new Object[getCapacity()];

        instanceCount = 0;
    }

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
        if(newCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        synchronized (lockObject) {
            Object[] newPool = new Object[newCapacity];
            System.arraycopy(pool, 0, newPool, 0, size);
            this.pool = newPool;
        } // synchronized
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
        if (this.size > 0) {
            return removeObject();
        } else if (instanceCount < maxInstances) {
            return createObject();
        } else {
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    e.printStackTrace();
                }
            } while (this.size < 0);
            return removeObject();
        }
    }

    private Object removeObject() {
        if (size > 0) {
            Object obj = pool[--size];
            pool[size] = null;
            return obj;
        }
        return null;
    }

    @Override
    public void release(Object obj) {
        synchronized (lockObject) {
            if (getSize() < maxInstances) {
                pool[size++] = obj;
                lockObject.notify();
            }
        }
    }

    private Object createObject() {
        lock.lock();
        try {
            if (instanceCount < maxInstances) {
                Object newObject = creator.create();
                if (newObject != null) {
                    instanceCount++;
                }
                return newObject;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }
}