// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import java.util.Map;

import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;

@Slf4j
class XEdgeLoader extends AbstractLoader<XEdge> {
    private final Cache<Long, XEdge> baseline;

    XEdgeLoader(Cache<Long, XEdge> baseline) {
        this.baseline = baseline;
    }

    @Override
    public XEdge load(Long key) throws Exception {
        XEdge e = baseline.get(key);
        log.info("load {}", key);
        return (null == e) ? null
                : XEdge.of(e);
    }

    public Map<Long, XEdge> loadAll(Iterable<? extends Long> iterable) throws BulkCacheLoadingException, Exception {
        return null;
    }
}

