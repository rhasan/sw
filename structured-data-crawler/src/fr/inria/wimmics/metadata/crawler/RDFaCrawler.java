package fr.inria.wimmics.metadata.crawler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.deri.any23.Any23;
import org.deri.any23.extractor.ExtractionException;
import org.deri.any23.filter.IgnoreAccidentalRDFa;
import org.deri.any23.http.HTTPClient;
import org.deri.any23.source.DocumentSource;
import org.deri.any23.source.HTTPDocumentSource;
import org.deri.any23.writer.TripleHandler;
import org.openrdf.model.Statement;


import fr.inria.wimmics.any23.writer.StatementWriter;

public class RDFaCrawler {
	final public static String USER_AGENT = "inria-wimmics-crawler-agent";
	
	public List<Statement> crawlFoafRDFaList(String url) throws IOException, URISyntaxException, ExtractionException {
		Any23 runner = new Any23("html-rdfa");
		
		List<Statement> statements = new ArrayList<Statement>();
		
		TripleHandler trpileHandler = new StatementWriter(statements);
		this.extract(runner, url, trpileHandler);
		return statements;
	}
	
	private void extract(Any23 runner, String url, TripleHandler handler) throws IOException, URISyntaxException, ExtractionException{
		runner.setHTTPUserAgent(USER_AGENT);
		HTTPClient httpClient = runner.getHTTPClient();
		DocumentSource source = new HTTPDocumentSource(httpClient,url);
				
		handler = new IgnoreAccidentalRDFa(handler, true); //ignore the CSS triples

		runner.extract(source, handler);
		
	}	
}
