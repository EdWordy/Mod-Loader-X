import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class Load {

    public static info modInfoVM;

    public static info modInfoML;

    public static StringWriter sw;

    public static info loadInfoVM() throws JAXBException, IOException {

        if (ModLoaderXController.infoToReadVM != null) {

            // creates a new context and sets it equal to an instance of the mod class
            JAXBContext context = JAXBContext.newInstance(info.class);
           
             // unmarshal  and concatenate the brackets
            modInfoVM = (info) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadVM.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml")));
            
            // initialize new string writer
            sw = new StringWriter();
            
            // marshal and output to string writer
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(modInfoVM, sw);
        }
        return null;
    }

    public static info loadInfoML() throws JAXBException, IOException {

        if (ModLoaderXController.infoToReadML != null) {

            // creates a new context and sets it equal to an instance of the mod class
            JAXBContext context = JAXBContext.newInstance(info.class);
            
            // unmarshal and concatenate the brackets
            modInfoML = (info) context.createUnmarshaller().unmarshal(new FileReader(ModLoaderXController.infoToReadML.replaceAll("[\\p{Ps}\\p{Pe}]", "").concat("\\info.xml")));


            // initialize new stringwriter
            sw = new StringWriter();

            // marshal and output to stringwriter
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(modInfoML, sw);
        }
        return null;
    }

    public void loadData(){



    }

}


