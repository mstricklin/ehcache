// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

//http://www.ehcache.org/blog/2016/05/12/ehcache3-serializers.html
// serialize id (long), array of out IDs (longs, array of in IDs (longs)
@Slf4j
public class XVertexSerializer implements Serializer<XVertex> {
    public XVertexSerializer(ClassLoader loader) {
    }
    @Override
    public ByteBuffer serialize(XVertex v) throws SerializerException {
        log.info( "Serialize {}", v );
        return KryoSerializer.of().serialize(v);
    }
    @Override
    public XVertex read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
        log.info( "Deserialize {}", bb.array() );
        XVertex v = KryoSerializer.of().deserialize( bb, XVertex.class );
        if (null == v) {
            throw new SerializerException( "Unable to deserialize: " + bb.array() );
        }
        return v;
    }
    @Override
    public boolean equals(XVertex v, ByteBuffer bb) throws ClassNotFoundException, SerializerException {
        log.info( "equals {}", v );
        return v.equals( read( bb ) );
    }
    // =================================

}
