default: feud

feud: Driver.class ModeratorPanel.class ContestantPanel.class Game.class Question.class ResponsePanel.class PanelMode.class
	java -cp ../bin Driver

%.class: %.java
	javac -d ../bin $<

.PHONY: clean
clean:
	rm -f bin/*.class
