package com.example.cobalogin;


import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadName, uploadDesc, uploadVenue, uploadDate, uploadPrice;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadImage);
        uploadName = findViewById(R.id.uploadName);
        uploadVenue = findViewById(R.id.uploadVenue);
        uploadDate = findViewById(R.id.uploadDate);
        uploadPrice = findViewById(R.id.uploadPrice);
        uploadDesc = findViewById(R.id.uploadDesc);




        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        uploadImage.setImageURI(uri);
                    } else {
                        Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        uploadImage.setOnClickListener(view -> {
            Intent photoPicker = new Intent (Intent.ACTION_PICK);
            photoPicker.setType("image/*");
            activityResultLauncher.launch(photoPicker);
        });

        saveButton.setOnClickListener(view -> saveData());
    }

    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("band Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            uriTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri urlImage = task.getResult();
                    imageURL = urlImage.toString();
                    uploadData();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Toast.makeText(UploadActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });
//            while (!uriTask.isComplete());
//            Uri urlImage = uriTask.getResult();
//            imageURL = urlImage.toString();
//            uploadData();
//            dialog.dismiss();
        }).addOnFailureListener(e -> dialog.dismiss());
    }
    public void uploadData(){

        String name = uploadName.getText().toString();
        String venue= uploadVenue.getText().toString();
        String date= uploadDate.getText().toString();
        String price= uploadPrice.getText().toString();
        String desc= uploadDesc.getText().toString();



//        String title = uploadTopic.getText().toString();
//        String desc = uploadDesc.getText().toString();
//        String lang = uploadLang.getText().toString();

        DataClass dataClass = new DataClass(name, venue,  date,  price,  desc, imageURL);
        FirebaseDatabase.getInstance().getReference("ticket")
                .push().setValue(dataClass).addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Toast.makeText(UploadActivity.this, "Save success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadActivity.this, ListActivity.class);
                    startActivity(intent);

                }).addOnFailureListener(e -> Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}
