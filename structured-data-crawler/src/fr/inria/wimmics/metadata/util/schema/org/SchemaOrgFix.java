package fr.inria.wimmics.metadata.util.schema.org;

import org.deri.any23.extractor.ExtractionContext;
import org.deri.any23.writer.RepositoryWriter;
import org.deri.any23.writer.TripleHandlerException;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.RepositoryConnection;

public class SchemaOrgFix extends RepositoryWriter {

	RepositoryConnection repoConn;
	public SchemaOrgFix(RepositoryConnection conn) {
		super(conn);
		repoConn = conn;
		// TODO Auto-generated constructor stub
	}

	public SchemaOrgFix(RepositoryConnection conn, Resource overrideContext) {
		super(conn, overrideContext);
		repoConn = conn;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void receiveTriple(Resource s, URI p, Value o, URI g,
			ExtractionContext context) throws TripleHandlerException {
		
		if(p.stringValue().contains("schema.org")) {
			String pUri = p.getLocalName();
			
		
			ValueFactory valueFactory  = repoConn.getRepository().getValueFactory();
			URI newP = valueFactory.createURI("http://schema.org/"+pUri);
			p = newP;
			//System.out.println(p.stringValue());
		}
		//System.out.println(p.stringValue());
		super.receiveTriple(s, p, o, g, context);
	}
}
