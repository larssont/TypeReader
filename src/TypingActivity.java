import java.util.ArrayList;
import java.util.Scanner;

public class TypingActivity {

    private Text text;

    public TypingActivity(Text text) {
        this.text = text;
    }

    public String start() {

        ArrayList<String> textLines = text.renderText();

        int totalWords = 0;
        int correctWords = 0;

        for (String line: textLines) {
            produceOutput(line);
            String[] textWords = line.split(" ");
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

        text.addProgress(correctWords, totalWords);
        return String.format("Total: %s\nCorrect: %s", totalWords, correctWords);
    }

    private void produceOutput(String out) {
        System.out.println(out);
    }

    private String[] parseInputWords() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();

        return in.split(" ");
    }
}
