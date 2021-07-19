package com.cqx.id.pool;


import com.cqx.id.pool.loader.IdSegmentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdPool {
    private static final Logger log = LoggerFactory.getLogger(IdPool.class);
    private static final Map<String, IdSegment> ID_SEGMENTS = new ConcurrentHashMap<>();
    private static IdSegmentLoader segmentLoader;
    private static volatile boolean inited = false;

    public static void init(IdSegmentLoader loader, List<String> bizList) {
        IdPool.segmentLoader = loader;
        for (String biz : bizList) {
            IdSegment segment = segmentLoader.load(biz);
            ID_SEGMENTS.put(segment.getBiz(), segment);
        }
        IdPool.inited = Boolean.TRUE;
    }

    public static <T> T nextId(String biz) {
        return (T) ID_SEGMENTS.get(biz).nextId();
    }

    public static boolean rollToNextSegment(String biz, IdSegment drySegment) {
        synchronized (biz) {
            IdSegment idSegment = ID_SEGMENTS.get(biz);
            if (idSegment == drySegment) {
                IdSegment freshSegment = drySegment.getNextSegment();
                ID_SEGMENTS.put(biz, freshSegment);
                log.info("idSegment {} roll to next [{},{})", biz, freshSegment.getCurId(), freshSegment.getMaxId());
                idSegment = null;
                return true;
            }
        }
        return false;
    }
}
