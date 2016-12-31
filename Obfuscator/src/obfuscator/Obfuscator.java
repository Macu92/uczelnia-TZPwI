/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

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
        indexStart = fileString.indexOf("//");
        if (indexStart != -1) {
            return true;
        }
        return false;
    }

    private String deleteFirstCommentOccurence(String fileString) {
        Integer indexStart = fileString.indexOf("/*");
        Integer indexEnd = fileString.indexOf("*/");
        if (indexStart != -1 && indexEnd != -1) {
            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
        }
        indexStart = fileString.indexOf("/*");
        if (indexStart != -1) {
            indexEnd = fileString.indexOf("\\n", indexStart);
            fileString = fileString.replace(fileString.substring(indexStart, indexEnd + 2), "");
        }
        return fileString;
    }

}
