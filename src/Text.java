import java.io.*;
import java.util.ArrayList;

public class Text implements Serializable {

    private static final String TEXT_FILE_NAME = "text.txt";
    private static final String TEXT_OBJ_NAME = "obj.txt";

    private final File textDir;
    private final File textFile;
    private final File objFile;
    private final String name;

    private int wordTotal = 0;
    private int wordsCorrect = 0;
    private int wordsCompleted = 0;

    private int linePosition;

    public Text(File textRootDir, String name, String inputFilePath) throws IOException {

        File textDir = FileProcessor.newSubFile(textRootDir, name);
        File textFile = FileProcessor.newSubFile(textDir, TEXT_FILE_NAME);
        File objFile = FileProcessor.newSubFile(textDir, TEXT_OBJ_NAME);

        textDir.mkdir();
        textFile.createNewFile();

        this.name = name;
        this.textDir = textDir;
        this.textFile = textFile;
        this.objFile = objFile;

        save(inputFilePath);
    }

    private void save(String inputFilePath) {
        FileProcessor.copyFileContents(inputFilePath, textFile.getAbsolutePath());

        calcProps();
    }

    public void addProgress(int correctWords, int completedWords, int linePosition) {
        this.wordsCorrect += correctWords;
        this.wordsCompleted += completedWords;
        this.linePosition += linePosition;
    }

    public int getLinePosition() {
        return linePosition;
    }

    public File getTextDir() {
        return textDir;
    }

    public File getTextFile() {
        return textFile;
    }

    public File getObjFile() {
        return objFile;
    }

    public static String getTextObjName() {
        return TEXT_OBJ_NAME;
    }

    public int getWordTotal() {
        return wordTotal;
    }

    public int getWordsCompleted() {
        return wordsCompleted;
    }

    public int getWordsCorrect() {
        return wordsCorrect;
    }

    public int getIncorrectWords() {
        return wordsCompleted - wordsCorrect;
    }

    public float getCompletionPercentage() {
        float percentage = ((float) wordsCompleted /(float) wordTotal)*100;
        return Double.isNaN(percentage) ? 0 : percentage;
    }

    public float getCorrectPercentage() {
        float percentage = (float) wordsCorrect /(float) wordsCompleted*100;
        return Double.isNaN(percentage) ? 0 : percentage;
    }

    private void calcProps() {
        ArrayList<String> lines = renderText();
        int a = 0;

        for (String s : lines) {
            a += s.split(" ").length;
        }

        wordTotal = a;
    }

    public ArrayList<String> renderText() {

        ArrayList<String> output = new ArrayList<>();
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(textFile));
            while( (line = br.readLine()) != null){
                if (line.trim().length() > 0) {
                    output.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public String toString() {
        return name;
    }
}
