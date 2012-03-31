package fr.inria.wimmics.metadata.crawler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.deri.any23.Any23;
import org.deri.any23.extractor.ExtractionException;
import org.deri.any23.filter.IgnoreAccidentalRDFa;
import org.deri.any23.http.HTTPClient;
import org.deri.any23.source.DocumentSource;
import org.deri.any23.source.HTTPDocumentSource;
import org.deri.any23.writer.NTriplesWriter;
import org.deri.any23.writer.RepositoryWriter;
import org.deri.any23.writer.TripleHandler;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import fr.inria.wimmics.metadata.util.schema.org.SchemaOrgFix;
/**
 * 
 * @author rakebul
 *
 */
public class Any23Crawler2Sesame {
	
	final public static String USER_AGENT = "inria-wimmics-crawler-agent";
	
	/**
	 * 
	 * @param url
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ExtractionException
	 */
	public void crawlURL(String url) throws IOException, URISyntaxException, ExtractionException {
		Any23 runner = new Any23();
		runner.setHTTPUserAgent(USER_AGENT);
		HTTPClient httpClient = runner.getHTTPClient();
		DocumentSource source = new HTTPDocumentSource(httpClient,url);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		TripleHandler handler = new NTriplesWriter(out);
		handler = new IgnoreAccidentalRDFa(handler, true); //ignore the CSS triples
		runner.extract(source, handler);
		String n3 = out.toString("UTF-8");
		System.out.println(n3);		
	}

	/**
	 * 
	 * @param url
	 * @param repo
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	
	public void crawlURL(String url, Repository repo) throws  RepositoryException, IOException, URISyntaxException {
		Any23 runner = new Any23();
		extractInsert(runner, url, repo);

	}
	
	/**
	 * 
	 * @param extractorName
	 * @param url
	 * @param repo
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void crawlURL(String extractorName,String url, Repository repo) throws  RepositoryException, IOException, URISyntaxException {
		// TODO Auto-generated method stub
		Any23 runner = new Any23(extractorName);
		extractInsert(runner, url, repo);		
	}	

	/**
	 * 
	 * @param runner
	 * @param url
	 * @param repo
	 * @throws RepositoryException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void extractInsert(Any23 runner, String url, Repository repo) throws  RepositoryException, IOException, URISyntaxException {
		runner.setHTTPUserAgent(USER_AGENT);
		HTTPClient httpClient = runner.getHTTPClient();
		DocumentSource source = new HTTPDocumentSource(httpClient,url);
		
		ValueFactory factory = repo.getValueFactory();
		RepositoryConnection con = repo.getConnection();
		URI context = factory.createURI(url);
		
		TripleHandler handler = new RepositoryWriter(con, context);
		handler = new IgnoreAccidentalRDFa(handler, true); //ignore the CSS triples
		try {
			runner.extract(source, handler);
		} catch (ExtractionException e) {
			con.rollback();
		} finally {
			if(con!=null) con.close();
		}
		
	}
	
	
	public void crawlURL4SchemaOrgMicroData(String url, Repository repo) throws IOException, URISyntaxException, RepositoryException {
		Any23 runner = new Any23("html-microdata");

		runner.setHTTPUserAgent(USER_AGENT);
		HTTPClient httpClient = runner.getHTTPClient();
		DocumentSource source = new HTTPDocumentSource(httpClient,url);
		
		ValueFactory factory = repo.getValueFactory();
		RepositoryConnection con = repo.getConnection();
		URI context = factory.createURI(url);
		
		TripleHandler handler = new SchemaOrgFix(con, context); //fix if any uri appears without http
		
		
		handler = new IgnoreAccidentalRDFa(handler, true); //ignore the CSS triples
		try {
			runner.extract(source, handler);
		} catch (ExtractionException e) {
			con.rollback();
		} finally {
			if(con!=null) con.close();
		}		
		
	}


}
