import urllib
import httplib2
import json

query = 'SELECT DISTINCT ?type WHERE { ?thing a ?type . } ORDER BY ?type'
repository = 'test1'
endpoint = "http://localhost:8080/openrdf-sesame/repositories/%s" % (repository)

print "POSTing SPARQL query to %s" % (endpoint)
params = { 'query': query }
headers = { 
  'content-type': 'application/x-www-form-urlencoded', 
  'accept': 'application/sparql-results+json' 
}
(response, content) = httplib2.Http().request(endpoint, 'POST', urllib.urlencode(params), headers=headers)

print "Response %s" % response.status
results = json.loads(content)
print "\n".join([result['type']['value'] for result in results['results']['bindings']])
