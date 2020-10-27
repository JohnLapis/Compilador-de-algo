class Template {
    public static String createTemplate(String text) {
        Pattern variablePattern = Pattern.compile("\$\\{(?<var>.*?)\\}");
        Matcher matcher = variablePattern.matcher(text);
        while (matcher.find()) {
            text.replaceFirst(matcher.group(), Scanner.getVar(matcher.group("var")));
        }
    }
}
