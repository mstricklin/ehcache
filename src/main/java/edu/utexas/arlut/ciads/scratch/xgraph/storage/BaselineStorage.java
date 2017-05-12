// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import org.ehcache.Cache;

public class BaselineStorage implements Storage {


    @Override
    public void addVertex(XVertex v) {
        vertexCache.put( v.getRawId(), v );
    }
    @Override
    public void removeVertex(Long id) {
        vertexCache.remove( id );
    }
    @Override
    public XVertex getVertex(Long id) {
        return vertexCache.get( id );
    }
    @Override
    public void addEdge(XEdge e) {
        edgeCache.put( e.getRawId(), e );
    }
    @Override
    public void removeEdge(Long id) {
        edgeCache.remove( id );
    }
    @Override
    public XEdge getEdge(Long id) {
        return edgeCache.get( id );
    }
    // =================================
    private BaselineStorage(Cache<Long, XVertex> vertexCache, Cache<Long, XEdge> edgeCache) {
        this.vertexCache = vertexCache;
        this.edgeCache = edgeCache;
    }
    private final Cache<Long, XVertex> vertexCache;
    private final Cache<Long, XEdge> edgeCache;
    // =================================
    static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Cache<Long, XVertex> vertexCache;
        private Cache<Long, XEdge> edgeCache;
        public Builder setVertexCache(Cache<Long, XVertex> vertexCache) {
            this.vertexCache = vertexCache;
            return this;
        }
        public Builder setEdgeCache(Cache<Long, XEdge> edgeCache) {
            this.edgeCache = edgeCache;
            return this;
        }
        public BaselineStorage build() {
            return new BaselineStorage( vertexCache, edgeCache );
        }
    }
}
