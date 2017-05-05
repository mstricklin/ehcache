// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import static com.google.common.collect.Sets.newConcurrentHashSet;

import java.util.Set;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KyroPool implements com.esotericsoftware.kryo.pool.KryoPool {
    // Kryo uses 0-10 for primitives, and util.Date
    private static final int MIN_ID = 15;
    // =================================
    private static KyroPool ourInstance = new KyroPool();
    // TODO: Injector?
    public static KyroPool getInstance() {
        return ourInstance;
    }

    public Kryo borrow() {
        return pool.borrow();
    }
    public void release(Kryo k) {
        pool.release( k );
    }
    @Override
    public <T> T run(KryoCallback<T> callback) {
        return (T)pool.run( callback );
    }

    // =================================
    private final Set<Kryo> kryos = newConcurrentHashSet();
    private final KryoFactory factory = new KryoFactory() {
        public Kryo create() {
            Kryo kryo = new Kryo();
//            kryo.setRegistrationRequired( true );
            kryos.add( kryo );
//            for (SerializationClasses sc: SerializationClasses.values()) {
//                kryo.register( sc.clazz, sc.id );
//            }
            return kryo;
        }
    };

    private final com.esotericsoftware.kryo.pool.KryoPool pool = new KryoPool.Builder( factory ).build();
    private final Set<Registration> registrations = newConcurrentHashSet();
}
