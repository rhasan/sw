import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.deri.any23.Any23;
import org.deri.any23.extractor.ExtractionException;
import org.deri.any23.http.HTTPClient;
import org.deri.any23.source.DocumentSource;
import org.deri.any23.source.HTTPDocumentSource;
import org.deri.any23.writer.NTriplesWriter;
import org.deri.any23.writer.TripleHandler;
import org.openrdf.OpenRDFException;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.Sail;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

//import com.hp.hpl.jena.rdf.model.Model;
//import com.hp.hpl.jena.rdf.model.ModelFactory;


/**
 * Examples of how to use 
 * 	1. Any23 library for extracting RDF from a URI
 *  2. Jena API hello world
 *  3. Sesame memory repository and query
 * @author rakebul
 *	
 */
public class HelloRDFWorld {
	
	private final static String NAMESPACE = "http://example.com/data#";
	private final static String NAMESPACE_ONTOLOGY = "http://example.com/ontology#";
	
	
	/**
	 * Extracts the RDF tripls from a hard-code
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ExtractionException
	 */
	public void any23Test() throws IOException, URISyntaxException, ExtractionException {

		Any23 runner = new Any23();
		runner.setHTTPUserAgent("test-user-agent");
		HTTPClient httpClient = runner.getHTTPClient();
		DocumentSource source = new HTTPDocumentSource(httpClient,"http://www.rentalinrome.com/semanticloft/semanticloft.htm");
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		TripleHandler handler = new NTriplesWriter(out);
		
		runner.extract(source, handler);
		
		String n3 = out.toString("UTF-8");
		
		System.out.println(n3);
		
		
	}
	
	/**
	 * Creates a Jena model with a triple and prints it (disabled)
	 */
	
	public void jenaTest() {
//		Model m = ModelFactory.createDefaultModel();
//		String NS = "http://example.com/test/";
//		
//		Resource r = m.createResource(NS + "r");
//		Property p = m.createProperty(NS + "p");
//		
//		r.addProperty(p, "hello world", XSDDatatype.XSDstring);
//		
	
/*		//org.slf4j.LoggerFactory.getILoggerFactory()
	
 //		GRDDL shows log error org/apache/commons/logging/LogFactory
  		Model m = ModelFactory.createDefaultModel();
		RDFReader r = m.getReader("GRDDL");
		r.setProperty("grddl.rdfa", "true");
		r.read(m,  "http://www.w3.org/2001/sw/grddl-wg/td/hCardFabien-RDFa.html");
*/		
//		m.write(System.out,"N-TRIPLE");
		
	}
	
	public void sesameTest() throws OpenRDFException {
		Repository repo = getMemoryBasedRepository();
		populateHardCodedTriples(repo); // initialize a repository
        populateHardCodedTriples( repo ); // add triples to the repository
        //searchWithSeRQL( repo ); // search the repository using SeRQL query language
        queryhWithSPARQL(repo);
	
	}
	
    /**
     * Adds triples to the repository using hard coded data.
     * Alternatively, we can read an RDF file to populate data.
     * 
     * @param repo
     * @throws RepositoryException
     */
	private void populateHardCodedTriples(Repository repo)
			throws RepositoryException {
		ValueFactory factory = repo.getValueFactory();

		// Let's define 3 people, 2 universities and 3 relations

		/**
		 * Here comes people and universities instances which will be RDF
		 * "subject" or "object" (note that these terms are used slightly in a
		 * different way in RDF)
		 * 
		 * Note that concepts in RDF are represented as URI.
		 */
		URI eric = factory.createURI(NAMESPACE + "eric");
		URI hideki = factory.createURI(NAMESPACE + "hideki");
		URI hovy = factory.createURI(NAMESPACE + "hovy");
		URI bob = factory.createURI(NAMESPACE + "bob");

		URI cmu = factory.createURI(NAMESPACE + "cmu");
		URI usc = factory.createURI(NAMESPACE + "usc");
		URI inria = factory.createURI(NAMESPACE + "inria");

		// Let's define an alternative form of CMU.
		URI cmuAlias = factory.createURI(NAMESPACE
				+ "carnegie_mellon_university");

		/**
		 * Here comes relationships (called "predicate" in the RDF world).
		 * 
		 * You can use static reference to one of the vocabulary primitives
		 * provided by sesame, or your own ontology. Following primitives are
		 * provided by sesame under the package org.openrdf.model.vocabulary.
		 * 
		 * OWL - Constants for OWL primitives and for the OWL namespace. RDF -
		 * Constants for RDF primitives and for the RDF namespace. RDFS -
		 * Constants for RDF Schema primitives and for the RDF Schema namespace.
		 * SESAME - Defines constants for the Sesame schema namespace XML -
		 * Defines constants for the standard XML Schema datatypes.
		 * 
		 * Primitives and inference rules for them are defined in the W3C RDF
		 * Semantics spec at http://www.w3.org/TR/rdf-mt/
		 */
		URI isa = RDFS.SUBCLASSOF; // predefined predicate
		URI worksFor = factory.createURI(NAMESPACE + "worksFor"); // original
																	// predicate
		URI coauthoredWith = factory.createURI(NAMESPACE + "coauthoredWith"); // original
																				// predicate
		
		URI instanceOf  = RDF.TYPE;
		
		//the ontology
		URI person  = factory.createURI(NAMESPACE_ONTOLOGY+"person");
		URI researchOrg  = factory.createURI(NAMESPACE_ONTOLOGY+"researchOrg");
		URI university  = factory.createURI(NAMESPACE_ONTOLOGY+"university");
		

		// Let's add triples into the repository
		RepositoryConnection con = repo.getConnection();
		try {
			con.setAutoCommit(false);

			con.add(person, instanceOf,RDFS.CLASS);
			con.add(researchOrg, instanceOf,RDFS.CLASS);
			con.add(university, instanceOf,RDFS.CLASS);
			
			con.add(university,RDFS.SUBCLASSOF,researchOrg);
			
			
			con.add(eric, instanceOf,person);
			con.add(hideki, instanceOf,person);
			con.add(hovy, instanceOf,person);
			con.add(bob, instanceOf,person);
			
			con.add(cmu,instanceOf,university);
			con.add(usc,instanceOf,university);
			con.add(inria,instanceOf,university);
			
			
			con.add(eric, worksFor, cmu);
			con.add(hideki, worksFor, cmu);
			con.add(hovy, worksFor, usc);
			con.add(bob,worksFor,inria);

			// Note that direction matters.
			//con.add(cmuAlias, isa, cmu);
			//con.add(cmu, isa, cmuAlias);

			con.add(eric, coauthoredWith, hovy);
			con.add(hovy, coauthoredWith, eric);
			con.add(eric, coauthoredWith, hideki);
			con.add(hideki, coauthoredWith, eric);

			con.commit();
		} catch (RepositoryException e) {
			// If something went wrong during the transaction, let's roll it
			// back
			con.rollback();
		} finally {
			con.close();
		}
	}

	/**
	 * Searches in the repository with various SeRQL queries.
	 * 
	 * See the query language spec at:
	 * http://www.openrdf.org/doc/sesame2/users/ch09.html
	 * 
	 * @param repo
	 * @throws RepositoryException
	 * @throws OpenRDFException
	 */
	private void searchWithSeRQL(Repository repo) throws RepositoryException,
			OpenRDFException {
		RepositoryConnection con = repo.getConnection();
		String queryString = null;
		String postfix = " USING NAMESPACE seit = <" + NAMESPACE + ">";
		try {

			System.out.println("#### Which university does Eric belong to?");
			queryString = "SELECT * FROM {seit:eric} seit:worksFor {answer}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out
					.println("#### Which university do Eric AND Hideki belong to?");
			queryString = "SELECT * FROM {seit:eric, seit:hideki} seit:worksFor {answer}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out.println("#### Who belongs to CMU?");
			queryString = "SELECT * FROM {answer} seit:worksFor {seit:cmu}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out
					.println("#### What relationship exists between Eric and CMU?");
			queryString = "SELECT * FROM {seit:eric} answer {seit:cmu}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out.println("#### Who coauthored with whom?");
			queryString = "SELECT * FROM {answer1} seit:coauthoredWith {answer2}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out
					.println("#### Is there such a person who coauthored a paper with Hideki, and another with Hovy?");
			queryString = "SELECT * FROM {seit:hideki} seit:coauthoredWith {answer} seit:coauthoredWith {seit:hovy}"
					+ postfix;
			printSearchResult(con, queryString);

			System.out.println("#### Which CMU person did Hovy coauthor with?");
			queryString = "SELECT * FROM {seit:hovy} seit:coauthoredWith {answer} seit:worksFor {} rdfs:subClassOf {seit:cmu}"
					+ postfix;
			printSearchResult(con, queryString);

			// Can sesame infer cmu == carnegie mellon university are the same
			// thing?
			System.out
					.println("#### Which Carnegie Mellon University person did Hovy coauthor with?");
			queryString = "SELECT * FROM {seit:hovy} seit:coauthoredWith {answer} seit:worksFor {} rdfs:subClassOf {seit:carnegie_mellon_university}"
					+ postfix;
			printSearchResult(con, queryString);

		} finally {
			con.close();
		}
	}

	/**
	 * Shows the search result from the repository given a query.
	 * 
	 * @param con
	 * @param queryString
	 * @throws RepositoryException
	 * @throws OpenRDFException
	 */
	private void printSearchResult(RepositoryConnection con, String queryString)
			throws RepositoryException, OpenRDFException {
		TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SERQL,
				queryString);
		TupleQueryResult result = tupleQuery.evaluate();
		try {
			int i = 0;
			while (result.hasNext()) {
				System.out.println("==== Result " + (++i) + " ====");
				BindingSet bindingSet = result.next();
				List<String> names = result.getBindingNames();
				for (String name : names) {
					System.out.println(name + ": " + bindingSet.getValue(name));
				}
				System.out.println();
			}
		} finally {
			result.close();
		}
	}
	
	
	private void printSPARQLQueryhResult(RepositoryConnection con, String quString) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, quString);
		
		TupleQueryResult result = tupleQuery.evaluate();
		try {
			int i = 0;
			while (result.hasNext()) {
				System.out.println("==== Result " + (++i) + " ====");
				BindingSet bindingSet = result.next();
				List<String> names = result.getBindingNames();
				for (String name : names) {
					System.out.println(name + ": " + bindingSet.getValue(name));
				}
				System.out.println();
			}
		} finally {
			result.close();
		}
		
	}
	
	
	private void queryhWithSPARQL(Repository repo) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		RepositoryConnection con = repo.getConnection();
		String queryString = null;
		String prefix = "PREFIX ex: <"+NAMESPACE+">"+
						"PREFIX exon: <"+NAMESPACE_ONTOLOGY+">";
		
		try {
			System.out.println("#### Which does Bob work?");
			queryString = prefix + "SELECT ?answer WHERE " +
					"{ex:bob ex:worksFor ?answer.}";
			printSPARQLQueryhResult(con, queryString);
			
			
			
			System.out.println("#### Who works for CMU?");
			queryString = prefix + "SELECT ?answer WHERE " +
					"{?answer ex:worksFor ex:cmu.}";
			printSPARQLQueryhResult(con, queryString);
			
			System.out.println("#### Who works for a research institute?");
			queryString = prefix + "SELECT * WHERE " +
					"{?subject ex:worksFor ?company. " +
					"?company a exon:researchOrg.}";
			//System.out.println(queryString);
			printSPARQLQueryhResult(con, queryString);
			
			
		} finally {
			con.close();
		}
		
		
	}

	/**
	 * Initializes and returns a RDFS inference enabled memory based repository 
	 * @return an instance of a {@link Repository}
	 * @throws RepositoryException
	 */
	
	private Repository getMemoryBasedRepository() throws RepositoryException {
		MemoryStore memory = new MemoryStore();
		Sail sail = new ForwardChainingRDFSInferencer( memory ); // to enable inference
        Repository rep = new SailRepository( sail ); // SAIL stands for Storage And Inference Layer
        rep.initialize();
        return rep;
		
		
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException, ExtractionException, OpenRDFException {
		// TODO Auto-generated method stub

		
		HelloRDFWorld hwTest = new HelloRDFWorld();
		//hwTest.any23Test();
		hwTest.sesameTest();
	}
}
