package by.tishalovichm;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FilesCollector {

    public List<Path> collectFiles(
        Path projectPath,
        Function<File, Boolean> filter
    ) {
        File rootFile = projectPath.toFile();
        if (!rootFile.isDirectory()) {
            throw new IllegalArgumentException();
        }

        List<Path> res = new ArrayList<>();
        File[] nestedFiles = rootFile.listFiles();
        if (nestedFiles == null) {
            throw new RuntimeException("An error occurred through files collecting");
        }
        for (File f : nestedFiles) {
            if (f.isDirectory()) {
                List<Path> filesToCompile = collectFiles(f.toPath(), filter);
                res.addAll(filesToCompile);
            } else {
                if (filter.apply(f)) {
                  res.add(f.toPath());
                }
            }
        }

        return res;
    }

    public List<Path> getFilesToCompile(Path srcDir) {
        return collectFiles(srcDir, f -> f.toString().endsWith(".java"));
    }

    public List<Path> getFilesToPackage(Path srcDir) {
        return collectFiles(srcDir, f -> f.toString().endsWith(".class"));
    }

}
