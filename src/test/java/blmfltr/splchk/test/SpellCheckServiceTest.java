package blmfltr.splchk.test;

import blmfltr.splchk.exceptions.InitializationException;
import blmfltr.splchk.exceptions.ProcessingException;
import blmfltr.splchk.service.SpellCheckService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class SpellCheckServiceTest {


    SpellCheckService spellCheckService;

    @Before
    public void setUp(){
        spellCheckService = new SpellCheckService();
    }

    //Test init with bad file
    @Test
    public void testInitWithBadFile(){
        Field field = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        field.setAccessible(true);
        ReflectionUtils.setField(field, spellCheckService, "no_file.txt");
        Exception exception = Assertions.assertThrows(InitializationException.class, () ->spellCheckService.init());
        Assertions.assertEquals("Unable to read dictionary File", exception.getMessage());

    }


    //Test init with empty file
    @Test
    public void testInitWithIncorrectFile(){
        Field field = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        field.setAccessible(true);
        ReflectionUtils.setField(field, spellCheckService, "empty_words_test.txt");
        Exception exception = Assertions.assertThrows(InitializationException.class, () ->spellCheckService.init());
        Assertions.assertEquals("Invalid input", exception.getMessage());

    }

    //Test spellCheck with correct file and right data
    @Test
    public void testSpellCheckNoInit() throws ProcessingException, InitializationException {
        Field field = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        field.setAccessible(true);
        ReflectionUtils.setField(field, spellCheckService, "words_test.txt");
        ArrayList<String> input = new ArrayList<String>();
        input.add("test");
        input.add("best");
        Exception exception = Assertions.assertThrows(ProcessingException.class,() -> spellCheckService.spellCheck(input));
        Assertions.assertEquals("Filter not initialized", exception.getMessage());

    }

    //Test spellCheck with correct file and right data
    @Test
    public void testSpellCheckWithNoProbability() throws ProcessingException, InitializationException {
        Field field = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        field.setAccessible(true);
        ReflectionUtils.setField(field, spellCheckService, "words_test.txt");
        Exception exception = Assertions.assertThrows(InitializationException.class, () ->spellCheckService.init());
        Assertions.assertEquals("Invalid input", exception.getMessage());

    }

    //Test with correct word
    @Test
    public void testSpellCheckWithCorrectFile() throws ProcessingException, InitializationException {
        Field path = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        path.setAccessible(true);
        ReflectionUtils.setField(path, spellCheckService, "words_test.txt");
        Field prob = ReflectionUtils.findField(SpellCheckService.class, "falsePositiveProbability");
        prob.setAccessible(true);
        ReflectionUtils.setField(prob, spellCheckService, 0.002);
        spellCheckService.init();
        ArrayList<String> input = new ArrayList<String>();
        input.add("test");
        input.add("best");
        Assertions.assertEquals(Arrays.asList(), spellCheckService.spellCheck(input));

    }

    //Test spellCheck with correct file and incorrect word
    @Test
    public void testSpellCheckWithInCorrectWord() throws ProcessingException, InitializationException {
        Field path = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        path.setAccessible(true);
        ReflectionUtils.setField(path, spellCheckService, "words_test.txt");
        Field prob = ReflectionUtils.findField(SpellCheckService.class, "falsePositiveProbability");
        prob.setAccessible(true);
        ReflectionUtils.setField(prob, spellCheckService, 0.002);
        spellCheckService.init();
        ArrayList<String> input = new ArrayList<String>();
        input.add("test");
        input.add("most");
        Assertions.assertEquals(Arrays.asList("most"), spellCheckService.spellCheck(input));

    }

    //Test spellCheck with correct file and uppercase word
    @Test
    public void testSpellCheckWithUpperCaseWord() throws ProcessingException, InitializationException {
        Field path = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        path.setAccessible(true);
        ReflectionUtils.setField(path, spellCheckService, "words_test.txt");
        Field prob = ReflectionUtils.findField(SpellCheckService.class, "falsePositiveProbability");
        prob.setAccessible(true);
        ReflectionUtils.setField(prob, spellCheckService, 0.002);
        spellCheckService.init();
        ArrayList<String> input = new ArrayList<String>();
        input.add("TeSt");
        input.add("MOst");
        Assertions.assertEquals(Arrays.asList("MOst"), spellCheckService.spellCheck(input));

    }

    //Test spellCheck with correct file and special char word
    @Test
    public void testSpellCheckWithSpecialCharWord() throws ProcessingException, InitializationException {
        Field path = ReflectionUtils.findField(SpellCheckService.class, "dictionaryPath");
        path.setAccessible(true);
        ReflectionUtils.setField(path, spellCheckService, "words_test.txt");
        Field prob = ReflectionUtils.findField(SpellCheckService.class, "falsePositiveProbability");
        prob.setAccessible(true);
        ReflectionUtils.setField(prob, spellCheckService, 0.002);
        spellCheckService.init();
        ArrayList<String> input = new ArrayList<String>();
        input.add("TeSt");
        input.add("MO$t");
        Assertions.assertEquals(Arrays.asList(), spellCheckService.spellCheck(input));

    }
}
