package edu.utexas.arlut.ciads.scratch.xgraph;

import edu.utexas.arlut.ciads.scratch.graph.Direction;
import edu.utexas.arlut.ciads.scratch.graph.Edge;
import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(callSuper=true)
public class XVertex extends XElement implements Vertex {

    public static XVertex of(long id) {
        return new XVertex(id);
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
    private XVertex() {
        super(99);
    }
}
