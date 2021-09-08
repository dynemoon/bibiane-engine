package Utility;

import java.io.File;
import java.util.ArrayList;

public class ReadTextFile {

    static ArrayList<File> getTextFile = new ArrayList<>();

    public static void fileVisit(String dir) {
        // array list to  store files
        File directory = new File(dir);
        // visiting loop ending with extension .html
        if (directory.isDirectory()) {
//                assert temp != null;
            File[] temp = directory.listFiles();
            assert temp != null;
            if (temp.length > 0) {
                for (File list : temp) {
                    if (list.getAbsolutePath().endsWith(".txt")) {
                        getTextFile.add(list);
                    } else if (list.isDirectory()) {
                        fileVisit(list.getAbsolutePath());
                    }
                }
            }
        }
    }
}






