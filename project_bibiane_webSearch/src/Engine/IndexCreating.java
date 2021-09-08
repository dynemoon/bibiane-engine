package Engine;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;


public class IndexCreating {
   static String indexPath="index";//output of indexed file
    static String fileToIndexPath="Dir";//inputFile to index

    public static IndexWriter creatingIndex() throws IOException {

        //lucene Directory instance
        Directory index = FSDirectory.open(Paths.get(indexPath));
        //
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //Creates a new index if one does not exist, otherwise it opens the index and documents will be appended.
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        IndexWriter indexWriter = new IndexWriter(index,config);

     return indexWriter;

    }

    public static void indexerDoc() throws IOException {

        IndexWriter indexWriter = creatingIndex();
        Document document = new Document();
        ReadDirectory readDirectory = new ReadDirectory();
        readDirectory.readTextFile(fileToIndexPath);

        List<File> list  = readDirectory.allFile;

        for(File file : list){
            String url = file.getAbsolutePath();
            document.add(new StringField("url",url, Field.Store.YES));
           // System.out.println(url + ":: url added");

            Reader read = new FileReader(file);

            document.add(new TextField("content",new String(Files.readAllBytes(file.toPath())), Field.Store.YES));
            System.out.println("url::"+file.toPath());


           //indexWriter.addDocument(document);
           indexWriter.updateDocument(new Term("utl",url),document);


        }
        System.out.println("document adding completed..... ");
        indexWriter.flush();
        indexWriter.close();

    }

    public static IndexSearcher search() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader indexReader = DirectoryReader.open(directory);

        return new IndexSearcher(indexReader);

    }

    public static TopDocs searchContent(String content , IndexSearcher searcher ) throws ParseException, IOException {
        //creating a query;
        QueryParser queryPars = new QueryParser("content",new StandardAnalyzer());
        Query query = queryPars.parse(content);

        //search the index


        TopDocs hints = searcher.search(query,10);

        return hints;
    }

    public static void searchContentWithHighlight(String content) throws ParseException, IOException, InvalidTokenOffsetsException {
             int hitsPerPage =0;
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader indexReader = DirectoryReader.open(directory);
       IndexSearcher searcher =search();
      QueryParser queryParser = new QueryParser("content",new StandardAnalyzer());
      Query query = queryParser.parse(content);
      TopDocs hints =searcher.search(query,10);


        //Uses HTML &lt;B&gt;&lt;/B&gt; tag to highlight the searched terms
        Formatter formatter = new SimpleHTMLFormatter();

        //It scores text fragments by the number of unique query terms found
        //Basically the matching score in layman terms

        QueryScorer scorer = new QueryScorer(query);

       //used to markup highlighted terms found in the best sections of a text
        Highlighter highlighter = new Highlighter(formatter, scorer);


        //It breaks text up into same-size texts but does not split up spans
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 10);

        highlighter.setTextFragmenter(fragmenter);

        for(int i =0 ; i<hints.scoreDocs.length;i++){
            int docId = hints.scoreDocs[i].doc;
            Document document = searcher.doc(docId);
            String url= document.get("url");
            String contents = document.get("content");


            TokenStream stream = TokenSources.getAnyTokenStream(indexReader,docId,"content",new StandardAnalyzer());

            String [] output =highlighter.getBestFragments(stream,contents,hitsPerPage);
            for (String res : output){
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println(res);
            }


        }


       directory.close();

    }


    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
//        indexerDoc();

//        TopDocs hints = searchContent("satellite",searcher);
//
//        System.out.println("number result::"+ hints.totalHits);
//
//        for(ScoreDoc sd : hints.scoreDocs){
//            Document document =searcher.doc(sd.doc);
//            System.out.println("url: "+ document.get("url") +" , score : " +sd.score);
//        }

        searchContentWithHighlight("satalite tv");






    }
}

