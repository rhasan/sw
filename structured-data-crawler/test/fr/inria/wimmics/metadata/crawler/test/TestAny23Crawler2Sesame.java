/**
 * 
 */
package fr.inria.wimmics.metadata.crawler.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.deri.any23.extractor.ExtractionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

import fr.inria.wimmics.metadata.crawler.Any23Crawler2Sesame;
import fr.inria.wimmics.metadata.util.sesame.SesameUtil;

/**
 * @author rakebul
 *
 */
public class TestAny23Crawler2Sesame {

	Any23Crawler2Sesame crawler;
	Repository repo;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repo = SesameUtil.getMemoryBasedRepository();
		crawler = new Any23Crawler2Sesame();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link fr.inria.wimmics.metadata.crawler.Any23Crawler2Sesame#crawlURL(java.lang.String)}.
	 */
	@Test
	public final void testCrawlURL() {
		//fail("Not yet implemented"); // TODO
		
		try {
			crawler.crawlURL("http://www.w3.org/People/Ivan/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("testCrawlURL() failed");
			e.printStackTrace();
		} 

	}
	
	/**
	 * Test method for {@link fr.inria.wimmics.metadata.crawler.Any23Crawler2Sesame#crawlURL(String, org.openrdf.repository.Repository)}}
	 */
	@Test
	public final void testCrawlURLSesame() {
		
		String url = "http://www.w3.org/People/Ivan/";
		try {
			crawler.crawlURL(url, repo);
			
			String queryString =  "SELECT * WHERE " +
									"{ GRAPH <http://www.w3.org/People/Ivan/> {?subject ?predicate ?object.}}";
			
			SesameUtil.queryWithSPARQL(repo, queryString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("testCrawlURL() failed");
			e.printStackTrace();
		}
	
	
	}
	
	
	@Test
	public final void testCrawlURL4RDFaSesame() {
		
		String url = "http://www-sop.inria.fr/members/Hasan.Rakebul/";
		try {
			crawler.crawlURL("html-rdfa",url, repo);
			
			String queryString =  "SELECT * WHERE " +
									"{ GRAPH <"+url+"> {?subject ?predicate ?object.}}";
			
			SesameUtil.queryWithSPARQL(repo, queryString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("testCrawlURL() failed");
			e.printStackTrace();
		}
	
	
	}	
	
	
}
