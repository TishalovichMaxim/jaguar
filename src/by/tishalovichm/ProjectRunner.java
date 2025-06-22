package by.tishalovichm;

import by.tishalovichm.data.MavenCoordinates;
import by.tishalovichm.data.ProjectInfo;
import by.tishalovichm.factories.UtilsFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProjectRunner {

    private final Utils utils = UtilsFactory.UTILS;

    public void run(Path project) {
        Path jaguarfilePath = project.resolve(Consts.JAGUAR_FILE);

        ProjectInfo projectInfo;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jaguarfileContent = Files.readString(jaguarfilePath);
            projectInfo = objectMapper.readValue(jaguarfileContent, ProjectInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MavenCoordinates mavenCoordinates = projectInfo.mavenCoordinates();
        String[] allDirs = utils.getMainClassPackageParts(mavenCoordinates.groupId(),
            mavenCoordinates.artifactId());

        String mainClassPackagePrefix = String.join(".", allDirs);
        String command = """
            java -cp "build/;libs/*" %s.Main
            """.formatted(mainClassPackagePrefix);

        ProcessBuilder processBuilder = new ProcessBuilder(
            command.split("\\s")
        );

        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            process.waitFor(Consts.PROCESS_TIMEOUT);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
