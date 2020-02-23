package blmfltr.splchk.filter;

import java.util.BitSet;

public interface BloomFilter<T> {


    public void add(T t);
    public boolean mightContain(T t);
}
