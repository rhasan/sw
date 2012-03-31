import urllib
import httplib2
import rdflib
import rdfalchemy

query = """PREFIX foaf: <http://xmlns.com/foaf/0.1/>

CONSTRUCT {
  ?person
    a foaf:Person ;
    foaf:name ?name ;
    ?prop ?value .
} WHERE {
  ?person a foaf:Person ;
    foaf:name ?name ;
    ?prop ?value .
}"""
repository = 'test1'
endpoint = "http://localhost:8080/openrdf-sesame/repositories/%s" % repository

print "POSTing SPARQL query to %s" % endpoint
params = { 'query': query }
headers = { 
  'content-type': 'application/x-www-form-urlencoded', 
  'accept': 'text/plain' 
}
(response, content) = httplib2.Http().request(endpoint, 'POST', urllib.urlencode(params), headers=headers)
print "Response %s" % response.status
#print "N3 %s" % content
graph = rdflib.ConjunctiveGraph()
graph.parse(rdflib.parser.StringInputSource(content), format="nt")


#for triple in graph.triples((None,None,None)):
#    print triple
print "Loaded %d triples" % len(graph)

FOAF = rdflib.Namespace('http://xmlns.com/foaf/0.1/')
RDF = rdflib.Namespace('http://www.w3.org/1999/02/22-rdf-syntax-ns#')

class Person(rdfalchemy.rdfSubject):
  rdf_type = FOAF.Person
  name = rdfalchemy.rdfSingle(FOAF.name)
  mbox = rdfalchemy.rdfSingle(FOAF.mbox)

rdfalchemy.rdfSubject.db = graph
sname = rdflib.term.Literal(u'Andrew Stott', lang=u'en')
#stott = Person.get_by(name='Andrew Stott')
stott = Person.get_by(name=sname)
print "Andrew Stott's email address: %s" % stott.mbox.n3()

