package modloaderx2;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Load {

    public static mod modVM;

    public static mod modML;

    public static StringWriter sw;

    public static mod loadInfoVM() throws JAXBException, IOException {

        if (ModLoaderXController.infoToReadVM != null) {
            // creates a new context and sets it equal to an instance of the mod class
            JAXBContext context = JAXBContext.newInstance(mod.class);
           
             // unmarshal  and concantanetate the brackets
            modVM = (mod) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml")));
            
            // initalize new stringwriter
            sw = new StringWriter();
            
            // marshal and output to stringwriter
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(modVM, sw);
        }
        return null;
    }

    public static mod loadInfoML() throws JAXBException, IOException {

        if (ModLoaderXController.infoToReadML != null) {
            // creates a new context and sets it equal to an instance of the mod class
            JAXBContext context = JAXBContext.newInstance(mod.class);
            
            // unmarshal and concantanetate the brackets
            modML = (mod) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml")));

            // initalize new stringwriter
            sw = new StringWriter();

            // marshal and output to stringwriter
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(modML, sw);
        }
        return null;
    }
}


