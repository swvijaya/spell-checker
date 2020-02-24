package blmfltr.splchk.service;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;
import blmfltr.splchk.factory.BloomFilterFactory;
import blmfltr.splchk.filter.BloomFilter;
import blmfltr.splchk.filter.EnglishBucketBloomFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that loads the dictionary into the bloom filter and determines whether a word is present in the
 * dictionary or not.
 */
@Service
public class SpellCheckService {


    @Value("${dictionaryPath}")
    private String dictionaryPath;

    @Value("${falsePositiveProbability}")
    private double falsePositiveProbability;

    private BloomFilter filter;

    /**
     * Initializer method to load the dictionary of words into the bloom filter
     *
     * @throws InitializationException
     */
    @PostConstruct
    public void init() throws InitializationException {
        System.out.println("Initializing Filter.....");
        List<String> words = Collections.emptyList();
        try{
            words = Files.readAllLines(Paths.get(dictionaryPath));
        }catch (IOException ioException){
            throw new InitializationException("Unable to read dictionary File");
        }
        filter = BloomFilterFactory.createBloomFilter("English",words.size(), falsePositiveProbability);

        for(String word: words){
            filter.add(word);
        }
        System.out.println("Intialization Complete!");

    }


    /**
     * Takes an array of input words to validate. Validates them against the bloom filter. Adds them to the ouput
     * if word is ont present in the filter.
     *
     * @param input
     * @return
     * @throws ProcessingException
     */
    public ArrayList<String> spellCheck(ArrayList<String> input) throws ProcessingException {
        if(filter == null ){
            throw new ProcessingException("Filter not initialized");
        }
        ArrayList<String> output = null;
        if(!CollectionUtils.isEmpty(input)){
            output = new ArrayList<String>();
            for(String word:input ){
                if (!filter.mightContain(word.toLowerCase())){
                    output.add(word);
                }
            }
        }
        return output;


    }




}
