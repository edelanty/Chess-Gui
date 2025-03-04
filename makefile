build: 
	mvn -f p2pChess/pom.xml package

run:
	mvn -f p2pChess/pom.xml exec:java

test:
	mvn -f p2pChess/pom.xml test

clean:
	mvn -f p2pChess/pom.xml clean