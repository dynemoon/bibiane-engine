package Engine;

import java.io.File;
import java.util.ArrayList;

public class ReadDirectory {

     ArrayList <File>allFile = new ArrayList<>();


          public  void readTextFile(String dir){

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
                          if (list.getAbsolutePath().endsWith(".txt")) {
                              allFile.add(list);
                          } else if ( list.isDirectory()) readTextFile(list.getAbsolutePath());
                      }

                  }
              }

          }


}
