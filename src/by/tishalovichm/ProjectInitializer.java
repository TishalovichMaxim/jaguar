package by.tishalovichm;

import by.tishalovichm.data.MavenCoordinates;
import by.tishalovichm.data.ProjectInfo;
import by.tishalovichm.factories.UtilsFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ProjectInitializer {

    private static final Utils utils = UtilsFactory.UTILS;

    private String createPackageLine(String[] dirsNames) {
        return "package %s;".formatted(String.join(".", dirsNames));
    }

    public void initProject(Path projectPath) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Input group id:");
            String groupId = scanner.nextLine();

            System.out.println("Input artifact id:");
            String artifactId = scanner.nextLine();

            System.out.println("Input version:");
            String version = scanner.nextLine();

            var coords = new MavenCoordinates(groupId, artifactId, version);
            var projectInfo = new ProjectInfo(coords, List.of());
            utils.writeProjectInfo(projectInfo);

            String[] mainClassPackageParts = utils.getMainClassPackageParts(coords.groupId(), coords.artifactId());

            Path mainClassPath = Path.of("src", mainClassPackageParts).resolve("Main.java");
            mainClassPath.getParent().toFile().mkdirs();
            Files.createFile(mainClassPath);

            String mainClassCode = String.format(
                "%s\n\n%s", createPackageLine(mainClassPackageParts), Consts.MAIN_CLASS_CODE);
            Files.writeString(mainClassPath, mainClassCode);

            Files.createDirectory(projectPath.resolve(Path.of("build")));
            Files.createDirectory(projectPath.resolve(Path.of("libs")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
