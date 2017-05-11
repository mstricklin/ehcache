package edu.utexas.arlut.ciads.scratch.xgraph;

import java.nio.ByteBuffer;

import edu.utexas.arlut.ciads.scratch.graph.Direction;
import edu.utexas.arlut.ciads.scratch.graph.Edge;
import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import edu.utexas.arlut.ciads.scratch.xgraph.serializer.KryoSerializer;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.serialization.SerializerException;

@Slf4j
@ToString(callSuper=true)
public class XVertex extends XElement implements Vertex {

    public static XVertex of(long id) {
        return new XVertex(id);
    }
    public static XVertex of(XVertex v) {
        return new XVertex(v);
    }
    @Override
    public Iterable<Edge> getEdges(Direction direction, String... labels) {
        return null;
    }
    @Override
    public Iterable<Vertex> getVertices(Direction direction, String... labels) {
        return null;
    }
    @Override
    public Edge addEdge(String label, Vertex inVertex) {
        return null;
    }
    // =================================
    private XVertex(long id) {
        super(id);
    }
    private XVertex(XVertex v) {
        super(v);
    }
    private XVertex() {
        super(99);
    } // TODO: what?

    // =================================
    public static class Serializer implements org.ehcache.spi.serialization.Serializer<XVertex> {
        public Serializer(ClassLoader loader) {
        }
        @Override
        public ByteBuffer serialize(XVertex v) throws SerializerException {
            log.info( "serialize {}", v );
            try (KryoSerializer ks = KryoSerializer.of()) {
                return ks.serialize( v );
            }
        }
        @Override
        public XVertex read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            log.info( "deserialize {}", bb.array() );
            try (KryoSerializer ks = KryoSerializer.of()) {
                XVertex v = ks.deserialize( bb, XVertex.class );
                if (null == v) {
                    throw new SerializerException( "Failed to deserialize: " + bb.array() );
                }
                return v;
            }
        }
        @Override
        public boolean equals(XVertex v, ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            log.info( "equals {}", v );
            return v.equals( read( bb ) );
        }
    }
}
