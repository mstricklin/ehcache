// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Set;

import edu.utexas.arlut.ciads.scratch.graph.Element;
import lombok.ToString;

@ToString
public abstract class XElement implements Element {
    @Override
    public <T> T getProperty(String key) {
        return (T)properties.get( key );
    }
    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }
    @Override
    public void setProperty(String key, Object value) {
        properties.put( key, value );
    }
    @Override
    public <T> T removeProperty(String key) {
        return (T)properties.remove( key );
    }
    @Override
    public void remove() {

    }
    @Override
    public Object getId() {
        return id;
    }
    public long getRawID() {
        return id;
    }
    // =================================
    protected XElement(long id) {
        this.id = id;
    }
    // =================================
    protected final long id;
    protected final Map<String, Object> properties = newHashMap();
}
