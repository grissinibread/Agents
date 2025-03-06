public interface ObjectPool_IF {
    int getSize();
    int getCapacity();
    void setCapacity(int newCapacity);
    Object getObject();
    Object waitForObject();
    void release(Object obj);
}