# This script will take genames rdf dump available here http://download.geonames.org/all-geonames-rdf.zip
# and convert each triples to N-Triple seralization.
# The dump has one rdf document per toponym on every line of the file.
# The produced N-Triples will be written in geonames.nt file. The final geonames.nt file is approximately 13.21GB
#!/usr/bin/python
import rdflib
fo = open("geonames.nt", "wb")
totalStmt = 0
with open("all-geonames-rdf.txt") as fileobject:
	count = 0
	for line in fileobject:
		#print "Line number: ", count+1, ":", line
		if count%2 != 0:
			g = rdflib.Graph()
			result = g.parse(data=line,format='xml')
			#print("graph has %s statements." % len(g))
			totalStmt += len(g)
			s = g.serialize(format='nt')
			fo.write(s)
			#print s
			#g.serialize(format='nt', destination='out.nt')
		#else:
		#	print "Feature: ", line
		

		count = count + 1
		#if count == 10:
		#	break

print "Total statements: ", totalStmt
fo.close()