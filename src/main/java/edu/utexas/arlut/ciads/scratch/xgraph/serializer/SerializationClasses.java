// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;

import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SerializationClasses {
    XVERTEX( XVertex.class, 15 ),
    XEDGE( XVertex.class, 16 );

    SerializationClasses(Class<?> clazz, int id) {
        this.clazz = clazz;
        this.id = id;
    }
    final Class<?> clazz;
    final int id;
}
