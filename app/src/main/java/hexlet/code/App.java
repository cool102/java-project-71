package hexlet.code;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two files and shows a difference.")
public class App implements Callable<Integer> {
    @Option(names = {"-f", "--format"}, description = "The format")
    private String formatName = "plain";
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
        System.out.println("Hello World!");
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        String result = Differ.generate(filepath1, filepath2, formatName);
        System.out.println(result);
        return 0;
    }

}
