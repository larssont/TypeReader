import java.util.List;
import java.util.Map;
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
            String[] textLineWords = getWords(line);
            String[] inputWords = parseInputWords();

            for (int i = 0; i < textLineWords.length; i++) {
                if (inputWords.length < i) break;
                if (textLineWords[i].equals(inputWords[i])) {
                    correctWords++;
                }
                totalWords++;
            }
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
