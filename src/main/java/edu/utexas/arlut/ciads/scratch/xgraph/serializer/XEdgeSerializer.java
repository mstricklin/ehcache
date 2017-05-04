// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.persistence.StateRepository;
import org.ehcache.spi.serialization.SerializerException;
import org.ehcache.spi.serialization.StatefulSerializer;

@Slf4j
public class XEdgeSerializer implements Closeable, StatefulSerializer<XEdge> {
    public XEdgeSerializer(ClassLoader loader) {

    }
    @Override
    public void close() throws IOException {
        log.info("persist XEdgeSerializer state");
    }
    @Override
    public void init(StateRepository stateRepository) {
        log.info("load XEdgeSerializer state {}", stateRepository);
    }
    @Override
    public ByteBuffer serialize(XEdge xEdge) throws SerializerException {
        return null;
    }
    @Override
    public XEdge read(ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
        return null;
    }
    @Override
    public boolean equals(XEdge xEdge, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
        return false;
    }
}
