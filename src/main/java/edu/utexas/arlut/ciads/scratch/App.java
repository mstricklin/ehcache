package edu.utexas.arlut.ciads.scratch;

import static java.util.Collections.singletonMap;

import edu.utexas.arlut.ciads.scratch.graph.Vertex;
import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import edu.utexas.arlut.ciads.scratch.xgraph.serializer.SerializationClasses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;
import org.ehcache.xml.XmlConfiguration;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;

// TODO: User Managed cache for xaction
@Slf4j
public class App {
    public static void main(String[] args) {
        System.out.println( "Hello World!" );
//        log.info( "Enum: {}", SerializationClasses.XEDGE);

        final URL myUrl = App.class.getClassLoader().getResource( "ehcache.xml" );
        XmlConfiguration xmlConfig = new XmlConfiguration( myUrl );
//        Map<String, CacheConfiguration<?, ?>> cfg = xmlConfig.getCacheConfigurations();
//        log.info("cfg: {}", cfg);
//        log.info("cfg: {}", cfg.get( "sikm" ));
//
//        CacheConfigurationBuilder.newCacheConfigurationBuilder( cfg.get("sikm") );

        cm = CacheManagerBuilder.newCacheManager( xmlConfig );

        log.info( "CacheManager: {}", cm );
        cm.init();
        Cache<Long, Foo> myCache = cm.getCache( "sikm", Long.class, Foo.class );
        log.info( "myCache: {}", myCache );

        myCache.put( 1L, Foo.of( 1L, "da one!" ) );
        Foo value = myCache.get( 1L );
        log.info( "Cached Foo {}", value );
//        foo();

        cm.removeCache( "sikm" );
//        org.ehcache.core.internal.service.ServiceLocator


        log.info("=======================");

        Cache<Long, XVertex> verticesCache = cm.getCache( "vertices", Long.class, XVertex.class );
        XVertex v0 = XVertex.of(1L);
        log.info("v0: {}", v0);
        verticesCache.put( 1L, v0 );
        v0 = verticesCache.get( 1L );
        log.info("v0: {}", v0);
//        Cache<Long, Foo> sCache =
//                cm.createCache( "fooThroughCache",
//                                CacheConfigurationBuilder.newCacheConfigurationBuilder( Long.class,
//                                                                                        Foo.class,
//                                                                                        ResourcePoolsBuilder.heap( 10 ) )
//                                                         .withLoaderWriter( new SampleLoaderWriter( ) )
//                                                         .add( WriteBehindConfigurationBuilder
//                                                                      .newBatchedWriteBehindConfiguration( 1, TimeUnit.SECONDS, 3)
//                                                                      .queueSize(3)
//                                                                      .concurrencyLevel(1)
//                                                                      .enableCoalescing())
//                                                         .build() );
//
//        Foo f0 = sCache.get( 1L );
//        sCache.put( 1L, f0 );
//        sCache.get( 1L );
//        sCache.put( 2L, Foo.of( 2L, "2L" ) );



        cm.close();


    }
    private static class SampleLoaderWriter implements CacheLoaderWriter<Long, Foo> {

        @Override
        public Foo load(java.lang.Long key) throws Exception {
            log.info("load {}", key);
            return Foo.of(key, Long.toString( key ));
        }
        @Override
        public Map<java.lang.Long, Foo> loadAll(Iterable<? extends java.lang.Long> iterable) throws BulkCacheLoadingException, Exception {
            log.info("loadAll");
            return null;
        }
        @Override
        public void write(java.lang.Long key, Foo val) throws Exception {
            log.info("write {} => {}", key, val);

        }
        @Override
        public void writeAll(Iterable<? extends Map.Entry<? extends java.lang.Long, ? extends Foo>> iterable) throws BulkCacheWritingException, Exception {
            log.info("writeAll");
            for (Map.Entry<? extends java.lang.Long, ? extends Foo> e: iterable) {
                log.info("writeAll {} => {}", e.getKey(), e.getValue());
            }
        }
        @Override
        public void delete(java.lang.Long aLong) throws Exception {
            log.info("delete");
        }
        @Override
        public void deleteAll(Iterable<? extends java.lang.Long> iterable) throws BulkCacheWritingException, Exception {
            log.info("deleteAll");
        }
    }
    private static void foo() {
        log.info( "CacheManager: {}", cm );
        Cache<Long, Foo> myCache = cm.getCache( "sikm", Long.class, Foo.class );
        log.info( "myCache: {}", myCache );
        Foo value = myCache.get( 1L );
        log.info( "Foo {}", value );

    }

    private static CacheManager cm;

    @RequiredArgsConstructor(staticName = "of")
    @Data
    private static class Foo implements Serializable {
        final Long id;
        final String s;
    }
}
