package by.tishalovichm;

import java.io.File;

public class Cleaner {

    private void deleteFile(File f) {
        if (!f.delete()) {
            throw new RuntimeException("%s can't be deleted".formatted(f.toString()));
        }
    }

    private void deleteFileRecursively(File f) {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                deleteFile(child);
            }
            deleteFile(f);
        } else {
            deleteFile(f);
        }
    }

    public void clean() {
        deleteFileRecursively(Consts.BUILD_DIR_PATH.toFile());
    }

}
