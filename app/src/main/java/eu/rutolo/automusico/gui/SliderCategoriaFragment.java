package eu.rutolo.automusico.gui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.rutolo.automusico.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SliderCategoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SliderCategoriaFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_CATEG = "idCateg";
    private static final String NOM_CATEG = "nomCateg";

    private int idCateg;
    private String nomCateg;

    public SliderCategoriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nomCateg Parameter 1.
     * @param nomCateg Parameter 2.
     * @return A new instance of fragment SliderCategoriaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SliderCategoriaFragment newInstance(int idCateg, String nomCateg) {
        SliderCategoriaFragment fragment = new SliderCategoriaFragment();
        Bundle args = new Bundle();
        args.putInt(ID_CATEG, idCateg);
        args.putString(NOM_CATEG, nomCateg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idCateg = getArguments().getInt(ID_CATEG);
            nomCateg = getArguments().getString(NOM_CATEG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider_categoria, container, false);
    }
}
