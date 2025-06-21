package by.tishalovichm;

import java.io.IOException;
import java.nio.file.Path;

public class CliProcessor {

    public void process(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            return;
        }

        String command = args[0];

        switch (command) {
            case "init" -> {
                ProjectInitializer projectInitializer = Factory.PROJECT_INITIALIZER;
                projectInitializer.initProject(Path.of("."));
            }
            case "build" -> {
                ProjectCompiler projectCompiler = Factory.PROJECT_COMPILER;
                projectCompiler.compileUsingBuildfile();
            }
            case "run" -> {
                ProjectRunner projectRunner = Factory.PROJECT_RUNNER;
                projectRunner.run(Path.of("."));
            }
        }
    }

}
