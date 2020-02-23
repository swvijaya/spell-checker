package blmfltr.splchk.service;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;
import blmfltr.splchk.filter.StringBloomFilter;
import blmfltr.splchk.util.FileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SpellCheckService {


    @Value("${dictionaryPath}")
    String dictionaryPath;

    private StringBloomFilter filter;

    public void init() throws InitializationException {
        List<String> words = Collections.emptyList();
        try{
            words = FileReader.readFileIntoList(this.dictionaryPath);
        }catch (IOException ioException){
            ioException.printStackTrace();
            throw new InitializationException("Unable to read dictionary File");
        }
        filter = new StringBloomFilter(words.size(), .03);
        for(String word:words){
            filter.add(word);
        }
    }

    public ArrayList<String> spellCheck(ArrayList<String> input) throws ProcessingException {
        if(filter == null || filter.getWordsAdded()  == 0){
            throw new ProcessingException("No words added");
        }
        ArrayList<String> output = null;
        if(!CollectionUtils.isEmpty(input)){
            output = new ArrayList<String>();
            for(String word:input ){
                if(!validWord(word) || !filter.mightContain(word)){
                    output.add(word);
                }
            }
        }
        return output;


    }

    private boolean validWord(String word){
        //TODO: Validate if word is made up of only alphabets
        return true;
    }
}
