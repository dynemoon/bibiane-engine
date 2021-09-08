package ReadFileAndDir;

import java.io.*;
import java.util.ArrayList;

public class fileProcessing {
    private static ArrayList<File> fileSave = new ArrayList<>();// stores file that ends with .html
    private static String path = "C:\\Users\\dynemoon\\Desktop\\htmlDum\\W3Schools";

    /*
    read each directory in file;
    select only file with extension ;
    returns a string ();
    * */
    public static void fileVisit(String dir) throws Exception {
        // array list to  store files


        File directory = new File(dir);
//
        // visiting loop ending with extension .html
        if(  directory.isDirectory()){
//                assert temp != null;
            File []temp   = directory.listFiles();
            assert temp != null;
            if(temp.length>0)
            {
                for (File list :temp) {
                    if (list.getAbsolutePath().endsWith(".html")) {
                        fileSave.add(list);
                    } else if ( list.isDirectory()) {
                        fileVisit(list.getAbsolutePath());
                    }
                }

            }
        }

    }
      /*
      read all html.
      read javaScript code.
      read CSS style code.* */
    public static String getHTLFromFile(File f) throws IOException {

        StringBuffer text = new StringBuffer("");

        char[] buffer=new char[1024];
        int len=0;
        if(f.getAbsoluteFile().getName().contains(".html")) {
            FileReader reader=new FileReader(f);
            while((len=reader.read(buffer))!=-1)
            {
                text.append(new String(buffer,0,len));
            }

        }
        return text.toString();
    }

    /*
    remove html tags.
    remove javaScript code
    remove CSS style

     **/
    public static String delHtmlTags(String htmlStr) {


        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        String htmlRegex="<[^>]+>";
        String spaceRegex = "\\s*|\t|\r";

        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        return htmlStr.trim();

    }
    public  static String  readFileDir(File path) throws IOException {

        BufferedReader fileReader = new BufferedReader(new FileReader(path));
        StringBuilder content = new StringBuilder();
        String text ;
        while((text=fileReader.readLine())!=null){

            content.append(text);
            content.append(System.lineSeparator());
        }
        fileReader.close();

        return content.toString();
    }


    public static void removeNoise() throws Exception {

        fileVisit(path);

//
        for (int i =0; i<fileSave.size();i++) {

            String noiseFile = getHTLFromFile(fileSave.get(i));
            String noNoiseFile = delHtmlTags(noiseFile);
            String targetPath =fileSave.get(i).getAbsolutePath().replace(".html",".txt");
            FileOutputStream fileWriter =new FileOutputStream(targetPath);
            fileWriter.write(noNoiseFile.getBytes());
            fileWriter.close();
            System.out.println(targetPath+"::file created.....");

        }




    }



    public static void main(String[] args) throws Exception {
     removeNoise();
    }
}
