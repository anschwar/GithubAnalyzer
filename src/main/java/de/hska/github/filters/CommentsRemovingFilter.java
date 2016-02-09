package de.hska.github.filters;


public class CommentsRemovingFilter implements Filter {

    @Override
    public String apply(String input) {
        String toReturn = input;
        // remove multiple line comment
        if (toReturn.startsWith("*")) return null;
        // check first single line comments
        if (toReturn.contains("//")) {
            int startComment = toReturn.indexOf("//");
            toReturn = toReturn.substring(0, startComment);
        }
        // then check multiple line comment applied to one line
        toReturn = toReturn.replaceAll("/.*/", "").trim();
        // check at least if its the start of a multiple line comment
        if (toReturn.startsWith("/*")) return null;
        return toReturn;
    }
}
