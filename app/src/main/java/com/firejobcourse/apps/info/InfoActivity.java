package com.firejobcourse.apps.info;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firejobcourse.apps.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    private DataInfo dataInfo;

    TextView tvTitle, tvTime ,tvDesc;
    ImageView ivInfo;
    LinearLayout btnBack;
    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        storage = FirebaseStorage.getInstance().getReference();

        tvTitle = findViewById(R.id.tvTitle);
        tvTime = findViewById(R.id.tvTime);
        tvDesc = findViewById(R.id.tvDesc);
        ivInfo = findViewById(R.id.ivInfo);
        btnBack = findViewById(R.id.btnBack);

        dataInfo = getIntent().getParcelableExtra("data");

        tvTitle.setText(dataInfo.getJudul_info());
        tvTime.setText(dataInfo.getJadwal());
        tvDesc.setText(dataInfo.getDeskripsi());

        StorageReference ref = storage.child(dataInfo.getLink_img());

        ref.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downUri = task.getResult();
                String imageUrl = downUri.toString();

                Picasso.with(this)
                        .load(imageUrl)
                        .centerCrop()
                        .fit()
                        .into(ivInfo);
            }else{
                Toast.makeText(this, "Gagal Load Image", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

    }
}