// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.graph;

import java.util.Set;

public interface Element {

    <T> T getProperty(String key);
    Set<String> getPropertyKeys();
    void setProperty(String key, Object value);
    <T> T removeProperty(String key);
    void remove();
    Object getId();

}
