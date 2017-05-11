// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;

public interface Storage {
    void addVertex(XVertex v);
    void removeVertex(Long id);
    XVertex getVertex(Long id);

    void addEdge(XEdge e);
    void removeEdge(Long id);
    XEdge getEdge(Long id);

}
