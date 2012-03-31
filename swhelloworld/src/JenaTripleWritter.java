import org.deri.any23.extractor.ExtractionContext;
import org.deri.any23.writer.TripleHandler;
import org.deri.any23.writer.TripleHandlerException;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

//import com.hp.hpl.jena.rdf.model.Model;


public class JenaTripleWritter implements TripleHandler {

//	private final Model conn;
//    private final Resource overrideContext;
 
//     public JenaTripleWritter(Model conn) {
//         this(conn, null);
//     }
 
//     public JenaTripleWritter(Model conn, Resource overrideContext) {
//         this.conn = conn;
//         this.overrideContext = overrideContext;
//     }	
	
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
		// TODO Auto-generated method stub
//		com.hp.hpl.jena.rdf.model.Resource js = conn.createResource(s.stringValue());
//		com.hp.hpl.jena.rdf.model.Property jp = conn.createProperty(p.stringValue());
		//com.hp.hpl.jena.rdf.model.RDFNode jo = new 
		
		
		
//		if(o instanceof Resource) {
//			js.addProperty(jp,conn.createResource(o.stringValue()));
//		} else if(o instanceof Literal)  {
//			
//			Literal on = (Literal)o;
//			String lex = on.getLabel();
//			com.hp.hpl.jena.rdf.model.Literal jl = conn.createLiteral(lex,on.getLanguage());
//			
//			js.addProperty(jp,jl);
//		}
		
		//o.
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

}
