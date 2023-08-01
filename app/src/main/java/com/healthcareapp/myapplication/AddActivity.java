package com.healthcareapp.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Objects;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText inputName;
    ImageView gallery;
    Spinner spinnerCategories;
    Button enterBttn;
    StorageReference storageReference;
    DatabaseReference itemsRef;
    String imageURL;
    String id= "";

    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        storageReference = FirebaseStorage.getInstance().getReference().child("uploads");

        Spinner Categories = findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Categories, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Categories.setAdapter(adapter2);
        Categories.setOnItemSelectedListener(this);

        inputName = findViewById(R.id.inputName);
        gallery = findViewById(R.id.gallery);
        spinnerCategories = findViewById(R.id.spinnerCategories);
        enterBttn = findViewById(R.id.enterBttn);

        enterBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertItem();
            }
        });


        ImageView gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });


        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myInt);
            }
        });
    }

    private void insertItem(){
        String name = inputName.getText().toString();
        String categories = spinnerCategories.getSelectedItem().toString();

        DBItem dbItem = new DBItem(name,categories, imageURL);
        uploadImage(selectedImage, dbItem);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){

            selectedImage =data.getData();
//            ImageView imageView = findViewById(R.id.itemImage);
//            imageView.setImageURI(selectedImage);
        }
    }

    private void uploadImage(Uri selectedImage, DBItem dbItem) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        String fileExtension = mimeTypeMap.getMimeTypeFromExtension(resolver.getType(selectedImage));

        StorageReference storage = storageReference.child(System.currentTimeMillis() + "." + fileExtension);
        StorageTask upload = storage.putFile(selectedImage);

        upload.continueWithTask(new Continuation() {
            @Override
            public Task<Uri> then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                return storage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String uri = task.getResult().toString();
                    dbItem.setImageURL(uri);

                    itemsRef = FirebaseDatabase.getInstance("https://my-application-e231c-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Items");
                    itemsRef.push().setValue(dbItem);
                }
                else {
                    Toast.makeText(AddActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
