// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.graph;

public interface Graph {
    Vertex addVertex(Object id);
    Vertex getVertex(Object id);
    void removeVertex(Vertex vertex);
    Iterable<Vertex> getVertices();

    Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label);
    Edge getEdge(Object id);
    void removeEdge(Edge edge);
    Iterable<Edge> getEdges();
}
