package by.tishalovichm;

import by.tishalovichm.data.MavenCoordinates;
import by.tishalovichm.data.ProjectInfo;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ObjectArrays;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    public String[] getMainClassPackageParts(String groupId, String artifactId) {
        String[] groupDirs = groupId.split("\\.");
        String[] artifactDirs = artifactId.split("\\.");
        return ObjectArrays.concat(groupDirs, artifactDirs, String.class);
    }

    public ProjectInfo readProjectInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jaguarfileContent = Files.readString(Consts.JAGUAR_FILE_PATH);
            return objectMapper.readValue(jaguarfileContent, ProjectInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeProjectInfo(ProjectInfo projectInfo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
            prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

            objectMapper.writer(prettyPrinter).writeValue(new File(Consts.JAGUAR_FILE), projectInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDependencyStr(MavenCoordinates mavenCoordinates) {
        return "%s:%s:%s".formatted(mavenCoordinates.groupId(),
            mavenCoordinates.artifactId(), mavenCoordinates.version());
    }

}
