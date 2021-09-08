package Utility;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessingFile {

  private static ArrayList <File> listOfFiles = new ArrayList<>();
       private static File targetFile;


    public ProcessingFile(File targetFile){
        this.targetFile=targetFile;
    }


    // remove all html tags and javaScrip codes
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


    public static String getHTLTextFromFile() throws IOException {

        StringBuffer text = new StringBuffer("");

        char[] buffer=new char[1024];
        int len=0;
        if(targetFile.getAbsoluteFile().getName().contains(".html")) {
            FileReader reader=new FileReader(targetFile);
            while((len=reader.read(buffer))!=-1)
            {
                text.append(new String(buffer,0,len));
            }

        }
        return text.toString();
    }

    public static void RemoveNoise(){

    }



}
