// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.atomic.AtomicLong;

import edu.utexas.arlut.ciads.scratch.graph.Edge;
import edu.utexas.arlut.ciads.scratch.graph.Graph;
import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import edu.utexas.arlut.ciads.scratch.xgraph.storage.XStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XGraph implements Graph {
    public XGraph() {
        xs = new XStorage();
    }
    public void shutdown() {
        xs.shutdown();
    }
    @Override
    public Vertex addVertex(Object id) {
        long vID = VERTEX_ID.getAndIncrement();
        XVertex v = XVertex.of( vID );
        xs.getBaseline().addVertex( v );
        log.info("addVertex {} => {}", id, v);
        return v;
    }
    @Override
    public Vertex getVertex(Object id) {
        checkNotNull( id );
        try {
            final Long longID = (id instanceof Long) ? (Long)id : Long.valueOf( id.toString() );
            XVertex v = xs.getBaseline().getVertex( longID );
            log.info("getVertex {} => {}", id, v);
            return v;
        } catch (NumberFormatException | ClassCastException e) {
            log.error( "could not find vertex id {}", id );
        }
        return null;
    }
    @Override
    public void removeVertex(Vertex v) {
        XVertex xv = (XVertex)v;
        xs.getBaseline().removeVertex( xv.getRawId() );

    }
    @Override
    public Iterable<Vertex> getVertices() {
        return null;
    }
    @Override
    public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
        return null;
    }
    @Override
    public Edge getEdge(Object id) {
        return null;
    }
    @Override
    public void removeEdge(Edge edge) {

    }
    @Override
    public Iterable<Edge> getEdges() {
        return null;
    }

    private static AtomicLong EDGE_ID = new AtomicLong( 0L );
    private static AtomicLong VERTEX_ID = new AtomicLong( 0L );
    private final XStorage xs;
}
