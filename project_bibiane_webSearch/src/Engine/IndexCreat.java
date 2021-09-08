package Engine;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.IOException;
import java.nio.file.Paths;

public class IndexCreat {

    public IndexWriter indexer (String filePath) throws IOException {

        //directory will contain the indexes
        Directory indexerDirectory = FSDirectory.open(Paths.get(filePath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);

        //creating index
        IndexWriter indexWriter = new IndexWriter(indexerDirectory,conf);

        return indexWriter;

    }

    public Document creatingDocument(){
    Document document = new Document();


    }


}
