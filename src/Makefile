default: feud

feud: Driver.class Panel.class
	java -cp ../bin Driver

%.class: %.java
	javac -d ../bin $<

.PHONY: clean
clean:
	rm -f bin/*.class
