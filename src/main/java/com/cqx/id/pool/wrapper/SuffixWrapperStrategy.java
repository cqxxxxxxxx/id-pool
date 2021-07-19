package com.cqx.id.pool.wrapper;

import java.util.concurrent.ThreadLocalRandom;

public class SuffixWrapperStrategy implements IdWrapperStrategy<String> {
    private static final String[] PREFIX = new String[676];

    static {
        int index = 0;
        for (char i = 'A'; i <= 'Z'; i++) {
            for (char j = 'A'; j <= 'Z'; j++) {
                PREFIX[index++] = String.valueOf(i) + j;
            }
        }
    }

    @Override
    public String wrap(long id) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        //[0,676)
        int i = random.nextInt(676);
        return id + PREFIX[i];
    }
}
