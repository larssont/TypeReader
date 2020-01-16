import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TypeReader {

    private static final String appName = "TypeReader";
    private static boolean exit = false;
    private static FileProcessor fileProcessor;
    private static Map<String, Runnable> commands;

    private static void start() {
        fileProcessor = new FileProcessor(appName);
        commands = new HashMap<>();

        // Populate commands map
        commands.put("add", TypeReader::runCmdAdd);
        commands.put("show", TypeReader::runCmdShow);
        commands.put("exit", TypeReader::runCmdExit);

        // TODO: Read saved books into memory

        run();
    }

    private static void run() {

        showWelcome();

        while (!exit) {
            String in = readInput();
            // Invoke some command
            try {
                commands.get(in).run();
            } catch (NullPointerException e) {
                runCmdInvalid();
            }
        }
    }

    private static String readInput() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine().trim();
        if (in.isEmpty()) {
            System.out.println("Empty input, try again");
            return readInput();
        }
        return in;
    }

    private static void showWelcome() {
        System.out.println("Welcome to TypeReader!");
    }
    private static void runCmdAdd() {
        System.out.println("Enter name of text:");
        String name = readInput();
        System.out.println("Enter full path for text file:");
        String filePath = readInput();

        if (!fileProcessor.isFileExisting(filePath)) {
            String fileAbsolutePath = fileProcessor.getAbsolutePath(filePath);
            String msg = String.format("File doesn't exist, make sure this is the correct path: %s", fileAbsolutePath);
            System.out.println(msg);
            return;
        }
        fileProcessor.addText(name, filePath);
        System.out.println("Text added!");
    }

    private static void runCmdShow() {
        System.out.println("Show command run");
    }

    private static void runCmdExit() {
        System.out.println("Goodbye!");
        exit = true;
    }

    private static void runCmdInvalid() {
        System.out.println("Invalid command, try again.");
    }

    public static void main(String[] args) {
        start();

    }
}
