// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.graph.Edge;
import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import edu.utexas.arlut.ciads.scratch.xgraph.serializer.KryoSerializer;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.serialization.SerializerException;

@Slf4j
public class XEdge extends XElement implements Edge {
    public static XEdge of(long id, XVertex out, XVertex in, String label) {
        return new XEdge(id, out, in, label);
    }
    @Override
    public Vertex getOutVertex() {
        return null;
    }
    @Override
    public Vertex getInVertex() {
        return null;
    }
    @Override
    public String getLabel() {
        return label;
    }
    // =================================
    private XEdge(long id, XVertex out, XVertex in, String label) {
        super(id);
        this.label = label;
        this.outVertexID = out.getRawId();
        this.inVertexID = in.getRawId();
    }
    private final String label;
    private final long outVertexID, inVertexID;

    // =================================
    public static class Serializer implements org.ehcache.spi.serialization.Serializer<XEdge> {
        public Serializer(ClassLoader loader) { }
        @Override
        public ByteBuffer serialize(XEdge e) throws SerializerException {
            log.info( "serialize {}", e );
            try (KryoSerializer ks = KryoSerializer.of()) {
                return ks.serialize( e );
            }
        }
        @Override
        public XEdge read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            log.info( "deserialize {}", bb.array() );
            try (KryoSerializer ks = KryoSerializer.of()) {
                XEdge e = ks.deserialize( bb, XEdge.class );
                if (null == e) {
                    throw new SerializerException( "Unable to deserialize: " + bb.array() );
                }
                return e;
            }
        }
        @Override
        public boolean equals(XEdge e, ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            return e.equals( read( bb ) );
        }
    }
}
