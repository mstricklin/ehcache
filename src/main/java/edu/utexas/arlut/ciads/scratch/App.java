package edu.utexas.arlut.ciads.scratch;

import edu.utexas.arlut.ciads.scratch.xgraph.XVertex;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.spi.copy.Copier;
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

        final URL myUrl = App.class.getClassLoader().getResource( "ehcache.xml" );
        XmlConfiguration xmlConfig = new XmlConfiguration( myUrl );
//        Map<String, CacheConfiguration<?, ?>> cfg = xmlConfig.getCacheConfigurations();
//        log.info("cfg: {}", cfg);
//        log.info("cfg: {}", cfg.get( "sikm" ));
//
//        CacheConfigurationBuilder.newCacheConfigurationBuilder( cfg.get("sikm") );


        cm = CacheManagerBuilder.newCacheManager( xmlConfig );
        log.info( "XStorage: {}", cm );
        cm.init();



        log.info( "=======================" );

        Cache<Long, XVertex> verticesCache = cm.getCache( "vertices", Long.class, XVertex.class );
        XVertex v0 = XVertex.of( 1L );
        log.info( "v0: {}", v0 );
        verticesCache.put( 1L, v0 );
        v0 = verticesCache.get( 1L );
        log.info( "v0: {}", v0 );

        v0.setProperty( "a", "a0" );
        log.info( "v0: {}", v0 );

        v0 = verticesCache.get( 1L );
        log.info( "v0: {}", v0 );

        log.info( "=======================" );
        log.info( "vertex XStorage: {}", cm.getCache( "vertices", Long.class, XVertex.class ) );
        log.info( "vertex XStorage: {}", cm.getCache( "vertices", Long.class, XVertex.class ) );
        log.info( "vertex XStorage: {}", cm.getCache( "vertices", Long.class, XVertex.class ) );


        log.info( "=======================" );
        UserManagedCache<Long, XVertex> umc =
                UserManagedCacheBuilder.newUserManagedCacheBuilder( Long.class, XVertex.class )
                                       .withLoaderWriter( new XVertexLoader( verticesCache ) )
                                       .build( true );
        v0 = umc.get( 1L );
        log.info( "umc v0: {}", v0 );
        v0 = umc.get( 1L );
        v0.setProperty( "b", "b0" );

        log.info( "umc v0: {}", v0 );
        umc.put( 2L, XVertex.of( 2L ) );
        v0 = umc.get( 2L );
        log.info( "umc v0: {}", v0 );

        v0 = verticesCache.get( 1L );
        log.info( "verticesCache v0: {}", v0 );

        v0 = umc.get( 72L );
        log.info( "umc v0: {}", v0 );



//        public class TestHook {
//            public static void main(String[] args) {
//                Runtime.getRuntime().addShutdownHook( new Thread() {
//                    public void run() {
//                        System.out.println( "Shutdown Hook is running !" );
//                    }
//                } );
//                System.out.println( "Application Terminating ..." );
//            }
//        }
//        XStorage<Long, Foo> sCache =
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
    private static class XVertexCopier implements Copier<XVertex> {

        @Override
        public XVertex copyForRead(XVertex xVertex) {
            log.info( "copyForRead {}", xVertex );
            return xVertex;
        }
        @Override
        public XVertex copyForWrite(XVertex xVertex) {
            log.info( "copyForWrite {}", xVertex );
            return xVertex;
        }
    }

    private static class XVertexLoader implements CacheLoaderWriter<Long, XVertex> {
        private final Cache<Long, XVertex> baseline;
        XVertexLoader(Cache<Long, XVertex> baseline) {
            this.baseline = baseline;
        }

        @Override
        public XVertex load(java.lang.Long key) throws Exception {
            XVertex v = baseline.get( key );
            log.info( "load {}", key );
            return (null == v) ? null
                   :XVertex.of( v );
        }
        @Override
        public Map<java.lang.Long, XVertex> loadAll(Iterable<? extends java.lang.Long> iterable) throws BulkCacheLoadingException, Exception {
            log.info( "loadAll" );
            return null;
        }
        @Override
        public void write(java.lang.Long key, XVertex val) throws Exception {
            log.info( "write {} => {}", key, val );
            // no-op?
        }
        @Override
        public void writeAll(Iterable<? extends Map.Entry<? extends java.lang.Long, ? extends XVertex>> iterable) throws BulkCacheWritingException, Exception {
            log.info( "writeAll" );
            for (Map.Entry<? extends java.lang.Long, ? extends XVertex> e : iterable) {
                log.info( "writeAll {} => {}", e.getKey(), e.getValue() );
            }
        }
        @Override
        public void delete(java.lang.Long aLong) throws Exception {
            log.info( "delete" );
        }
        @Override
        public void deleteAll(Iterable<? extends java.lang.Long> iterable) throws BulkCacheWritingException, Exception {
            log.info( "deleteAll" );
        }
    }
    private static void foo() {
        log.info( "XStorage: {}", cm );
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
