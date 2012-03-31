import urllib
import httplib2

repository = 'test1'
graph      = 'http://source.data.gov.uk/data/reference/organogram-co/2010-06-30'
filename   = '/home/rakebul/Downloads/index.rdf'

print "Loading %s into %s in Sesame" % (filename, graph)
params = { 'context': '<' + graph + '>' }
endpoint = "http://localhost:8080/openrdf-sesame/repositories/%s/statements?%s" % (repository, urllib.urlencode(params))
data = open(filename, 'r').read()
(response, content) = httplib2.Http().request(endpoint, 'PUT', body=data, headers={ 'content-type': 'application/rdf+xml' })
print "Response %s" % response.status
print content
