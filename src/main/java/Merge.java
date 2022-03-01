import com.ximpleware.*;

import java.io.IOException;

public class Merge {

    public static void mergeAnimationsTemp (String one, String two) throws NavException, ModifyException, TranscodeException, IOException {
        VTDGen vg = new VTDGen();
        if (!vg.parseFile(one, false))
            return;
        VTDNav vn1=vg.getNav();
        if(!vg.parseFile(two, false))
            return;
        VTDNav vn2 = vg.getNav();
        XMLModifier xm = new XMLModifier(vn1);
        long l = vn2.getContentFragment();
        xm.insertBeforeTail(vn2, l);
        xm.output(ModLoaderXController.source + "\\library\\animations.xml" );
    }

    public static void mergeHavenTemp (String one, String two) throws NavException, ModifyException, TranscodeException, IOException {
        VTDGen vg = new VTDGen();
        if (!vg.parseFile(one, false))
            return;
        VTDNav vn1=vg.getNav();
        if(!vg.parseFile(two, false))
            return;
        VTDNav vn2 = vg.getNav();
        XMLModifier xm = new XMLModifier(vn1);
        long l = vn2.getContentFragment();
        xm.insertBeforeTail(vn2, l);
        xm.output(ModLoaderXController.source + "\\library\\haven.xml" );
    }

    public static void mergeTextsTemp(String one, String two) throws NavException, ModifyException, TranscodeException, IOException {
        VTDGen vg = new VTDGen();
        if (!vg.parseFile(one, false))
            return;
        VTDNav vn1=vg.getNav();
        if(!vg.parseFile(two, false))
            return;
        VTDNav vn2 = vg.getNav();
        XMLModifier xm = new XMLModifier(vn1);
        long l = vn2.getContentFragment();
        xm.insertBeforeTail(vn2, l);
        xm.output(ModLoaderXController.source + "\\library\\texts.xml" );
    }

}
