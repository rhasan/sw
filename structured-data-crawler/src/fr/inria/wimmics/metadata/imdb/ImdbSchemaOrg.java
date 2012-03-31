package fr.inria.wimmics.metadata.imdb;

import java.io.IOException;
import java.net.URISyntaxException;

import org.deri.any23.extractor.ExtractionException;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

import fr.inria.wimmics.metadata.crawler.Any23Crawler2Sesame;
import fr.inria.wimmics.metadata.util.sesame.SesameUtil;

public class ImdbSchemaOrg {

	Any23Crawler2Sesame crawler;
	Repository repo;	
	String schemaOrgLocation = "http://schema.org/docs/schemaorg.owl";
	
	
	
	public ImdbSchemaOrg() throws RepositoryException, IOException, URISyntaxException, ExtractionException, MalformedQueryException, QueryEvaluationException {
		repo = SesameUtil.getMemoryBasedRepository();
		crawler = new Any23Crawler2Sesame();
		crawler.crawlURL("rdf-xml",schemaOrgLocation,repo);
		//SesameUtil.printGraph(repo,schemaOrgLocation);
	}

	public void crawlImdbURL(String movieUrl) throws RepositoryException, IOException, URISyntaxException {
		crawler.crawlURL4SchemaOrgMicroData(movieUrl,repo);
		
	}
	
	public void showImdbURL(String movieUrl) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		
		SesameUtil.printGraph(repo,movieUrl);
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String movieUrl = "http://www.imdb.com/name/nm0301959/";
		ImdbSchemaOrg imdb =  new ImdbSchemaOrg();
		imdb.crawlImdbURL(movieUrl);
		imdb.showImdbURL(movieUrl);
		
		//http://schema.org/CreativeWork
		
		
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				//"select * where {?subject rdfs:label ?labelstr}";
			"SELECT DISTINCT ?subject ?labelstr ?object WHERE {?subject a <http://schema.org/CreativeWork>. " +
				"?subject ?predicate ?object. " +
				"?predicate rdfs:label ?labelstr.}";
		System.out.println("Show me all the CreativeWork:"+queryString);
		SesameUtil.queryWithSPARQL(imdb.repo, queryString);
		
	
	}

}
