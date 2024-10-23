package com.example.agripro;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GridView gridView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandList;
    private ProgressBar progressBar;

    private FirebaseFirestore db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = view.findViewById(R.id.gridViewBrand);
        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(getContext(), brandList);
        gridView.setAdapter(brandAdapter);
        progressBar = view.findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        String category = "Feeds";
        showBrand(category);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Brand clickedBrand = (Brand) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ProductItem.class);
                // Put extra data into the Intent
                intent.putExtra("ProductName", clickedBrand.getProductName());
                intent.putExtra("Category", clickedBrand.getCategory());

                // Start the new Activity
                startActivity(intent);
            }
        });

        return view;
    }

    private void showBrand(String category) {
        CollectionReference citiesRef = db.collection("postCategory");
        Query query = citiesRef.whereEqualTo("Category", category);

        progressBar.setVisibility(View.VISIBLE);

        db.collection("postCategory")
                .whereEqualTo("Category",category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            brandList.clear(); // Clear the list before adding new items
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Brand item = document.toObject(Brand.class);
                                brandList.add(item);
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    brandAdapter.notifyDataSetChanged(); // Notify the adapter about data changes

                                    // Hide the ProgressBar once data is loaded
                                    progressBar.setVisibility(View.GONE);
                                }
                            }, 2000);
                        } else {
                            // Handle the error here
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}