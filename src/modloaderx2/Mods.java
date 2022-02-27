package modloaderx2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Mods {

    public static void findInfos(String glob, String location) throws IOException {

	final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);
		
	Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
			
		@Override
		public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) throws IOException {
			if (pathMatcher.matches(p)) {
                         
                            System.out.println("Info found at: " + p);
			}
			return FileVisitResult.CONTINUE;
			}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
			}

	});
    }

    public static void findFiles(String glob, String location) throws IOException {

        final PathMatcher pathMatcher2 = FileSystems.getDefault().getPathMatcher(glob);
        

	Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
			
		@Override
		public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) throws IOException {
			if (pathMatcher2.matches(p) && pathMatcher2.toString().contains("!/spacehaven_0.14.1/*")) {
                            System.out.println("files found at: " + p);
			}
			return FileVisitResult.CONTINUE;
			}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
			}

	});

    }


}



