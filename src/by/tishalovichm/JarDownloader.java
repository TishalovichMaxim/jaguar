package by.tishalovichm;

import by.tishalovichm.data.MavenCoordinates;
import by.tishalovichm.data.ProjectInfo;
import by.tishalovichm.factories.UtilsFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JarDownloader {

    private final Utils utils = UtilsFactory.UTILS;

    public void download(
        String groupId,
        String artifactId,
        String version
    ) {
        var mavenCoords = new MavenCoordinates(groupId, artifactId, version);
        ProjectInfo projectInfo = utils.readProjectInfo();

        String dependencyStr = utils.getDependencyStr(mavenCoords);
        if (projectInfo.dependencies() != null && projectInfo.dependencies().contains(dependencyStr)) {
            throw new RuntimeException("Dependency has already been downloaded");
        }

        String groupPath = groupId.replace('.', '/');

        String url = String.format("https://repo.maven.apache.org/maven2/%s/%s/%s/%s-%s.jar",
            groupPath, artifactId, version, artifactId, version);

        Path outputDir = Paths.get("libs");
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectories(outputDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String fileName = String.format("%s-%s.jar", artifactId, version);
        Path outputPath = outputDir.resolve(fileName);

        try (InputStream in = new URI(url).toURL().openStream();
             FileOutputStream out = new FileOutputStream(outputPath.toFile())) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        List<String> newDependencyList;
        if (projectInfo.dependencies() != null) {
            newDependencyList = new ArrayList<>(projectInfo.dependencies());
        } else {
            newDependencyList = new ArrayList<>();
        }
        newDependencyList.add(dependencyStr);
        var newProjectInfo = new ProjectInfo(projectInfo.mavenCoordinates(), newDependencyList);
        utils.writeProjectInfo(newProjectInfo);
    }

}
