package com.firejobcourse.apps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firejobcourse.apps.info.DataInfo;
import com.firejobcourse.apps.info.InfoActivity;
import com.firejobcourse.apps.info.InfoAdapter;
import com.firejobcourse.apps.info.InfoInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeAct extends AppCompatActivity implements InfoInterface {

    RecyclerView rvData;
    InfoAdapter adapter;
    private ArrayList<DataInfo> dataInfos;
    DatabaseReference reference;
    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reference = FirebaseDatabase.getInstance().getReference().child("Info");
        storage = FirebaseStorage.getInstance().getReference();

        dataInfos = new ArrayList<>();
        rvData = findViewById(R.id.rvData);
        adapter = new InfoAdapter(this, this, storage);

        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter.submitData(
                dataInfos
        );

        setupListener();
    }

    private void setupListener() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataInfos.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.e("TAG setup", "onDataChange: " + data.toString());
                    dataInfos.add(data.getValue(DataInfo.class));
                }
                adapter.submitData(dataInfos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(DataInfo dataInfo) {
        startActivity(
                new Intent(this, InfoActivity.class)
                        .putExtra("data", dataInfo)
        );
    }
}
