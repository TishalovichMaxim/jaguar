package by.tishalovichm;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        CliProcessor cliProcessor = new CliProcessor();
        cliProcessor.process(args);
    }

}
