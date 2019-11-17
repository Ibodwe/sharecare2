package com.vogella.android.retrofitgithub.services.servicesList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vogella.android.retrofitgithub.R;
import com.vogella.android.retrofitgithub.authentication.RequestDetail.Request_detail;
import com.vogella.android.retrofitgithub.services.serviceQR.ServiceQR;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList

    ImageView status;
    private ArrayList<ServiceListItem> request_listItems = new ArrayList<>() ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() { }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return request_listItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.request_list_item, parent, false);

        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView task = convertView.findViewById(R.id.task);
        TextView token = convertView.findViewById(R.id.token);
        TextView duration = convertView.findViewById(R.id.duration);
        TextView cartaker = convertView.findViewById(R.id.caretaker);
        Button Edit = convertView.findViewById(R.id.Edit);
        Button chekcIn =convertView.findViewById(R.id.chekcIn);

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Request_detail.class);
                intent.putExtra("title",task.getText().toString());
                intent.putExtra("hours",token.getText().toString());
                intent.putExtra("description",duration.getText().toString());

                context.startActivity(intent);

            }
        });

        chekcIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ServiceQR.class);

                context.startActivity(intent);
            }
        });
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ServiceListItem request_listItem = request_listItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        task.setText(request_listItem.getTask());
        token.setText(request_listItem.getToken());
        duration.setText(request_listItem.getDuration());
        cartaker.setText(request_listItem.getCaretaker());



        return convertView;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return request_listItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( String task, String token, String duration, String caretaker) {
        ServiceListItem item = new ServiceListItem( task,  token,  duration, caretaker);
        item.setTask(task);
        item.setToken(token);
        item.setDuration(duration);
        item.setCaretaker(caretaker);

        request_listItems.add(item);
    }

    @Override
    public void onClick(View v) {

    }
}

