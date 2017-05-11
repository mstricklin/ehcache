// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.graph;

public interface Vertex extends Element {
    Iterable<Edge> getEdges(Direction direction, String... labels);
    Iterable<Vertex> getVertices(Direction direction, String... labels);

    // VertexQuery query();

    Edge addEdge(String label, Vertex inVertex);
}
