import java.io.*;

public class FileProcessor {

    private File appDir;
    private File textsDir;
    private File imagesDir;
    private File settingsDir;

    public FileProcessor(String appName, String textsDir, String imagesDir, String settingsDir) {

        String userHome = System.getProperty("user.home");

        this.appDir = new File(formatPath(userHome, appName));

        this.textsDir = newSubFile(appDir, textsDir);
        this.imagesDir = newSubFile(appDir, imagesDir);
        this.settingsDir = newSubFile(appDir, settingsDir);

        createDirs();
    }


    private void createDirs() {
        appDir.mkdir();
        textsDir.mkdir();
        imagesDir.mkdir();
        settingsDir.mkdir();
    }


    public static File newSubFile(File parentDir, String file) {
        return new File(formatPath(parentDir.getAbsolutePath(), file));
    }

    public static String formatPath(String path, String name) {
        return String.format("%s/%s", path, name);
    }

    public static boolean isFileExisting(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static File searchDir(File parentDir, String fileName) {
        File[] files = getFiles(parentDir, fileName);
        return files.length == 0 ? null : files[0];
    }

    public static File[] getFiles(File parentDir, String fileName) {
        return parentDir.listFiles((dir, name) -> name.startsWith(fileName));
    }

    public File getTextDir() {
        return textsDir;
    }

    public static boolean copyFileContents(String inputFilePath, String outputFilePath) {
        try {
            FileReader fr = new FileReader(inputFilePath);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(outputFilePath, true);
            String s;

            while ((s = br.readLine()) != null) { // read a line
                fw.write(String.format("%s\n",s)); // write to output file
                fw.flush();
            }
            br.close();
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
