// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import edu.utexas.arlut.ciads.scratch.graph.Edge;
import edu.utexas.arlut.ciads.scratch.graph.Vertex;

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
        this.outVertexID = out.getRawID();
        this.inVertexID = in.getRawID();
    }
    private final String label;
    private final long outVertexID, inVertexID;
}
