// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import java.util.Map;

import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;

@Slf4j
class XVertexLoader extends AbstractLoader<XVertex> {
    private final Cache<Long, XVertex> baseline;
    XVertexLoader(Cache<Long, XVertex> baseline) {
        this.baseline = baseline;
    }

    @Override
    public XVertex load(Long key) throws Exception {
        XVertex v = baseline.get( key );
        log.info( "load {}", key );
        return (null == v) ? null
                           :XVertex.of( v );
    }
    public Map<Long, XVertex> loadAll(Iterable<? extends Long> iterable) throws BulkCacheLoadingException, Exception {
        return null;
    }
}
