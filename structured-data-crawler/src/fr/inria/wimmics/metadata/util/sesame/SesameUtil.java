/**
 * 
 */
package fr.inria.wimmics.metadata.util.sesame;

import static org.junit.Assert.fail;

import java.util.List;

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

/**
 * @author rakebul
 *
 */
public class SesameUtil {

	
	/**
	 * Initializes and returns a RDFS inference enabled memory based repository 
	 * @return an instance of a {@link Repository}
	 * @throws RepositoryException
	 */
	
	public static Repository getMemoryBasedRepository() throws RepositoryException {
		MemoryStore memory = new MemoryStore();
		Sail sail = new ForwardChainingRDFSInferencer( memory ); // to enable inference
        Repository rep = new SailRepository( sail ); // SAIL stands for Storage And Inference Layer
        rep.initialize();
        return rep;
		
		
	}
	
	/**
	 * 
	 * @param con
	 * @param quString
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException
	 */
	public static void printSPARQLQueryhResult(RepositoryConnection con, String quString) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
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
	
	/**
	 * 
	 * @param repo
	 * @param queryString
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException
	 */
	public static void queryWithSPARQL(Repository repo, String queryString) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
		RepositoryConnection con = repo.getConnection();

		
		try {
			printSPARQLQueryhResult(con, queryString);
			
		} finally {
			con.close();
		}
	}
	
	
	public static void printGraph(Repository repo, String graphUrl) throws RepositoryException, MalformedQueryException, QueryEvaluationException {
			
		String queryString =  "SELECT * WHERE " +
								"{ GRAPH <"+graphUrl+"> {?subject ?predicate ?object.}}";
		
		queryWithSPARQL(repo, queryString);
	}
	
}
