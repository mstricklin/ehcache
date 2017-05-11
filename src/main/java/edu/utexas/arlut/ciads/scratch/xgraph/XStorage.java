// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import java.net.URL;

import edu.utexas.arlut.ciads.scratch.App;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

@Slf4j
public class XStorage {
    private static final String CONFIG_NAME = "ehcache.xml";
    private static final String VERTEX_CACHE_NAME = "vertices";
    private static final String EDGE_CACHE_NAME = "edges";


    XStorage() {
        final URL configURL = App.class.getClassLoader().getResource( CONFIG_NAME );
        XmlConfiguration xmlConfig = new XmlConfiguration( configURL );
        cm = CacheManagerBuilder.newCacheManager( xmlConfig );
        cm.init();

        verticesBaseline = vertexCache();
        edgesBaseline = edgeCache();

        baseline = BaselineStorage.builder()
                                  .setVertexCache( verticesBaseline )
                                  .setEdgeCache( edgesBaseline )
                                  .build();

    }
    void shutdown() {
        cm.close();
    }

    Storage getBaseline() {
        return baseline;
    }

    Cache<Long, XVertex> vertexCache() {
        return cm.getCache( VERTEX_CACHE_NAME, Long.class, XVertex.class );
    }
    Cache<Long, XEdge> edgeCache() {
        return cm.getCache( EDGE_CACHE_NAME, Long.class, XEdge.class );
    }
    // load, write, delete...?

    // vertex
    // edge
    // vertex property
    // edge property
    // implicit index
    // overlay index
    static class TransactionStorage {
        TransactionStorage() {

        }

    }

    private CacheManager cm;
    private final Storage baseline;

    private final Cache<Long, XVertex> verticesBaseline;
    private final Cache<Long, XEdge> edgesBaseline;

}
