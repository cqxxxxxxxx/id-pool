package com.cqx.id.pool;


import com.cqx.id.pool.loader.IdSegmentLoader;
import com.cqx.id.pool.wrapper.IdWrapperStrategy;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 号段 [curId, maxId) => maxId当做nextSegment的初始化的curId
 */
public class IdSegment<T> {
    private IdSegment() {
    }

    /**
     * id封装包裹策略
     */
    private IdWrapperStrategy<T> idWrapper;

    /**
     * IdSegment加载类
     */
    private IdSegmentLoader loader;

    /**
     * 业务类型
     */
    private String biz;

    /**
     * 当前id
     */
    private AtomicLong curId;

    /**
     * 当前号段最大可取到的id
     */
    private long maxId;

    /**
     * 大小
     */
    private int size;

    /**
     * 阈值，到达时候提前加载下一个号段
     */
    private long threshold;

    /**
     * 下一个号段
     */
    private IdSegment nextSegment;


//=============== METHOD ================

    public T nextId() {
        long r = curId.getAndIncrement();
        if (r >= maxId) {
            if (nextSegment == null) {
                throw new IllegalStateException("biz [" + biz + "] id overflow and next segment not ready. [" + curId + "," + maxId + ")");
            } else {
                IdPool.rollToNextSegment(biz, this);
                return IdPool.nextId(biz);
            }
        }
        //超过阈值，且下个号段没有生成， 触发下一个号段加载
        if (r >= threshold && nextSegment == null) {
            synchronized (this) {
                if (nextSegment == null) {
                    this.nextSegment = loader.load(biz);
                }
            }
        }
        return idWrapper.wrap(r);
    }


    public static <R> Builder<R> builder() {
        return new Builder<R>();
    }



//======================== GETTER ====================

    public IdWrapperStrategy<T> getIdWrapper() {
        return idWrapper;
    }

    public IdSegmentLoader getLoader() {
        return loader;
    }

    public String getBiz() {
        return biz;
    }

    public AtomicLong getCurId() {
        return curId;
    }

    public long getMaxId() {
        return maxId;
    }

    public int getSize() {
        return size;
    }

    public long getThreshold() {
        return threshold;
    }

    public IdSegment getNextSegment() {
        return nextSegment;
    }


//======================== BUILDER ===========

    public static final class Builder<T> {
        private IdWrapperStrategy<T> idWrapper;
        private IdSegmentLoader loader;
        private String biz;
        private AtomicLong curId;
        private long maxId;
        private int size;
        private long threshold;
        private IdSegment nextSegment;

        private Builder() {
        }

        public static Builder anIdSegment() {
            return new Builder();
        }

        public Builder idWrapper(IdWrapperStrategy<T> idWrapper) {
            this.idWrapper = idWrapper;
            return this;
        }

        public Builder loader(IdSegmentLoader loader) {
            this.loader = loader;
            return this;
        }

        public Builder biz(String biz) {
            this.biz = biz;
            return this;
        }

        public Builder curId(AtomicLong curId) {
            this.curId = curId;
            return this;
        }

        public Builder maxId(long maxId) {
            this.maxId = maxId;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder threshold(long threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder nextSegment(IdSegment nextSegment) {
            this.nextSegment = nextSegment;
            return this;
        }

        public IdSegment build() {
            IdSegment idSegment = new IdSegment();
            idSegment.nextSegment = this.nextSegment;
            idSegment.loader = this.loader;
            idSegment.maxId = this.maxId;
            idSegment.curId = this.curId;
            idSegment.threshold = this.threshold;
            idSegment.idWrapper = this.idWrapper;
            idSegment.size = this.size;
            idSegment.biz = this.biz;
            return idSegment;
        }
    }


}
