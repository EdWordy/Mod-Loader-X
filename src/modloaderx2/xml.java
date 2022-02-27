package modloaderx2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// SAMPLE XML
//  <mod>
//      <name>Merc's Sound Pack</name>
//      <author>TedDraws#0648 aka Mercenary</author>
//      <description>Adds in new sounds files and extra effects! version 0.1 </description>
//      <minimumLoaderVersion>0.10.0</minimumLoaderVersion>
//      <gameVersions>
//          <v>0.14.1</v>
//      </gameVersions>
//      <modid>5657</modid>
//  </mod> 



@XmlRootElement
public class xml {

    String name;
    String author;
    String description;
    String gameVersions;
    String modid;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(String name) {
        this.author = name;
    }
    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String name) {
        this.description = name;
    }

 
}
