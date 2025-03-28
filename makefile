build: 
	mvn -f p2pChess/pom.xml package

run:
	java -jar p2pChess/target/p2pChess-1.0.jar

test:
	mvn -f p2pChess/pom.xml test

clean:
	mvn -f p2pChess/pom.xml clean