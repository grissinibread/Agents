public interface ObjectPool_IF {
    int getSize(); // num of free objects in pool
    int getCapacity(); // max capacity in pool
    void setCapacity(int newCapacity); // set max capacity in pool
    Object getObject(); // get an object from pool
    Object waitForObject(); // get an object from pool, wait if none available
    void release(Object obj); // release an object back to pool
}