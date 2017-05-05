// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.nio.ByteBuffer;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.persistence.StateHolder;
import org.ehcache.spi.persistence.StateRepository;
import org.ehcache.spi.serialization.SerializerException;
import org.ehcache.spi.serialization.StatefulSerializer;

@Slf4j
public abstract class AbstractSerializer<T> implements AutoCloseable, StatefulSerializer<T> {
    public final static String KRYO_MAPPING = "KyroMapping";
    @Override
    public void close() {

    }
    public void init(StateRepository stateRepository, Class<?> clazz) {
//        StateHolder<Class, Integer> stateholder = stateRepository.getPersistentStateHolder( KRYO_MAPPING, Class.class, Integer.class );
//        log.info( "StateHolder {}", stateholder );
//        log.info( "StateHolder {}", clazz );
//        log.info( "StateHolder {}", stateholder.get( clazz ) );
//        for (Map.Entry<Class, Integer> e:stateholder.entrySet()) {
//            log.info("\t\t{} => {}", e.getKey(), e.getValue());
//        }
//        KyroPool km = KyroPool.getInstance();
//
//        Integer regID = stateholder.get( clazz );
//        if (null == regID) {
//            regID = km.register( clazz );
//        } else {
//            regID = km.register( clazz, regID );
//        }
//        stateholder.putIfAbsent( clazz, regID );
    }
    @Override
    public abstract void init(StateRepository stateRepository);
    @Override
    public abstract ByteBuffer serialize(T t) throws SerializerException;
    @Override
    public abstract T read(ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException;
    @Override
    public abstract boolean equals(T o, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException;
}
