package modloaderx2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class findMods {

    public static void find(String glob, String location) throws IOException {
       
        //Files.walk(Paths.get(location)).filter(p -> p.toString().endsWith(".xml" )).map(p -> p.getParent()).distinct().forEach(System.err::println);

        //Files.find(Paths.get(location),Integer.MAX_VALUE,(filePath, fileAttr) -> fileAttr.isDirectory()).forEach(System.out::println);
    
         try (Stream<Path> paths = Files.walk(Paths.get(location), 1)) {paths.filter(Files::isDirectory).forEach(System.out::println);


         }   
    }
}