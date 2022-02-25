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

public class findInfos {

    public static void find(String glob, String location) throws IOException {

	final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(glob);
		
	Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
			
		@Override
		public FileVisitResult visitFile(Path p,
				BasicFileAttributes attrs) throws IOException {
			if (pathMatcher.matches(p)) {
				System.out.println(p);

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