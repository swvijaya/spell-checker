package blmfltr.splchk.test;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;
import blmfltr.splchk.filter.StringBloomFilter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class StringBloomFilterTest {

    @Test(expected = InitializationException.class)
    public void testEmptyFilterSize() throws InitializationException {
        StringBloomFilter filter = new StringBloomFilter(0, 0.05);
    }

    @Test
    public void testZeroProbability() throws InitializationException {
       Exception exception = Assertions.assertThrows(InitializationException.class , () -> { new StringBloomFilter(50000, 0);});
       Assertions.assertEquals("Invalid input", exception.getMessage());
    }

    @Test
    public void testInstantiateFilter() throws InitializationException {
        int size = 8;
        StringBloomFilter filter = new StringBloomFilter(size, 0.05);
        Assertions.assertEquals(size, filter.size());
        Assertions.assertEquals(0,filter.getWordsAdded());
    }

    @Test
    public void testAdd() throws InitializationException {

        StringBloomFilter filter = new StringBloomFilter(1, 0.05);
        filter.add("nice");
        Assertions.assertEquals(1, filter.getWordsAdded());
    }

    @Test
    public void testAddWithLimitReached() throws InitializationException {
        StringBloomFilter filter = new StringBloomFilter(1, 0.05);
        filter.add("nice");
        Exception exception = Assertions.assertThrows(InitializationException.class, () -> {filter.add("wise");});
        Assertions.assertEquals("Filter limit reached", exception.getMessage());
    }
}
