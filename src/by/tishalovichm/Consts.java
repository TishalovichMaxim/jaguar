package by.tishalovichm;

import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Consts {
    public static final String JAGUAR_FILE = "Jaguarfile.json";
    public static final Path JAGUAR_FILE_PATH = Path.of(JAGUAR_FILE);
    public static final String BUILD_FILE = "Buildfile";
    public static final String MAIN_CLASS_CODE = """
        public class Main {
            public static void main(String[] args) {
                
            }
        }
        """;
    public static final Path SRC_DIR = Path.of("src");
    public static final Path BUILD_DIR = Path.of("build");
    public static final Path LIBS_DIR = Path.of("libs");
    public static final Duration PROCESS_TIMEOUT = Duration.of(3_500L, ChronoUnit.MILLIS);
}
