# Mod-Loader-X4
version 0.3.5

A modloader for Space Haven written in Java 8.

---



CURRENT FEATURES:

-Menu: Quit

-Menu: Help

-dialog to display previous action (clear mods, add mod, remove mod, launch)

-listview of current mod folder and mods loaded, with items that switch between them.

-a method that finds all the info.xml files

-addMod method (adds mods from selected listview item to list1 or removes from list2 and displays it in another listview)

-removeMod method (removes mod from list1 and adds back to list2)

-clearMods method (removes all mods from list1 and adds back to list2)

-read info.xml file and display contents in an info pane

-loading and reading the .jar

-writing a modid.cim file

-a selectable root game directory (the folder where the spacehaven.jar is)

-launcher method (unpack and repacks jar with modded assets and generates a textures file)

-writing a generated_textures.xml file


- ...



TO DO:

-solving the stackOverFlowError in-game (GAME BREAKING)

-more features!

- ...



---

HOW TO USE:

Download the latest release and run the executable jar file. You'll need java 8 installed to do so.

---

THANKS TO:

Paperfox, Gravelyn, Neato, SagaciousZed and a few others, along with Bugbyte. Cheers!

---

NOTES:

-When composing your XML files (haven, animations, texts, etc), please DO NOT use ampersands as they EXPLODE the parser and merge functions. You've been warned.

-When choosing the file directory, select the root game directory (where the spacehaven.jar is). The modloader will take care of the rest.

- ...
