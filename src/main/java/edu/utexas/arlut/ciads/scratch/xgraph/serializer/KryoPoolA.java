package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import static com.google.common.collect.Maps.newConcurrentMap;
import static com.google.common.collect.Queues.newConcurrentLinkedQueue;
import static com.google.common.collect.Sets.newConcurrentHashSet;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.kryo.Registration;

public class KryoPoolA {
    private static int MAX_POOL_SZ = 5;
    private static KryoPoolA ourInstance = new KryoPoolA();

    // TODO: Injector?
    public static KryoPoolA getInstance() {
        return ourInstance;
    }
    // =================================
    public synchronized Kryo borrowSerializer() {
        while (free.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        Kryo k = free.poll();
        inUse.add( k );
        return k;
    }
    public synchronized void freeSerializer(Kryo serializer) {
        inUse.remove( serializer );
        free.add( serializer );
        this.notify();
    }
    public synchronized void register(Class<?> clazz, int id) {
        Kryo k = borrowSerializer();
        classMapping.put( clazz, id );
        for (Kryo kFree: free) {
            doRegistration( kFree );
        }
    }
    public synchronized int register(Class<?> clazz) {
        lock.lock();
        Kryo k = borrowSerializer();
        Registration r = k.register( clazz );
        classMapping.put( clazz, r.getId() );
        for (Kryo kFree: free) {
            doRegistration( kFree );
        }
        freeSerializer( k );
        return r.getId();
    }
    private void doRegistration(com.esotericsoftware.kryo.Kryo k) {
        for (Map.Entry<Class<?>, Integer> e : classMapping.entrySet()) {
            k.register( e.getKey(), e.getValue() );
        }
    }
    // =================================
    public static class Kryo extends com.esotericsoftware.kryo.Kryo implements Closeable {
        private Kryo(KryoPoolA pool) {
            this.pool = pool;
            this.serializer = new com.esotericsoftware.kryo.Kryo();
            pool.doRegistration( this );
        }

        @Override
        public void close() throws IOException {
            pool.freeSerializer( this );
        }
        // =================================
        com.esotericsoftware.kryo.Kryo serializer;
        KryoPoolA pool;
    }

    private KryoPoolA() {
        // TODO: lazy?
        for (int i = 0; i < MAX_POOL_SZ; i++) {
            free.add( new Kryo( this ) );
        }
    }
    // =================================
    private Map<Class<?>, Integer> classMapping = newConcurrentMap();
    private Queue<Kryo> free = newConcurrentLinkedQueue();
    private Set<Kryo> inUse = newConcurrentHashSet();
    private CloseableReentrantLock lock = new CloseableReentrantLock();
    private int count = 0;


    // =================================
    private static class CloseableReentrantLock extends ReentrantLock implements AutoCloseable {
        @Override
        public void close() throws Exception {
            unlock();
        }
    }
}
