/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Maciek
 */
public class Obfuscator {

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

    private boolean hasAnyComment(String fileString) {
        Integer indexStart = fileString.indexOf("/*");
        Integer indexEnd = fileString.indexOf("*/");
        if (indexStart != -1 && indexEnd != -1) {
            return true;
        }
//        indexStart = fileString.indexOf("//");
//        if (indexStart != -1) {
//            return true;
//        }
        return false;
    }

    public String deleteFirstCommentOccurence(String fileString) {
        Integer indexStart = fileString.indexOf("/*");
        Integer indexEnd = fileString.indexOf("*/");
        if (!(indexStart <= -1) && !(indexEnd <= -1)) {
            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
        }
//        indexStart = fileString.indexOf("/*");
//        if (!(indexStart <= -1)) {
//            indexEnd = fileString.indexOf("\\n", indexStart);
//            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
//        }
        return fileString;
    }

    public List<String> findFields(String fileString) {
        List<String> fieldsList = new LinkedList<>();
        Pattern pattern = Pattern.compile("\\bprivate(\\s.*)\\s.*");
        Matcher matcher = pattern.matcher(fileString);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        return fieldsList;
    }

    public List<String> findMethdos(String fileString) {
        List<String> methodsList = new LinkedList<>();
        Pattern pattern = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
        Matcher matcher = pattern.matcher(fileString);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        return methodsList;
    }

    public List<String> findClasses(String fileString) {
        List<String> classesList = new LinkedList<>();
        return classesList;
    }
}
