package blmfltr.splchk.factory;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.filter.BloomFilter;
import blmfltr.splchk.filter.EnglishBucketBloomFilter;

public class BloomFilterFactory {

    public static BloomFilter createBloomFilter(String type, int size, double falsePositiveProbability) throws InitializationException {
        if("English".equals(type)){
            return new EnglishBucketBloomFilter(size, falsePositiveProbability);
        }else{
            //TODO: Create other types of filters for other languages
        }
        return null;
    }
}
