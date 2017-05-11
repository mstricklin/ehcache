// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

@Slf4j
public class TransactionStorage implements Storage {
    @Override
    public void addVertex(XVertex v) {

    }
    @Override
    public void removeVertex(Long id) {

    }
    @Override
    public XVertex getVertex(Long id) {
        return null;
    }
    @Override
    public void addEdge(XEdge e) {

    }
    @Override
    public void removeEdge(Long id) {

    }
    @Override
    public XEdge getEdge(Long id) {
        return null;
    }
    // =================================
    private TransactionStorage(Cache<Long, XVertex> vertexCache, Cache<Long, XEdge> edgeCache) {
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
        public Builder setVertexBaseline(Cache<Long, XVertex> vertexCache) {
            this.vertexCache = vertexCache;

            UserManagedCache<Long, XVertex> umc =
                    UserManagedCacheBuilder.newUserManagedCacheBuilder( Long.class, XVertex.class )
                                           .withLoaderWriter( new XVertexLoader( vertexCache ) )
                                           .build( true );
            return this;
        }
        public Builder setEdgeBaseline(Cache<Long, XEdge> edgeCache) {
            this.edgeCache = edgeCache;
            return this;
        }
        public TransactionStorage build() {
            return new TransactionStorage( vertexCache, edgeCache );
        }
    }

}
