// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import static com.google.common.collect.Sets.newConcurrentHashSet;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class KyroManager {
    private static KyroManager ourInstance = new KyroManager();
    // TODO: Injector?
    public static KyroManager getInstance() {
        return ourInstance;
    }
    public synchronized int register(Class<?> clazz) {
        Kryo k = kp.borrow();
        Registration r = k.register( clazz );
        registrations.add( r );
        kp.release( k );
        registerAll();
        return r.getId();
    }
    public synchronized int register(Class<?> clazz, int id) {
        Kryo k = kp.borrow();
        Registration r = k.register( clazz, id );
        registrations.add( r );
        kp.release( k );
        registerAll();
        return id;
    }
    private synchronized void register(Kryo k) {
        for (Registration r : registrations) {
            k.register( r );
        }
    }
    private void registerAll() {
        for (Kryo k: kryos) {
            register(k);
        }
    }
    public CloseableKryo borrow() {
        return (CloseableKryo)kp.borrow();
    }
    public void release(Kryo k) {
        kp.release( k );
    }
    // =================================
    public static class CloseableKryo extends Kryo implements Closeable {
        private CloseableKryo(KryoPool kp) {
            super();
            this.pool = kp;
        }

        @Override
        public void close() throws IOException {
            pool.release( this );
        }

        // =================================
        private final KryoPool pool;
    }
    // =================================
    private final Set<Kryo> kryos = newConcurrentHashSet();
    KryoFactory factory = new KryoFactory() {
        public Kryo create() {
            Kryo kryo = new CloseableKryo(kp);
            kryos.add( kryo );
            register( kryo );
            return kryo;
        }
    };

    private final KryoPool kp = new KryoPool.Builder( factory ).build();
    private final Set<Registration> registrations = newConcurrentHashSet();
}
