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

import javax.annotation.PostConstruct;
import java.util.ArrayList;


@RestController
public class SpellCheckController {

    @Autowired
    SpellCheckService spellCheckerService;

    @PostConstruct
    public void init(){
        try {
            spellCheckerService.init();
        } catch(InitializationException exp){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Filter initialization Failed",exp);
        }

    }



    @PostMapping(path = "/spellcheck", consumes = "application/json", produces = "application/json")
    public ArrayList<String> spellCheck(@RequestBody ArrayList<String> input){
        ArrayList<String> incorrectWords = null;
        try {

            incorrectWords = spellCheckerService.spellCheck(input);
        }catch(ProcessingException exp){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing the words",exp);
        }
        return incorrectWords;
    }

}
