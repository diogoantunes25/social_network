PO_UILIB_DIR=../../po-uilib
JARNAME=sonet-app-browser

all:
	(cd src; javac -cp $(PO_UILIB_DIR)/po-uilib.jar:../../sonet-core/sonet-core.jar -encoding UTF-8 `find . -name \*.java`)
	(cd src; jar cf ../$(JARNAME).jar `find . -name \*.class -o -name \*.java` )

clean:
	$(RM) $(JARNAME).jar `find . -name \*.class`
