package fr.inria.wimmics.metadata.crawler.test;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.deri.any23.extractor.ExtractionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.Statement;

import fr.inria.wimmics.metadata.crawler.RDFaCrawler;

public class TestRDFaCrawler {
	
	RDFaCrawler crawler;

	@Before
	public void setUp() throws Exception {
		crawler = new RDFaCrawler();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testCrawlFoafRDFaList() throws IOException, URISyntaxException, ExtractionException {
		
		List<Statement> statements = crawler.crawlFoafRDFaList("http://www-sop.inria.fr/members/Hasan.Rakebul");
		
		for(Statement st:statements) {
			//System.out.println(st.getSubject().toString()+" "+st.getPredicate().toString()+" "+st.getObject().toString()+" "+st.getContext()); //with context
			System.out.println(st.getSubject().toString()+" "+st.getPredicate().toString()+" "+st.getObject().toString());
		}
	}
}
