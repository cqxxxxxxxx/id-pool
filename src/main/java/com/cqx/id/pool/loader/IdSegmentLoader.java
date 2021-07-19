package com.cqx.id.pool.loader;

import com.cqx.id.pool.IdSegment;

public interface IdSegmentLoader {

    IdSegment load(String biz);

}
