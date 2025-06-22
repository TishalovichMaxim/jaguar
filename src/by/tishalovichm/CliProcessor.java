package by.tishalovichm;

import by.tishalovichm.data.MavenCoordinates;
import by.tishalovichm.factories.Factory;

import java.nio.file.Path;
import java.util.Arrays;

public class CliProcessor {

    private MavenCoordinates parseDependencyInfo(String dependencyInfo) {
        String[] parts = dependencyInfo.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Wrong dependency");
        }

        return new MavenCoordinates(parts[0], parts[1], parts[2]);
    }

    public void process(String[] args) {
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
                ProjectCompiler projectCompiler = Factory.PROJECT_COMPILER;
                projectCompiler.compileUsingBuildfile();
                ProjectRunner projectRunner = Factory.PROJECT_RUNNER;
                projectRunner.run(Path.of("."));
            }
            case "install" -> {
                JarDownloader jarDownloader = Factory.JAR_DOWNLOADER;
                if (args.length < 2) {
                    throw new IllegalArgumentException("Dependencies must be specified");
                }

                Arrays.stream(args)
                    .skip(1)
                    .map(this::parseDependencyInfo)
                    .forEach(dep ->
                        jarDownloader.download(dep.groupId(), dep.artifactId(), dep.version())
                    );
            }
            case "clean" -> {
                Factory.CLEANER.clean();
            }
        }
    }

}
