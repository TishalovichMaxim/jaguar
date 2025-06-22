package by.tishalovichm.data;

import java.util.List;

public record ProjectInfo(
    MavenCoordinates mavenCoordinates,
    List<String> dependencies
) {
}
