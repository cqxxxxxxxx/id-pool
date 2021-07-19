package com.cqx.id.pool.loader;

import com.cqx.id.pool.IdPoolEntity;
import com.cqx.id.pool.IdSegment;
import com.cqx.id.pool.wrapper.IdWrapperStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractIdSegmentLoader implements IdSegmentLoader {
    protected static final Logger log = LoggerFactory.getLogger(AbstractIdSegmentLoader.class);

    protected abstract IdPoolEntity loadSegment(String biz) throws Exception;

    protected abstract boolean updateSegment(String biz, Long expectId, Long updateId) throws Exception;

    @Override
    public IdSegment load(String biz) {
        try {
            while (true) {
                IdPoolEntity entity = loadSegment(biz);
                long expectId = entity.getIdCur();
                long updateId = entity.getCacheSize() + entity.getIdCur();
                boolean updated = updateSegment(biz, expectId, updateId);
                if (updated) {
                    IdSegment idSegment = IdSegment.builder()
                            .biz(biz)
                            .curId(new AtomicLong(expectId))
                            .size(entity.getCacheSize())
                            .maxId(updateId)
                            .threshold(entity.getIdCur() + entity.getCacheSize() >> 1)
                            .nextSegment(null)
                            .loader(this)
                            .idWrapper(wrapperStrategy(entity.getStrategy()))
                            .build();
                    return idSegment;
                }
            }
        } catch (Exception e) {
            //todo retry?
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private IdWrapperStrategy wrapperStrategy(String strategyClass) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(strategyClass);
            Constructor<?> constructor = aClass.getConstructor();
            Object o = constructor.newInstance();
            IdWrapperStrategy idWrapper = IdWrapperStrategy.class.cast(o);
            return idWrapper;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        throw new IllegalArgumentException("Id Pool init error");
    }


}
