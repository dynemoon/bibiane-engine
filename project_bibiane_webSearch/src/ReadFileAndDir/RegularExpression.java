package ReadFileAndDir;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RegularExpression {

    //deleting html tags
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



}
