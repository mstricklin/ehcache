// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.persistence.StateHolder;
import org.ehcache.spi.persistence.StateRepository;
import org.ehcache.spi.serialization.SerializerException;
import org.ehcache.spi.serialization.StatefulSerializer;

//http://www.ehcache.org/blog/2016/05/12/ehcache3-serializers.html
@Slf4j
public class XVertexSerializer implements Closeable, StatefulSerializer<XVertex> {
    public final static String KRYO_MAPPING = "KyroMapping";
    public XVertexSerializer(ClassLoader loader) {

    }
    StateHolder<Class, Integer> sh;
    @Override
    public void init(StateRepository stateRepository) {
        log.info("load XVertexSerializer state {}", stateRepository);
        sh = stateRepository.getPersistentStateHolder( KRYO_MAPPING, Class.class, Integer.class );
        log.info("StateHolder {}", sh);
        log.info("StateHolder {}", sh.get( XVertex.class ));
        KyroManager km = KyroManager.getInstance();

        km.register( XVertex.class, 27 );

    }
    @Override
    public void close() throws IOException {
        log.info("persist XVertexSerializer state");
        sh.putIfAbsent( XVertex.class, 27 );
        // persistState
    }
    @Override
    public ByteBuffer serialize(XVertex v) throws SerializerException {
        log.info("Serialize... {}", v);
        ByteBuffer buff = ByteBuffer.allocate(8);
        buff.putLong(v.getRawID()).flip();
        log.info("ByteBuffer {}", buff);
        return buff;
    }
    @Override
    public XVertex read(ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
        log.info("Deserialize...");
        Long id = byteBuffer.getLong();
        XVertex v = XVertex.of(id);
        if(null == v) {
            throw new SerializerException("Unable to deserialize: " + byteBuffer.array());
        }
        log.info("Deserialize {}", v);
        return v;
    }
    @Override
    public boolean equals(XVertex v, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
        log.info("equals {}", v);
        return v.equals(read(byteBuffer));
    }
    // =================================
    private File stateFile;

}
