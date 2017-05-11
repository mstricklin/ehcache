// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch;

import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import edu.utexas.arlut.ciads.scratch.xgraph.XGraph;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App2 {
    public static void main(String[] args) {
        XGraph g = new XGraph();
        Vertex v0a = g.addVertex( null );
        log.info("add vertex {}", v0a);
        Vertex v0b = g.getVertex( v0a.getId() );
        log.info("get vertex {}", v0b);

        g.shutdown();
    }
}
