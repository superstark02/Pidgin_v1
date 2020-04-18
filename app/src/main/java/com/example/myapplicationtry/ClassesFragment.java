package com.example.myapplicationtry;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassesFragment newInstance(String param1, String param2) {
        ClassesFragment fragment = new ClassesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    DatabaseReference reference,reference1,reference2;
    ArrayList<Classes> list;
    MyAdapterClasses adapter;
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    SearchView searchView;

    MyAdapterContent content;
    ArrayList<HomeImages> list1;
    LinearLayoutManager HorizontalLayout;

    MyAdapterClassesCategories classesCategories;
    ArrayList<ClassesCategories> list2;
    LinearLayoutManager HorizontalLayout1;
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
        View view = inflater.inflate(R.layout.fragment_classes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.classes);
        searchView = (SearchView) view.findViewById(R.id.search);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.images_classes);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.categories);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        String keyword = getActivity().getIntent().getStringExtra("keyword");

        HorizontalLayout
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView1.setLayoutManager(HorizontalLayout);

        HorizontalLayout1
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView2.setLayoutManager(HorizontalLayout1);

        reference = FirebaseDatabase.getInstance().getReference().child("Classes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Classes>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Classes p = dataSnapshot1.getValue(Classes.class);
                    list.add(p);
                }
                adapter = new MyAdapterClasses(getActivity(),list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        reference1 = FirebaseDatabase.getInstance().getReference().child("Images").child("Classes");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<HomeImages>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HomeImages p = dataSnapshot1.getValue(HomeImages.class);
                    list1.add(p);
                }
                content = new MyAdapterContent(getActivity(), list1);
                recyclerView1.setAdapter(content);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("Images").child("Categories");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<ClassesCategories>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ClassesCategories p = dataSnapshot1.getValue(ClassesCategories.class);
                    list2.add(p);
                }
                classesCategories = new MyAdapterClassesCategories(getActivity(), list2);
                recyclerView2.setAdapter(classesCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }


        return view;
    }

    private void search(String str){
        ArrayList<Classes> myList = new ArrayList<>();
        for(Classes object:list){
            if(object.getName().toLowerCase().contains(str.toLowerCase()) || object.getEmail().toLowerCase().contains(str.toLowerCase())||object.getType().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
                MyAdapterClasses myAdapter = new MyAdapterClasses(getActivity(),myList);
                recyclerView.setAdapter(myAdapter);
            }
        }
    }

}
