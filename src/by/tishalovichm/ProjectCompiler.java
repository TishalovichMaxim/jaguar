package by.tishalovichm;

import by.tishalovichm.factories.Factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ProjectCompiler {

    private final FilesCollector filesCollector = Factory.FILES_COLLECTOR;

    public static Path getBuildfilePath() {
        return Consts.BUILD_DIR_PATH.resolve(Consts.BUILD_FILE);
    }

    public static void createBuildfile() {
        Path buildfilePath = getBuildfilePath();
        buildfilePath.getParent().toFile().mkdirs();

        try {
            buildfilePath.toFile().createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void fillBuildfile() {
        createBuildfile();
        Path buildfilePath = getBuildfilePath();

        StringBuilder sb = new StringBuilder();
        sb.append("-d ./build/");
        sb.append("\n");

        for (Path fileToCompile : filesCollector.getFilesToCompile(Consts.SRC_DIR)) {
            sb.append(fileToCompile.toString());
            sb.append(("\n"));
        }

        try {
            Files.writeString(buildfilePath, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void compileUsingBuildfile() {
        fillBuildfile();
        ProcessBuilder processBuilder = new ProcessBuilder(
            "javac", "@build/%s".formatted(Consts.BUILD_FILE)
        );

        try {
            Process process = processBuilder.start();
            process.waitFor(Consts.PROCESS_TIMEOUT);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void compileUsingFilesCommandEnumeration(Path path) throws IOException, InterruptedException {
        List<Path> filesToCompile = filesCollector.getFilesToCompile(path.resolve(Consts.SRC_DIR));
        StringBuilder sb = new StringBuilder("javac -d ./build/ ");

        for (Path p : filesToCompile) {
            sb.append(p.toString());
            sb.append(' ');
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
            sb.toString().split("\\s")
        );

        Process process = processBuilder.start();
        process.waitFor();
    }

}
