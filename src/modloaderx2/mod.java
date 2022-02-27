package modloaderx2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// SAMPLE MOD INFO XML FILE
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

@XmlRootElement(name = "mod")
public class mod {

    @XmlElement
    String name;
    public String getName() {
        return name;
    }

    @XmlElement
    String author;

    @XmlElement
    String description;

    @XmlElement
    String gameVersions;

    @XmlElement
    String modid;

    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public String getGameVersions() {
        return gameVersions;
    }
    public String getModID() {
        return modid;
    }
}

