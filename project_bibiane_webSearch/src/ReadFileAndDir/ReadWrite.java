package ReadFileAndDir;
import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReadWrite {
     static  ArrayList<File> fileSave = new ArrayList<>();
    public static void fileVisit(String dir) throws Exception {
          // array list to  store files


        File directory = new File(dir);
//

        // visiting loop ending with extension .html
            if(  directory.isDirectory()){
//                assert temp != null;
                File []temp   = directory.listFiles();
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

   // reading file by its path

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

  public static void getTextFromHtml() throws IOException {
       File f=null;
        for(int i=0;i<fileSave.size();i++)
        {
            f=fileSave.get(i);
            FileReader reader=new FileReader(f);
            char[] buffer=new char[1024];
            int len=0;
           String allTextNoNosie = "";
           // StringBuffer allTextNoNosie = new StringBuffer();
            while((len=reader.read(buffer))!=-1)
            {
                allTextNoNosie += new String(buffer,0,len);
            }
            System.out.println(allTextNoNosie);
            String targetPath=f.getAbsolutePath().replace(".html",".txt");
            FileOutputStream fos=new FileOutputStream(targetPath);
            fos.write(allTextNoNosie.getBytes());
            fos.close();

        }




    }

    public static void deleFile(String dir){


        File directory = new File(dir);
//

        // visiting loop ending with extension .html
        if(  directory.isDirectory()){
//                assert temp != null;
            File []temp   = directory.listFiles();
            if(temp.length>0)
            {
                for (File list :temp) {
                    if (list.getAbsolutePath().endsWith(".txt")) {

                        System.out.println("file delete"+  list.getAbsolutePath());
                    } else if ( list.isDirectory()) {
                        deleFile(list.getAbsolutePath());
                    }

                }




            }
        }




    }
    public static void main(String[] args) throws Exception {
        deleFile("C:\\Users\\dynemoon\\Desktop\\htmlDum\\W3Schools");



        


    }
}
