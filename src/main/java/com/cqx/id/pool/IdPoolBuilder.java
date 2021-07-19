//package com.cqx.id.pool;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IdPoolBuilder {
//
//    private IdSegmentLoader loader;
//    private List<String> bizs = new ArrayList<>();
//
//    public IdPoolBuilder loader(IdSegmentLoader loader) {
//        this.loader = loader;
//        return this;
//    }
//
//    public IdPoolBuilder biz(String biz) {
//        this.bizs.add(biz);
//        return this;
//    }
//
//    public IdPoolBuilder biz(String... biz) {
//        for (String s : biz) {
//            this.bizs.add(s);
//        }
//        return this;
//    }
//
//    public void build() {
//        IdPool.init(loader, bizs);
//    }
//}
