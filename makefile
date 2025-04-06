build: 
	mvn -f p2pChess/pom.xml package

run: build
	java -jar p2pChess/target/p2pChess-1.0.jar

test:
	mvn -f p2pChess/pom.xml test

clean:
	sh packing/clean.sh

build-deb: p2pChess/target/p2pChess-1.0.jar
	sh packing/debBuild.sh

chess-v1.0.0.deb:
	sh packing/debBuild.sh

lint-deb: chess-v1.0.0.deb
	lintian chess-v1.0.0.deb