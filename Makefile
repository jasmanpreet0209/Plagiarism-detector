JFLAGS=-g
JC=javac
JVM=java
FILE1=
FILE2=

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = s40231120_detector.java

MAIN = s40231120_detector

default: classes
classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE1) $(FILE2)

clean:
	$(RM) *.class

testPlagiarism: $(MAIN).class
	@echo "Testing plagiarism test cases in ../data/plagiarismXX"
	@for file in ../data/plagiarism*; do echo "Testing $$file"; $(JVM) $(MAIN) $$file/1.txt $$file/2.txt; done

testNonPlagiarism: $(MAIN).class
	@echo "Testing non-plagiarism test cases in ../data/okayXX"
	@for file in ../data/okay*; do echo "Testing $$file"; $(JVM) $(MAIN) $$file/1.txt $$file/2.txt; done

test: testPlagiarism testNonPlagiarism