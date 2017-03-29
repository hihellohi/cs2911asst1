JFLAGS = -g -d bin -s src -h src -cp bin -sourcepath src
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
		  VanRentalSystem.java \

FILES = $(addprefix src/, $(CLASSES))

classes: $(addprefix src/, $(CLASSES:.java=.class))

default: classes

docs: classes
	javadoc -d docs $(FILES)

clean: 
	$(RM) bin/*.class

