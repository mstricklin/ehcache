// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.persistence.StateRepository;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;
import org.ehcache.spi.serialization.StatefulSerializer;

// serialize id (long), label (String)
// out ID (long), in ID (long)
@Slf4j
public class XEdgeSerializer implements Serializer<XEdge> {
    public XEdgeSerializer(ClassLoader loader) { }
    @Override
    public ByteBuffer serialize(XEdge e) throws SerializerException {
        log.info( "Serialize {}", e );
        return KryoSerializer.of().serialize(e);
    }
    @Override
    public XEdge read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
        log.info( "Deserialize {}", bb.array() );
        XEdge e = KryoSerializer.of().deserialize( bb, XEdge.class );
        if (null == e) {
            throw new SerializerException( "Unable to deserialize: " + bb.array() );
        }
        return e;
    }
    @Override
    public boolean equals(XEdge e, ByteBuffer bb) throws ClassNotFoundException, SerializerException {
        return e.equals( read( bb ) );
    }
}
