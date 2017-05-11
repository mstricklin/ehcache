// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph;

import static com.google.common.collect.Maps.newHashMap;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;

import edu.utexas.arlut.ciads.scratch.graph.Element;
import edu.utexas.arlut.ciads.scratch.xgraph.serializer.KryoSerializer;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.impl.copy.ReadWriteCopier;
import org.ehcache.spi.copy.Copier;
import org.ehcache.spi.serialization.SerializerException;

@Slf4j
@ToString
public abstract class XElement implements Element {
    @Override
    public <T> T getProperty(String key) {
        return (T)properties.get( key );
    }
    @Override
    public Set<String> getPropertyKeys() {
        return properties.keySet();
    }
    @Override
    public void setProperty(String key, Object value) {
        properties.put( key, value );
    }
    @Override
    public <T> T removeProperty(String key) {
        return (T)properties.remove( key );
    }
    @Override
    public void remove() {

    }
    @Override
    public Object getId() {
        return id;
    }
    public long getRawId() {
        return id;
    }
    // =================================
    protected XElement(long id) {
        this.id = id;
        this.properties = newHashMap();
    }
    protected XElement(XElement e) {
        this.id = e.id;
        this.properties = newHashMap(e.properties);
    }
    // =================================
    protected final long id;
    protected final Map<String, Object> properties;

    @ToString
    public static class Properties {
        private Properties(final Properties rhs) {
            this.id = rhs.id;
            this.properties = newHashMap(rhs.properties);
        }
        public Properties(long id) {
            this.id = id;
            properties = newHashMap();
        }
        @Override
        public int hashCode() { return (int)id; }
        @Override
        public boolean equals(Object o) {
            if (null == o) return false;
            if (this == o) return true;
            if ( ! getClass().equals( o.getClass())) return false;
            return (((Properties)o).id == id);
        }
        protected final long id;
        protected final Map<String, Object> properties;
    }
    // =================================
    public static class Serializer implements org.ehcache.spi.serialization.Serializer<Properties> {
        public Serializer(ClassLoader loader) {
        }
        @Override
        public ByteBuffer serialize(Properties p) throws SerializerException {
            log.info( "serialize {}", p );
            try (KryoSerializer ks = KryoSerializer.of()) {
                return ks.serialize( p );
            }
        }
        @Override
        public Properties read(ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            log.info( "deserialize {}", bb.array() );
            try (KryoSerializer ks = KryoSerializer.of()) {
                Properties re = ks.deserialize( bb, Properties.class );
                if (null == re) {
                    throw new SerializerException( "Unable to deserialize: " + bb.array() );
                }
                return re;
            }
        }
        @Override
        public boolean equals(Properties p, ByteBuffer bb) throws ClassNotFoundException, SerializerException {
            log.info( "equals {}", p );
            return p.equals( read( bb ) );
        }
    }
    // =================================
    public static class XCopier implements Copier<Properties> {

        @Override
        public Properties copyForRead(Properties src) {
            // make mutable copy?
            return new Properties( src );
        }
        @Override
        public Properties copyForWrite(Properties src) {
            // shouldn't be done? replay actions to get result...
            return src;
        }
    }
    // =================================
    public static class ZCopier extends ReadWriteCopier<Properties> {

        @Override
        public Properties copy(Properties properties) {
            return null;
        }
    }
}
