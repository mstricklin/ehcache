// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

@Slf4j
public abstract class AbstractLoader<T> implements CacheLoaderWriter<Long, T> {
    @Override
    public abstract T load(Long aLong) throws Exception;
    @Override
    public Map<Long, T> loadAll(Iterable<? extends Long> iterable) throws BulkCacheLoadingException, Exception {
        return null;
    }
    @Override
    public void write(Long id, T t) throws Exception {
        log.warn("Unimplemented write of {} => {}", id, t);
    }
    @Override
    public void writeAll(Iterable<? extends Map.Entry<? extends Long, ? extends T>> iterable) throws BulkCacheWritingException, Exception {
        log.warn("Unimplemented writeAll");
    }
    @Override
    public void delete(Long id) throws Exception {
        log.warn("Unimplemented delete of {}", id);
    }
    @Override
    public void deleteAll(Iterable<? extends Long> iterable) throws BulkCacheWritingException, Exception {
        log.warn("Unimplemented deleteAll");
    }
}
