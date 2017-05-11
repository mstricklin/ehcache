// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

public interface Storage {
    void addVertex(XVertex v);
    void removeVertex(Long id);
    XVertex getVertex(Long id);

    void addEdge(XEdge e);
    void removeEdge(Long id);
    XEdge getEdge(Long id);

}
