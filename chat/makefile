all: build
	@java -cp bin src.main.ChatServer

build:
	@javac -d bin ./src/config/*.java ./src/main/*.java

clean:
	rm -r ./bin/*
