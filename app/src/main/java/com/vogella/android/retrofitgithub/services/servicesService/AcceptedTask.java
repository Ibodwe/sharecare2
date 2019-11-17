package com.vogella.android.retrofitgithub.services.servicesService;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.services.servicesList.ListViewAdapter;
import com.vogella.android.retrofitgithub.services.servicesList.ServiceListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcceptedTask.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcceptedTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptedTask extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String task,token,duration,caretaker;
    public static ArrayList<ServiceListItem> serviceListItems;
    private OnFragmentInteractionListener mListener;

    public AcceptedTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcceptedTask.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptedTask newInstance(String param1, String param2) {
        AcceptedTask fragment = new AcceptedTask();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = getArguments().getString("task");
            token = getArguments().getString("token");
            duration = getArguments().getString("duration");
            caretaker = getArguments().getString("caretaker");

            serviceListItems.add(new ServiceListItem(task,duration,token,caretaker));

        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_task,container,false);
        ListViewAdapter adapter = new ListViewAdapter();

        ListView listView = (ListView)view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        serviceListItems = new ArrayList<ServiceListItem>();

        serviceListItems.add(new ServiceListItem("Help Grocery shopping","30","46 minutes","Kayle McStork"));
        serviceListItems.add(new ServiceListItem("Help Grocery shopping","30","47 minutes","Kayle McStork"));
        serviceListItems.add(new ServiceListItem("Help Grocery shopping","30","48 minutes","Kayle McStork"));

       adapter.notifyDataSetChanged();
        //replace code with data from database;

        for(int i=0; i< serviceListItems.size(); i++){
            adapter.addItem(serviceListItems.get(i).getTask().toString(),
                            serviceListItems.get(i).getToken().toString(),
                            serviceListItems.get(i).getDuration().toString(),
                            serviceListItems.get(i).getCaretaker().toString()

                    );
        }

        return view;
    }

    public ArrayList<ServiceListItem> getServiceListItems() {
        return serviceListItems;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
