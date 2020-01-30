import java.io.*;

public class Text implements Serializable {

    private File textDir;
    private File textFile;
    private File objFile;

    private String name;
    private static final String TEXT_FILE_NAME = "text.txt";
    private static final String TEXT_OBJ_NAME = "obj.txt";

    private int totalWords;
    private int correctWords;
    private int completedWords;

    public Text(File textRootDir, String name) throws IOException {

        File textDir = FileProcessor.newSubFile(textRootDir, name);
        File textFile = FileProcessor.newSubFile(textDir, TEXT_FILE_NAME);
        File objFile = FileProcessor.newSubFile(textDir, TEXT_OBJ_NAME);

        textDir.mkdir();
        textFile.createNewFile();

        this.name = name;
        this.textDir = textDir;
        this.textFile = textFile;
        this.objFile = objFile;
    }

    public void addProgress(int correctWords, int completedWords) {
        this.correctWords+=correctWords;
        this.completedWords+=completedWords;
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

    public int getTotalWords() {
        return totalWords;
    }

    public int getCompletedWords() {
        return completedWords;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public int getIncorrectWords() {
        return completedWords - correctWords;
    }

    public float getCompletionPercentage() {
        return ((float)completedWords/(float)totalWords)*100;
    }

    public float getCorrectPercentage() {
        return ((float)correctWords/(float)completedWords)*100;
    }

    @Override
    public String toString() {
        return name;
    }
}
