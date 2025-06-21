package by.tishalovichm;

import com.google.common.collect.ObjectArrays;

public class Utils {

    public String[] getMainClassPackageParts(String groupId, String artifactId) {
        String[] groupDirs = groupId.split("\\.");
        String[] artifactDirs = artifactId.split("\\.");
        return ObjectArrays.concat(groupDirs, artifactDirs, String.class);
    }

}
