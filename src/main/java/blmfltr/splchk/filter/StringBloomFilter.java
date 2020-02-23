package blmfltr.splchk.filter;

import blmfltr.splchk.exceptions.InitializationException;
import org.springframework.stereotype.Service;

import java.util.BitSet;

/**
 * A bloom filter storage for a set of Strings
 */
public class StringBloomFilter{

    private BitSet filterStore = null;
    private int sizeOfData;
    private float falsePositiveProbability;
    private int noOfHashes;
    private int sizeOfFilter;
    private int wordsAdded = 0;
    private static int MAX_HASHES = 8;
    private static  int[] HASH_FACTOR = new int []{31, 41, 37, 53, 61,67,43,59};





    /**
     *
     * @param size
     * @param falsePositiveProbability
     */

    public StringBloomFilter(int size, double falsePositiveProbability) throws InitializationException {
        if(size == 0 || falsePositiveProbability == 0){
            throw new InitializationException("Invalid input");
        }
        this.sizeOfData = size;
        sizeOfFilter = computeSizeOfFilter(falsePositiveProbability);
        filterStore = initFilter();
        noOfHashes = computeNoOfHashes();
    }

    private int computeNoOfHashes() {
        int hashes = 0;
        if(sizeOfData > 0 ) {
            hashes = (int)  (sizeOfFilter * Math.log(2) / sizeOfData);
        }
        return hashes > MAX_HASHES ? MAX_HASHES : hashes;
    }

    private int computeSizeOfFilter( double falsePositiveProbability) {
        if(sizeOfData > 0){
            return (int) Math.abs((sizeOfData * Math.log(falsePositiveProbability) / (Math.log(2)*Math.log(2))));
        }
        return 0;
    }

    public void add(String input) throws InitializationException {
        if(wordsAdded == sizeOfData){
                throw new InitializationException("Filter limit reached");
        }


        for(int i=0; i< noOfHashes; i++){
            int hash = hash(input, i);
            filterStore.set(hash,true);
        }

        wordsAdded++;
    }
    public boolean mightContain(String input){
        for(int i=0; i< noOfHashes; i++){
            int hash = hash(input, i);
            if(!filterStore.get(hash)){
                return false;
            }
        }
        return true;
    }

    public int size(){
        return sizeOfData;
    }

    public void clear(){
        filterStore = initFilter();
        int dataAdded = 0;
    }

    private int hash(String input, int index){
        int hash = 0;
        int powers = 1;
        for(int i = 0; i<input.length(); i++){
            hash = (hash + (input.charAt(i)+1) * powers)%sizeOfFilter;
            powers = (powers*HASH_FACTOR[index])%sizeOfFilter;
        }
        return hash;
    }

    private BitSet initFilter(){
        BitSet filter = new BitSet(sizeOfFilter);
        if(filter != null && sizeOfFilter != 0){
            filter.set(0,sizeOfFilter,false);
        }
        return filter;
    }


    public int getWordsAdded() {
        return wordsAdded;
    }

    public void setWordsAdded(int wordsAdded) {
        this.wordsAdded = wordsAdded;
    }
}
