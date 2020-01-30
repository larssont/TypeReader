import java.util.List;
import java.util.Scanner;

public class TypingActivity {

    private List<String> textLines;

    public TypingActivity(List<String> textLines) {
        this.textLines = textLines;
    }

    public String start() {

        int totalWords = 0;
        int correctWords = 0;

        for (String line: textLines) {
            produceOutput(line);
            String[] textWords = getWords(line);
            String[] inputWords = parseInputWords();

            for (int i = 0; i < textWords.length; i++) {
                if (inputWords.length > i) {
                    if (textWords[i].equals(inputWords[i])) {
                        correctWords++;
                    }
                }
            }
            totalWords += textWords.length;
        }

        return String.format("Total: %s\nCorrect: %s", totalWords, correctWords);
    }

    private void produceOutput(String out) {
        System.out.println(out);
    }

    private String[] parseInputWords() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();

        return getWords(in);
    }

    private String[] getWords(String s) {
        return s.split(" ");
    }
}
