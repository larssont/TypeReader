import java.util.List;
import java.util.Scanner;

public class TypingActivity {

    private Text text;

    private final String exitKey = "!exit";

    private int wordsCorrect = 0;
    private int wordsCompleted = 0;
    private int linePosition = 0;

    public TypingActivity(Text text) {
        this.text = text;
    }

    public String start() {

        int linePosition = text.getLinePosition();

        List<String> allLines = text.renderText();
        List<String> unfinishedLines = allLines.subList(linePosition, allLines.size());

        processLines(unfinishedLines);
        return end();
    }

    private void processLines(List<String> lines) {

        int i;

        lineLoop:
        for (i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            produceOutput(line);

            String[] textWords = line.split(" ");
            String[] inputWords = parseInputWords();

            for (int j = 0; j < textWords.length; j++) {
                if (inputWords.length >= 1 && inputWords[0].equals(exitKey)) {
                    break lineLoop;
                }
                if (inputWords.length > j) {
                    if (textWords[j].equals(inputWords[j])) {
                        wordsCorrect++;
                    }
                }
            }
            wordsCompleted += textWords.length;
        }

        linePosition += i;
    }

    private String end() {
        text.addProgress(wordsCorrect, wordsCompleted, linePosition);

        FileProcessor.writeObjToFile(text.getObjFile(), text);
        return String.format("Total: %s\nCorrect: %s", wordsCompleted, wordsCorrect);
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
