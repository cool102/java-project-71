package hexlet.code;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format=format"}, description = "output format [default: stylish]")
    private String format;

    @Option(names = {"-h", "--help"}, description = "Show this help message and exit.")
    private String help = "Что здесь?";

    @Option(names = {"-V", "--version"}, description = "Print version information and exit.")
    private String version = "ver.1";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
        System.out.println("Hello World!4");
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        //Path fp1 = Paths.get(filepath1);
        //Path fp2 = Paths.get(filepath2);
//

        String res = Differ.generate(filepath1, filepath2);
        System.out.println(res);

        // byte[] fileContents = Files.readAllBytes(file.toPath());
        // byte[] digest = MessageDigest.getInstance(algorithm).digest(fileContents);
        // System.out.printf(algorithm + " hash of " + file.getPath() + ": %0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
        return 0;
    }

}
