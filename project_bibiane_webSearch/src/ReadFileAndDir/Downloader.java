package ReadFileAndDir;

import java.io.*;
import java.net.*;
public class Downloader
{

    public static String delHtmlTags(String htmlStr) {

        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        String htmlRegex="<[^>]+>";
        String spaceRegex = "\\s*|\t|\r";

        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getTextFromHtml(String htmlStr) {
        htmlStr = delHtmlTags(htmlStr);
        htmlStr = htmlStr.replaceAll(" ", "");
        return htmlStr;
    }
    public static String getHTLFromFile(File f) throws IOException {
        StringBuffer text=new StringBuffer("");
        char[] buffer=new char[1024];
        int len=0;
        if(f.getAbsoluteFile().getName().contains(".html"))
        {
            FileReader reader=new FileReader(f);
            while((len=reader.read(buffer))!=-1)
            {
                text.append(new String(buffer,0,len));
            }

        }
        return text.toString();
    }
    public static void printPathRecusive(File path) throws IOException {
        if(path.isDirectory())
        {
            System.out.println(path.getAbsolutePath()+" is a directory");
            File[] subDirectory=path.listFiles();
            assert subDirectory != null;
            for(File f :subDirectory)
            {
                printPathRecusive(f);
            }
        }else
        {
//            System.out.println(path.getAbsolutePath()+" is a file");
            //System.out.println( path.getAbsolutePath().toString().replace(".txt",".html"+" is a file"));
            System.out.println(getTextFromHtml(getHTLFromFile(path)));
        }


    }
    public static void main(String[] args) throws IOException {

        File f=new File("Dir/index.html");

        printPathRecusive(f);



    }
}
