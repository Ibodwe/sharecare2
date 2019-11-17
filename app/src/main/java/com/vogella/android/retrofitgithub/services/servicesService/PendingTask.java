package com.vogella.android.retrofitgithub.services.servicesService;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.services.servicesList.ListViewAdapter;
import com.vogella.android.retrofitgithub.services.servicesList.PendingListAdapter;
import com.vogella.android.retrofitgithub.services.servicesList.ServiceListItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PendingTask.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PendingTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingTask extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AcceptedTask acceptedTask = new AcceptedTask();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<ServiceListItem> serviceListItems = acceptedTask.getServiceListItems();
    private OnFragmentInteractionListener mListener;

    public PendingTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingTask.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingTask newInstance(String param1, String param2) {
        PendingTask fragment = new PendingTask();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


public void add (ServiceListItem serviceListItem){

    serviceListItems.add(serviceListItem);
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_task, container, false);
        PendingListAdapter adapter = new PendingListAdapter();


        ListView listView = (ListView)view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        //replace code with data from database;
        for(int i=0; i<serviceListItems.size();i++){
            adapter.addItem(serviceListItems.get(i).getTask(),serviceListItems.get(i).getToken(),serviceListItems.get(i).getDuration(),serviceListItems.get(i).getCaretaker());
        }

        return view;
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
