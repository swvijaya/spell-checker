package blmfltr.splchk.test;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;
import blmfltr.splchk.filter.BloomFilter;
import blmfltr.splchk.filter.EnglishBucketBloomFilter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class EnglishBucketBloomFilterTest {

    EnglishBucketBloomFilter filter = null;

    private static double FALSE_POSITIVE_PROBABILITY = 0.002;

    //Test with 0 size
    @Test(expected = InitializationException.class)
    public void testFilterWithZeroSize() throws InitializationException {
        filter = new EnglishBucketBloomFilter(0, 0.002);
    }

    //Test with 1 probability
    @Test
    public void testFilterWithOneProbability() throws InitializationException, ProcessingException {
        filter = new EnglishBucketBloomFilter( 3, 1);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertTrue(filter.mightContain("best"));
    }

    //Add word beyond size
    @Test
    public void testFilterInstantiation() throws InitializationException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Exception e = Assertions.assertThrows(InitializationException.class, () ->filter.add("next"));
        Assertions.assertEquals("Filter limit reached", e.getMessage());

    }

    //Check word size
    @Test
    public void testFilterSize() throws InitializationException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertEquals(3,filter.getNoOfElementsAdded());

    }

    //Check for word present
    @Test
    public void testFilterForWordPresent() throws InitializationException, ProcessingException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertTrue(filter.mightContain("next"));
    }

    //Check for mixed case word present
    @Test
    public void testFilterForWordPresentCase() throws InitializationException, ProcessingException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertTrue(filter.mightContain("nExT"));
    }

    //Check for word not in filter
    @Test
    public void testFilterForWordNotPresent() throws InitializationException, ProcessingException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertFalse(filter.mightContain("nexs"));
    }

    //Check for word with special character
    @Test
    public void testFilterWithSpecialCharacter() throws InitializationException, ProcessingException {
        filter = new EnglishBucketBloomFilter(3, FALSE_POSITIVE_PROBABILITY);
        filter.add("next");
        filter.add("lime");
        filter.add("wise");
        Assertions.assertTrue(filter.mightContain("next$"));
        Assertions.assertEquals(3,filter.getNoOfElementsAdded());


    }

}
