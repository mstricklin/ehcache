// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.config.builders.UserManagedCacheBuilder;


@Slf4j
public class TransactionStorage implements Storage {
    @Override
    public void addVertex(XVertex v) {
        vertexCache.get().put( v.getRawId(), v );
    }
    @Override
    public void removeVertex(Long id) {
        vertexCache.get().remove( id );
    }
    @Override
    public XVertex getVertex(Long id) {
        return vertexCache.get().get(id);
    }
    @Override
    public void addEdge(XEdge e) {
        edgeCache.get().put( e.getRawId(), e );
    }
    @Override
    public void removeEdge(Long id) {
        edgeCache.get().remove( id );
    }
    @Override
    public XEdge getEdge(Long id) {
        return edgeCache.get().get(id);
    }
    // =================================
    private TransactionStorage(Cache<Long, XVertex> vertexCache, Cache<Long, XEdge> edgeCache) {
        this.baselineVertexCache = vertexCache;
        this.baselineEdgeCache = edgeCache;
    }

    private final Supplier<Cache<Long, XVertex>> vertexCacheSupplier = new Supplier<Cache<Long, XVertex>>() {
        public Cache<Long, XVertex> get() {
            return UserManagedCacheBuilder.newUserManagedCacheBuilder( Long.class, XVertex.class )
                        .withLoaderWriter( new XVertexLoader( baselineVertexCache ) )
                        .build( true );
//            return "Foo";
        }
    };
    private final Supplier<Cache<Long, XEdge>> edgeCacheSupplier = new Supplier<Cache<Long, XEdge>>() {
        public Cache<Long, XEdge> get() {
            return UserManagedCacheBuilder.newUserManagedCacheBuilder( Long.class, XEdge.class )
                    .withLoaderWriter( new XEdgeLoader( baselineEdgeCache ) )
                    .build( true );
        }
    };

    private final Cache<Long, XVertex> baselineVertexCache;
    private Supplier<Cache<Long, XVertex>> vertexCache = Suppliers.memoize(vertexCacheSupplier);

    private final Cache<Long, XEdge> baselineEdgeCache;
    private Supplier<Cache<Long, XEdge>> edgeCache = Suppliers.memoize(edgeCacheSupplier);

    // =================================
    static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Cache<Long, XVertex> vertexCache;
        private Cache<Long, XEdge> edgeCache;
        public Builder setVertexBaseline(Cache<Long, XVertex> vertexCache) {
            this.vertexCache = vertexCache;
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
