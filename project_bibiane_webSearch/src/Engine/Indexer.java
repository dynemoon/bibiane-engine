package Engine;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Indexer {
    public static void indexerWriter(String fileToIndex) throws IOException {
        // String  indexedFile = null;
        //  Path fileToIndexPath = Paths.get(fileToIndex);
        //Path indexedPath = Paths.get(indexedFile);
        // FSDirectory dir = FSDirectory.open(index);


        //creating index
        //Directory index = new RAMDirectory();
        FSDirectory index = FSDirectory.open(Paths.get("index"));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(index, config);
        Document document = new Document();


        document.add(new TextField("content", fileToIndex, Field.Store.YES));

        indexWriter.addDocument(document);
        indexWriter.close();


    }

    public static IndexSearcher search() throws IOException {
        Directory dir = FSDirectory.open(Paths.get("index"));
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        return indexSearcher;


    }
    public static  void query(Integer id, IndexSearcher searcher) throws IOException, ParseException {

        QueryParser queryParser = new QueryParser("id",new StandardAnalyzer());
        Query idQuery = queryParser.parse(id.toString());
        TopDocs hits = searcher.search(idQuery, 10);
        System.out.println("results" + hits);

    }

    public static String readFile() throws FileNotFoundException, IOException {
        StringBuffer sb = new StringBuffer("");
        FileInputStream input = new FileInputStream("htmlDum/W3Schools Offline Latest 2018 [Clone by Md Maruf Adnan Sami]/about/about_advert.html");
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = input.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, len));
        }
        input.close();
        return sb.toString();
    }

    public static void regularExpression() throws IOException {
        String str = readFile();
        String strRegEx = "<[^>]*>";
        System.out.println(str.replaceAll(strRegEx, ""));
    }


    public static void main(String[] args) throws IOException, ParseException {
        //IndexSearcher indexSearcher = search();
        //query(1,indexSearcher);
         regularExpression();
    }
}