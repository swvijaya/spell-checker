package blmfltr.splchk.controller;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;

import blmfltr.splchk.service.SpellCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;


/**
 * Controller class to send request from the UI
 */
@RestController
public class SpellCheckController {

    @Autowired
    SpellCheckService spellCheckerService;


    /**
     * The post method that receives the list of words and returns the list of invalid words as determined byt he bloom filter
     *
     * @param input
     * @return
     */
    @PostMapping(path = "/spellcheck", consumes = "application/json", produces = "application/json")
    public ArrayList<String> spellCheck(@RequestBody ArrayList<String> input){
        ArrayList<String> incorrectWords = null;
        System.out.println(input);
        try {

            incorrectWords = spellCheckerService.spellCheck(input);
        }catch(ProcessingException exp){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing the words",exp);
        }
        System.out.println(incorrectWords);
        return incorrectWords;
    }

}
