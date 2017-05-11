// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.graph;

public interface Edge extends Element {

    Vertex getOutVertex();
    Vertex getInVertex();
    String getLabel();
}
