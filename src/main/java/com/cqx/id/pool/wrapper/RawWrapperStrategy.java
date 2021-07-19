package com.cqx.id.pool.wrapper;

public class RawWrapperStrategy implements IdWrapperStrategy<Long> {

    @Override
    public Long wrap(long id) {
        return id;
    }
}
