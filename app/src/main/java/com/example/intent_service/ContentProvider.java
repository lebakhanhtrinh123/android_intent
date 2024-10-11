package com.example.intent_service;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ContentProvider extends AppCompatActivity {

    private static final int REQUEST_CONTACTS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider);

        Button buttonViewContacts = findViewById(R.id.button_view_contacts);

        buttonViewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra quyền truy cập danh bạ
                if (ContextCompat.checkSelfPermission(ContentProvider.this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // Yêu cầu quyền nếu chưa được cấp
                    ActivityCompat.requestPermissions(ContentProvider.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_CONTACTS_PERMISSION);
                } else {
                    // Nếu đã được cấp quyền, chuyển đến trang hiển thị danh bạ
                    openContacts();
                }
            }
        });
    }

    // Phương thức mở trang danh bạ
    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }

    // Xử lý khi người dùng cấp hoặc từ chối quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CONTACTS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Người dùng đã cấp quyền, mở danh bạ
                openContacts();
            } else {
                // Quyền bị từ chối
                Toast.makeText(this, "Bạn cần cấp quyền truy cập danh bạ để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}