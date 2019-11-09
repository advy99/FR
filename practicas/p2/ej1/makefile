HOME = .
BIN = $(HOME)/bin
SRC = $(HOME)/src


$(BIN)/*.class: init $(SRC)/*.java
	javac -d $(BIN)/ $(SRC)/*.java


init:
	-mkdir $(BIN)


clean:
	-rm $(BIN)/*
