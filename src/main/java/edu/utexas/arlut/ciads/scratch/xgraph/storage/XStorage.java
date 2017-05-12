// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.storage;

import java.net.URL;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import edu.utexas.arlut.ciads.scratch.App;
import edu.utexas.arlut.ciads.scratch.xgraph.XEdge;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import edu.utexas.arlut.ciads.scratch.xgraph.storage.BaselineStorage;
import edu.utexas.arlut.ciads.scratch.xgraph.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.xml.XmlConfiguration;

@Slf4j
public class XStorage {
    private static final String CONFIG_NAME = "ehcache.xml";
    private static final String VERTEX_CACHE_NAME = "vertices";
    private static final String EDGE_CACHE_NAME = "edges";


    public XStorage() {
        final URL configURL = App.class.getClassLoader().getResource(CONFIG_NAME);
        XmlConfiguration xmlConfig = new XmlConfiguration(configURL);
        cm = CacheManagerBuilder.newCacheManager(xmlConfig);
        cm.init();

        baseline = BaselineStorage.builder()
                .setVertexCache(verticesBaseline.get())
                .setEdgeCache(edgesBaseline.get())
                .build();
    }

    public void shutdown() {
        cm.close();
    }

    public Storage getBaseline() {
        return baseline;
    }

    Cache<Long, XVertex> vertexCache() {
        return verticesBaseline.get();
    }

    Cache<Long, XEdge> edgeCache() {
        return cm.getCache(EDGE_CACHE_NAME, Long.class, XEdge.class);
    }
    // load, write, delete...?

    // vertex
    // edge
    // vertex property
    // edge property
    // implicit index
    // overlay index

    private ThreadLocal<Storage> transaction = new ThreadLocal<Storage>() {
        @Override
        protected Storage initialValue() {
            return TransactionStorage.builder()
                    .setVertexBaseline(verticesBaseline.get())
                    .setEdgeBaseline(edgesBaseline.get())
                    .build();
        }
    };

    private final CacheManager cm;
    private final Storage baseline;

    private final Supplier<Cache<Long, XVertex>> verticesBaseline =
            Suppliers.memoize(new Supplier<Cache<Long, XVertex>>() {
                public Cache<Long, XVertex> get() {
                    return cm.getCache(VERTEX_CACHE_NAME, Long.class, XVertex.class);
                }
            });
    private final Supplier<Cache<Long, XEdge>> edgesBaseline =
            Suppliers.memoize(new Supplier<Cache<Long, XEdge>>() {
                public Cache<Long, XEdge> get() {
                    return cm.getCache(EDGE_CACHE_NAME, Long.class, XEdge.class);
                }
            });


}
