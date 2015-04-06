package br.com.newagemobile.example;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.newagemobile.revealtextview.RevealTextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        RevealTextView revealTextView = (RevealTextView) v.findViewById(R.id.text);
        revealTextView.setDuration(2500);
        revealTextView.show();

        return v;
    }
}
