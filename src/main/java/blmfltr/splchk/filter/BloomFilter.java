package blmfltr.splchk.filter;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;

import java.util.BitSet;


/**
 * Interface with methods to add data to a bloom filter and remove content from it
 */
public interface BloomFilter{


    /**
     * Method to add data to the bloom filter
     *
     * @param input
     * @throws InitializationException
     */
    public void add(String input)  throws InitializationException;

    /**
     * Method to check if datais present in the filter
     *
     * @param input
     * @return
     */
    public boolean mightContain(String input) throws ProcessingException;
}
