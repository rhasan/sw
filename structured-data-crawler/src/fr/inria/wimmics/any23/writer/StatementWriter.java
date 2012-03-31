package fr.inria.wimmics.any23.writer;

import java.util.List;

import org.deri.any23.extractor.ExtractionContext;
import org.deri.any23.writer.TripleHandler;
import org.deri.any23.writer.TripleHandlerException;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;


public class StatementWriter implements TripleHandler  {

	private List<Statement> statements;
	//private final Resource overrideContext;
	ValueFactory valueFactory;

	/*public StatementWriter(List<Statement> statements) {
		// TODO Auto-generated constructor stub
		this(statements,null);
	}*/	
	
	//public StatementWriter(List<Statement> statements, Resource overrideContext) {
	public StatementWriter(List<Statement> statements) {
		this.statements = statements;
		//this.overrideContext = overrideContext;
		valueFactory = new ValueFactoryImpl();
	}
	
	
	@Override
	public void startDocument(URI documentURI) throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openContext(ExtractionContext context)
			throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveTriple(Resource s, URI p, Value o, URI g,
			ExtractionContext context) throws TripleHandlerException {
		
		statements.add(valueFactory.createStatement(s, p, o, g));
		
	}

	@Override
	public void receiveNamespace(String prefix, String uri,
			ExtractionContext context) throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeContext(ExtractionContext context)
			throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument(URI documentURI) throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentLength(long contentLength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws TripleHandlerException {
		// TODO Auto-generated method stub
		
	}
	
	/*private Resource getContextResource(Resource fromExtractor) {
		if (overrideContext != null) {
			return overrideContext;
		}
		return fromExtractor;
	}*/

}
