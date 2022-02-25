package modloaderx;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;


/**
 *
 * @author Taylor
 */
public class info  {


    
   public static void readInfo() {

    File file = new File(ModLoaderXController.CURRENT_PATH);
    Document document = null;
        try {
            document = new Builder().build(file);
        } catch (ParsingException ex) {
            Logger.getLogger(info.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(info.class.getName()).log(Level.SEVERE, null, ex);
        }
       String InfotoString = document.toString();
       ModLoaderXController.modDetails.setText(InfotoString);
    }
}
