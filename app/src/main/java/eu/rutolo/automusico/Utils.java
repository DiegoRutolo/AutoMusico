package eu.rutolo.automusico;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Utils {

    public static final String DTAG = "DEBUUUUG ~";

    public static final String SUBDIR_COMPOSICIONES = "compsiciones";
    public static final String SUBDIR_FRAGMENTOS = "fragmentos";
    public static final String NOMBRE_PREFERENCIAS = "prefsGenerales";

    // https://stackoverflow.com/a/10887697
    public static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

}
