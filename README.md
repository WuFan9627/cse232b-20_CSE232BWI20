# Milestone 4 demo 
## Content:
#### XPathTest.txt: 
It is the query that need to be rewriten. 

#### XPath-1.0-SNAPSHOT.jar: 
It accepts 2 argument. The first one is query txt file, which is XPathTest.txt. 
The second one is flag which indicates bushy or left deep join

#### rewriteQUery.txt: 
The rewriten query

#### reult.txt: 
The final output 

## How to run:
java -jar  XPath-1.0-SNAPSHOT.jar  XPathTest.txt  -flag

If the input query contains "join" or no whereclause or no joined trees then it can not be rewriten. The rewriteQUery.txt will be "rewrite failed" 
