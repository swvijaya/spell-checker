package blmfltr.splchk.filter;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;

import java.util.BitSet;

/**
 * A bloom filter storage for English Words. The filter is internally bucketed into 26 buckets. the words are stored
 * in the buckets based on the first character.
 */
public class EnglishBucketBloomFilter extends StringBloomFilter{

    private BitSet[] filter = new BitSet[26];




    /**
     * Constructor calls the parent constructor to compute the size of the filter and the number of hashes
     * required. Then it instantiates each of the filter buckets
     *
     * @param size
     * @param falsePositiveProbability
     */

    public EnglishBucketBloomFilter(int size, double falsePositiveProbability) throws InitializationException {
        super(size, falsePositiveProbability);
        initializeEachBucket();

    }

    /**
     * Instantiates each of the buckets to a bit array of determined size
     *
     */
    private void initializeEachBucket(){
        for(int i=0; i<26; i++){
            filter[i] = new BitSet(sizeOfFilter);
        }
    }


    /**
     * Adds the word after converting to lower case to the filter if they don' t have any special character
     *
     * @param input
     * @throws InitializationException
     */
    public void add(String input) throws InitializationException {
        //TODO: Handle words
        if(input != null && isWordValid(input)) {
            if (noOfElementsAdded == sizeOfData) {
                throw new InitializationException("Filter limit reached");
            }

            int[] hashes = hashes(input.toLowerCase());
            for (int hash : hashes) {
                filter[input.toLowerCase().charAt(0) - 'a'].set(hash, true);
            }

            noOfElementsAdded++;
        }
    }

    /**
     * This method returns false when the word' s hashes don' t match whats stored in the filter.
     * True when the hashes matches the filter. It also returns true when the word has any special characters
     *
     * @param input
     * @return
     */
    public boolean mightContain(String input) throws ProcessingException {
        if(noOfElementsAdded == 0){
            throw new ProcessingException("No words Added");
        }
        //TODO: Handle Proper nouns
        //TODO: Handle words with
        if(input == null){
            return true;
        }
        if(isWordValid(input)) {
            int[] hashes = hashes(input.toLowerCase());
            for (int hash : hashes) {
                if (!filter[input.toLowerCase().charAt(0) - 'a'].get(hash)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Returns true when the word contains only alphabets
     * and false when there are any special characters
     *
     * @param word
     * @return
     */
    private boolean isWordValid(String word){
        //TODO: Handle words with apostrophes, hyphens and other special characters
        return word.matches("^[a-zA-Z-']*$");
    }



}
