JFLAGS = -g -d bin -s src -h src -cp bin -sourcepath src
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \

FILES = $(addprefix src/, $(CLASSES))

classes: $(addprefix src/, $(CLASSES:.java=.class))

default: classes

docs:
	javadoc -d docs $(FILES)

clean: 
	$(RM) bin/*.class

