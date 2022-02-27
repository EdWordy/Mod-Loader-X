package modloaderx2;

import com.sun.deploy.config.Config;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.validation.Schema;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

public class Load {

    public static mod modVM;

    public static mod modML;

    public static File modVM2;

    public static File modML2;

    @FXML
    public static Label modDetails;



    public static mod loadInfoVM() throws JAXBException, IOException {

        modVM2 = new File (ModLoaderXController.infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("/info.xml"));

        if (ModLoaderXController.infoToReadVM != null) {
            JAXBContext context = JAXBContext.newInstance(mod.class);
            modVM = (mod) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("/info.xml")));

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(modVM, System.err);
            }
            return null;
    }

    public static mod loadInfoML() throws JAXBException, IOException {
       
        modML2 = new File (ModLoaderXController.infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("/info.xml"));

        if (ModLoaderXController.infoToReadML != null) {
            JAXBContext context = JAXBContext.newInstance(mod.class);
            System.err.println(ModLoaderXController.infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("/info.xml"));
            modML = (mod) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("/info.xml")));
            
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(modML, System.err);
        }
        return null;
    }

    public void loadFiles() {

    }










}


