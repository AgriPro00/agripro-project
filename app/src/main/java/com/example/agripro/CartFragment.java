    package com.example.agripro;
    
    import android.content.SharedPreferences;
    import android.os.Bundle;
    
    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.os.Handler;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;
    
    import java.util.ArrayList;
    
    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link CartFragment#newInstance} factory method to
     * create an instance of this fragment.
     */
    public class CartFragment extends Fragment  implements OrderAdapter.OnItemClickListener {
    
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
    
        private FirebaseFirestore db;
        private RecyclerView recyclerView;
        TextView total, selectedItem;
        OrderAdapter adapter;
        ArrayList<Order> orderList;
        SharedPreferences sharedPreferences;

        int Total = 0;
        int Item = 0;
        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
    
        public CartFragment() {
            // Required empty public constructor
        }
    
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
    
        public static CartFragment newInstance(String param1, String param2) {
            CartFragment fragment = new CartFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }
    
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            db = FirebaseFirestore.getInstance();
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }
    
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
    
            View view = inflater.inflate(R.layout.fragment_cart, container, false);
            recyclerView = view.findViewById(R.id.cartList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            orderList = new ArrayList<>();
            adapter = new OrderAdapter(getActivity(), (ArrayList<Order>) orderList, (OrderAdapter.OnItemClickListener) this);
            recyclerView.setAdapter(adapter);
            showOrder();
            total = view.findViewById(R.id.TotalData);
            selectedItem = view.findViewById(R.id.SelectData);
            new Handler().postDelayed(() -> {
                // Update the TextView after a delay (e.g., 1000 milliseconds)
                total.setText(String.valueOf(Total));
                selectedItem.setText(String.valueOf(Item));
            }, 1500);
            return view;
        }
    
        private String getDataFromSharedPreferences(String key, String defaultValue) {
            sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
            return sharedPreferences.getString(key, defaultValue);
        }
    
        private void showOrder() {
            Total = 0;
            Item = 0;
            String Id = getDataFromSharedPreferences("Id", "none").toString();
    
            db.collection("customer")
                    .document(Id)
                    .collection("order")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                orderList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Order item = document.toObject(Order.class);
                                    orderList.add(item);
                                    Total += item.getRetailPrice();
                                    Item += 1;
                                }
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        public void onEditClick(int position) {
            Order order = orderList.get(position);
    
    //        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
    //        intent.putExtra("order_id", order.getId());
    //        intent.putExtra("order_name", order.getName());
    //        startActivity(intent);
        }
    
    
        public void onRemoveClick(int position) {
            Order order = orderList.get(position);
            String Id = getDataFromSharedPreferences("Id", "none").toString();
            String OrderId = order.getOrderId();
            db.collection("customer").document(Id).collection("order").document(OrderId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            showOrder();
        }
    }