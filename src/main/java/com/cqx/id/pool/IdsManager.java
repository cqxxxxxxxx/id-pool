package com.cqx.id.pool;//package com.cqx.id.hoe;
//
//import com.xzl.bigdata.storage.dao.IdGeneratorMapper;
//import com.xzl.bigdata.storage.entity.IdGeneratorEntity;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Slf4j
//@Component
//public class IdsManager {
//
//    @Autowired
//    IdGeneratorMapper idGeneratorMapper;
//
//    private IdSegmentLoader loader = this::load;
//
//    @PostConstruct
//    public void init() {
//        IdSegmentPool.setSegmentLoader(loader);
//        IdSegmentPool.init("order_ticket");
//    }
//
//    public String nextId(String type) {
//        return IdSegmentPool.nextId(type);
//    }
//
//
//    @SneakyThrows
//    private IdSegment load(String biz) {
//        while (true) {
//            IdGeneratorEntity entity = idGeneratorMapper.getByBiz(biz);
//            Long expectId = entity.getIdCur();
//            Long updateId = entity.getCacheSize() + entity.getIdCur();
//            int updated = idGeneratorMapper.tryFetchIdSegment(biz, expectId, updateId);
//            if (updated == 1) {
//                IdSegment idSegment = new IdSegment();
//                idSegment.setBiz(biz);
//                idSegment.setNextSegment(null);
//                idSegment.setCurId(new AtomicLong(expectId));
//                idSegment.setSize(entity.getCacheSize());
//                idSegment.setMaxId(updateId);
//                idSegment.setThreshold(entity.getIdCur() + entity.getCacheSize() / 2);
//                idSegment.setLoader(loader);
//                Class<?> aClass = Class.forName(entity.getStrategy());
//                Object o = aClass.newInstance();
//                IdWrapperStrategy idWrapperStrategy = IdWrapperStrategy.class.cast(o);
//                idSegment.setIdWrapper(idWrapperStrategy);
//                return idSegment;
//            }
//        }
//    }
//}
