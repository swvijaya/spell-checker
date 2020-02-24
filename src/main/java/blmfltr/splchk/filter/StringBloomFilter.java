package blmfltr.splchk.filter;

import blmfltr.splchk.exceptions.InitializationException;

/**
 * Abstract class that has an implementation for calculating 8 different hashes for each string
 */
public abstract class StringBloomFilter implements BloomFilter {

    //With  his implementation we are limiting the max. number of hashes to 8
    static int MAX_HASHES = 8;

    //Array of prime numbers to compute 8 different hashes
    static  int[] HASH_FACTOR = new int []{31, 41, 37, 53, 61,67,43,59};

    //Size of the bit array
    int sizeOfFilter = 0;

    //No of elements that are added to the array
    int noOfElementsAdded = 0;

    //No of hashes for a particular filter
    int noOfHashes;

    //total number of words that can be added to the filter
    int sizeOfData;


    /**
     * Constructor that computes the size of filter and the no of hashes based on number of strings that can be added
     * and false positive probability.
     *
     * @param size
     * @param falsePositiveProbability
     * @throws InitializationException
     */
    protected StringBloomFilter(int size, double falsePositiveProbability) throws InitializationException {
        if(size == 0 || falsePositiveProbability == 0){
            throw new InitializationException("Invalid input");
        }
        this.sizeOfData = size;
        this.sizeOfFilter = computeSizeOfFilter(falsePositiveProbability);
        noOfHashes = computeNoOfHashes();
    }

    /**
     * Computes the number of Hashes required based on the formula:
     * size of filter * ln(2) / no of strings to be added
     *
     * @return
     */
    private int computeNoOfHashes() {
        int hashes = 0;
        if(sizeOfData > 0 ) {
            hashes = (int)  (sizeOfFilter * Math.log(2) / sizeOfData);
        }
        return hashes > MAX_HASHES ? MAX_HASHES : hashes;
    }

    /**
     * Computes the size of the bit array with the formular:
     * Absolute value of (No of strings to be added * ln(false positive Probability)) /(ln 2)^2
     *
     * @param falsePositiveProbability
     * @return
     */
    private int computeSizeOfFilter( double falsePositiveProbability) {
        if(sizeOfData > 0){
            return (int) Math.abs((sizeOfData * Math.log(falsePositiveProbability) / (Math.log(2)*Math.log(2))));
        }
        return 0;
    }

    /**
     * Generates multiple hashes for the input String
     *
     * @param input
     * @return
     */
    protected int[] hashes(String input){
        int[] hashes = new int[noOfHashes];
        for(int i=0;i<noOfHashes; i++){
            hashes[i] = hash(input,i);
        }
        return hashes;
    }

    /**
     * Generates hash based on a prime number
     *
     * @param input
     * @param index
     * @return
     */
    private int hash(String input, int index){
        int hash = 0;
        int powers = 1;
        for(int i = 0; i<input.length(); i++){
            hash = (hash + (input.charAt(i)+1) * powers)%sizeOfFilter;
            powers = (powers*HASH_FACTOR[index])%sizeOfFilter;
        }
        return hash;
    }

    /**
     * Returns the number of elements added to the filter
     *
     * @return
     */
    public int getNoOfElementsAdded() {
        return noOfElementsAdded;
    }

}
