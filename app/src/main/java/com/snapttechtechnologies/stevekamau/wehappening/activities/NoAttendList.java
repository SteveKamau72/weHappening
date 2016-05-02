package com.snapttechtechnologies.stevekamau.wehappening.activities;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.snapttechtechnologies.stevekamau.wehappening.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoAttendList extends Fragment {


    public NoAttendList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_no_attend_list, container, false);

        TextView txt1 = (TextView) rootView.findViewById(R.id.one);
        TextView txt2 = (TextView) rootView.findViewById(R.id.two);

        String fontPath = "fonts/Centurygothic.ttf";
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        txt1.setTypeface(tf);
        txt2.setTypeface(tf);


        final View one = rootView.findViewById(R.id.img);
        final View two = rootView.findViewById(R.id.one);
        final View three = rootView.findViewById(R.id.two);
        final View four = rootView.findViewById(R.id.three);

        animateSampleOne(one,two,three,four);
        return rootView;
    }
    private void animateSampleOne(View one, View two, View three, View four) {
        final PropertyAction fabAction = PropertyAction.newPropertyAction(three).scaleX(0).scaleY(0).duration(750).interpolator(new AccelerateDecelerateInterpolator()).build();
        final PropertyAction headerAction = PropertyAction.newPropertyAction(two).interpolator(new DecelerateInterpolator()).translationY(-200).duration(550).alpha(0.4f).build();
        final PropertyAction bottomAction = PropertyAction.newPropertyAction(four).translationY(500).duration(750).alpha(0f).build();
        Player.init().
                animate(headerAction).
                then().
                animate(fabAction).
                then().
                animate(bottomAction).
                play();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
