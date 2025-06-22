cmp:
	javac -cp "./libs/*" -d ./build/ \
		./src/by/tishalovichm/data/MavenCoordinates.java \
		./src/by/tishalovichm/data/ProjectInfo.java \
		./src/by/tishalovichm/FilesCollector.java \
		./src/by/tishalovichm/JarDownloader.java \
		./src/by/tishalovichm/Consts.java \
		./src/by/tishalovichm/Utils.java \
		./src/by/tishalovichm/ProjectCompiler.java \
		./src/by/tishalovichm/ProjectRunner.java \
		./src/by/tishalovichm/ProjectInitializer.java \
		./src/by/tishalovichm/CliProcessor.java \
		./src/by/tishalovichm/factories/Factory.java \
		./src/by/tishalovichm/factories/UtilsFactory.java \
		./src/by/tishalovichm/Main.java

run:
	java -cp "./build;./libs/*" by.tishalovichm.Main init

jrun:
	java -cp "./build;./libs/*" by.tishalovichm.Main run

clean:
	rm build/*

package:
	jar -cfe "jaguar.jar" by.tishalovichm.Main \
		-C ./build /by/tishalovichm/FilesCollector.class \
		-C ./build by/tishalovichm/ProjectCompiler.class \
		-C ./build by/tishalovichm/ProjectRunner.class \
		-C ./build by/tishalovichm/ProjectInitializer.class \
		-C ./build by/tishalovichm/CliProcessor.class \
		-C ./build by/tishalovichm/Factory.class \
		-C ./build by/tishalovichm/Main.class \
		-C ./build by/tishalovichm/data/MavenCoordinates.class \
		-C ./build by/tishalovichm/data/ProjectInfo.class \
		./libs/jackson-core-2.19.1.jar \
		./libs/jackson-databind-2.19.1.jar

update: cmp package
