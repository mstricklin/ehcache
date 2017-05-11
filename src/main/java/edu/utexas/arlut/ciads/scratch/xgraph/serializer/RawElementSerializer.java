// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.xgraph.XElement;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;

@Slf4j
public class RawElementSerializer implements Serializer<XElement.Properties> {
    public RawElementSerializer(ClassLoader loader) { }
    @Override
    public ByteBuffer serialize(XElement.Properties re) throws SerializerException {
        log.info( "serialize {}", re );
        try (KryoSerializer ks = KryoSerializer.of()) {
            return ks.serialize( re );
        }
    }
    @Override
    public XElement.Properties read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
        log.info( "deserialize {}", bb.array() );
        try (KryoSerializer ks = KryoSerializer.of()) {
            XElement.Properties re = ks.deserialize( bb, XElement.Properties.class );
            if (null == re) {
                throw new SerializerException( "Unable to deserialize: " + bb.array() );
            }
            return re;
        }
    }
    @Override
    public boolean equals(XElement.Properties rawElement, ByteBuffer byteBuffer) throws ClassNotFoundException, SerializerException {
        return false;
    }
}
