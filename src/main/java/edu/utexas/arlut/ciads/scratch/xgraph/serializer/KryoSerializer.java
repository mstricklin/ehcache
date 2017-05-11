// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package edu.utexas.arlut.ciads.scratch.xgraph.serializer;


import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KryoSerializer implements AutoCloseable {
    public static KryoSerializer of() {
        return new KryoSerializer();
    }

    @Override
    public void close() {
        pool.release( serializer );
    }

    public <T> ByteBuffer serialize(T o) {
        Output out = new Output( 4096 );

        try {
            serializer.writeObject( out, o );
        } catch (Exception e) {
            log.error( "KryoSerializer exception", e );
        }
        return ByteBuffer.wrap( out.getBuffer(), 0, (int)out.total() );
    }

    public <T> T deserialize(ByteBuffer bb, Class<T> type) {
        try {
            return serializer.readObject( new Input( bb.array() ), type );
        } catch (Exception e) {
            log.error( "KryoSerializer exception", e );
        }
        return null;
    }
    // =================================
    private KryoSerializer() {
        pool = KyroPool.getInstance();
        serializer = pool.borrow();
    }
    private final KyroPool pool;
    private final Kryo serializer;
}
