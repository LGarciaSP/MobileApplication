package com.healthcareapp.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcareapp.myapplication.Adapter.ItemAdapter;
import com.healthcareapp.myapplication.DBItem;
import com.healthcareapp.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SeriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<DBItem> mItems;
    FirebaseDatabase databse;
    DatabaseReference itemsRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_series, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mItems = new ArrayList<>();

        readItems();

        // Inflate the layout for this fragment
        return view;
    }

    private void readItems() {

        DatabaseReference itemsRef = FirebaseDatabase.getInstance("https://my-application-e231c-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Items");

        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DBItem dbItem = snapshot.getValue(DBItem.class);
                    assert dbItem != null;
                    if ( dbItem.getCategories().equals("Series") ){
                        mItems.add(dbItem);
                    }

                }

                itemAdapter = new ItemAdapter(getContext(), mItems);
                recyclerView.setAdapter(itemAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}