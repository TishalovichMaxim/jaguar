package by.tishalovichm;

public class Factory {

    public static final FilesCollector FILES_COLLECTOR = new FilesCollector();

    public static final ProjectCompiler PROJECT_COMPILER = new ProjectCompiler();

    public static final ProjectInitializer PROJECT_INITIALIZER = new ProjectInitializer();

    public static final ProjectRunner PROJECT_RUNNER = new ProjectRunner();

    public static final Utils UTILS = new Utils();

}
