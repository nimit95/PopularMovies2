package com.example.nimitagg.popularmovies2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.

 */
public class FDetailViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tt1, tt2, tt3, tt5;
    TextView tt4;
    Button btn1, btn2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FDetailViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FDetailViewFragment newInstance(String param1, String param2) {
        FDetailViewFragment fragment = new FDetailViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FDetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail_view, container, false);
        tt1=(TextView)view.findViewById(R.id.title);
        tt2=(TextView)view.findViewById(R.id.textView2);
        tt3= (TextView) view.findViewById(R.id.textView3);
        tt5= (TextView) view.findViewById(R.id.textView5);
        btn1= (Button) view.findViewById(R.id.button2);

        btn2= (Button) view.findViewById(R.id.button);
        Intent intent= getActivity().getIntent();
        btn1.setText("REMOVE AS\nFAVORITE");
        Picasso.with(getActivity()).load(intent.getStringExtra("poster")).networkPolicy(NetworkPolicy.OFFLINE).into((ImageView) view.findViewById(R.id.imageView2));
        tt1.setText(intent.getStringExtra("title"));
        tt2.setText(intent.getStringExtra("release"));
        if(intent.getStringExtra("overview").compareTo("")!=0)
            tt3.setText(intent.getStringExtra("overview"));

        else {
            tt3.setText("No Overview Found");
        }
        tt5.setText(intent.getStringExtra("rating"));
        btn2.setVisibility(View.GONE);
        final String temp=intent.getStringExtra("movieid");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver resolver=getActivity().getContentResolver();
                Toast.makeText(getActivity(), "Removed from favorite movie", Toast.LENGTH_SHORT).show();
                resolver.delete(TestTable.CONTENT_URI, TestTable.FIELD_MOVIEID + " = ?", new String[]{temp});
                getActivity().getContentResolver().notifyChange(TestTable.CONTENT_URI, null);
                getActivity().finish();
            }
        });

        return view;
    }

}
