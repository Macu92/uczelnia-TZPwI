/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Maciek
 */
public class Obfuscator {
    private Random rnd = new Random();

    public String deleteComments(String fileString) {
        while (hasAnyComment(fileString) != false) {
            fileString = deleteFirstCommentOccurence(fileString);
        }
        return fileString;
    }

    public String deleteWhiteSpaces(String fileString) {
        String noWhite = fileString.replaceAll("\\t", "");
        noWhite = noWhite.replaceAll("\\n", "");
        noWhite = noWhite.replaceAll("\\r", "");
        noWhite = noWhite.replaceAll("\\v", "");
        noWhite = noWhite.replaceAll("\\b", "");
        noWhite = noWhite.replaceAll("\\f", "");
        return noWhite;
    }

    // testtt
    
    private boolean hasAnyComment(String fileString) {
        Integer indexStart = fileString.indexOf("/*");
        Integer indexEnd = fileString.indexOf("*/");
        if (indexStart != -1 && indexEnd != -1) {
            return true;
        }
        indexStart = fileString.indexOf("//");
        if (indexStart != -1) {
            return true;
        }
        return false;
    }

    public String deleteFirstCommentOccurence(String fileString) {
        Integer indexStart = fileString.indexOf("/*");
        Integer indexEnd = fileString.indexOf("*/");
        if (!(indexStart <= -1) && !(indexEnd <= -1)) {
            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
        }
        indexStart = fileString.indexOf("//");
        if (!(indexStart <= -1)) {
            indexEnd = fileString.indexOf(String.format("%n"), indexStart);
            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
        }
        return fileString;
    }
    
    public String changeIdentifiers(String fileString){
        Set<String> valuesToChange =  new HashSet<>();
        List<String> values = findMethdos(fileString);
        valuesToChange.addAll(values);
        values = findFields(fileString);
        valuesToChange.addAll(values);
        valuesToChange.remove("main");
        valuesToChange.remove("");
        for(String s : valuesToChange){
            String generated = generateRandomString();
            fileString = fileString.replaceAll("\\b"+s+"\\b", generated);
        }
        return fileString;
    }

    public List<String> findFields(String fileString) {
        List<String> fieldsList = new LinkedList<>();
        Pattern pattern = Pattern.compile("[^\\\"] *([A-Z]+\\w*(\\<[A-Z]+\\w*\\>)*|int) ([a-z]+[\\w]*) *.*[\\;]+");
        Matcher matcher = pattern.matcher(fileString);
        while (matcher.find() != false) {
           fieldsList.add(matcher.group(3));
        }
        return fieldsList;
    }

    public List<String> findMethdos(String fileString) {
        List<String> methodsList = new LinkedList<>();
        Pattern pattern = Pattern.compile("(public|protected|private|static| )( \\w*\\<*\\w*\\>*\\[*\\]*)* ([a-z]+\\w*) *(\\( *\\w* +([a-z]+\\w*| *|) *\\)|\\(\\)) *\\{+[^;]");
        Matcher matcher = pattern.matcher(fileString);
        while (matcher.find() != false) {
           methodsList.add(matcher.group(3));
           methodsList.add(matcher.group(5));
        }
        return methodsList;
    }

    public List<String> findClasses(String fileString) {
       List<String> classList = new LinkedList<>();
        Pattern pattern = Pattern.compile("(class|\\s)* ([A-Z]+\\w*) *\\{+");
        Matcher matcher = pattern.matcher(fileString);
        while (matcher.find() != false) {
           classList.add(matcher.group(2));
        }
        return classList;
    }
    
    private String generateRandomString(){
        char c =(char)(rnd.nextInt(123-98)+98); 
        return c+UUID.randomUUID().toString().replaceAll("-", "");
    }
}
