all:
	@javac -d bin ./src/config/*.java ./src/main/*.java

server:
	@java -cp bin src.main.Server

client:
	@java -cp bin src.main.Client

clean:
	rm -r ./bin/*
