package com.example.gimnasio;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ADAPTADOR.MyAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear una lista de elementos
        List<MyAdapter.Item> items = new ArrayList<>();
        items.add(new MyAdapter.Item(R.drawable.chest, "6 días", "PECHO", "EQUIPAMIENTO GIMNASIO"));
        items.add(new MyAdapter.Item(R.drawable.back, "5 días", "ESPALDA", "EQUIPAMIENTO GIMNASIO"));
        items.add(new MyAdapter.Item(R.drawable.legs, "4 días", "PIERNA", "EQUIPAMIENTO GIMNASIO"));
        items.add(new MyAdapter.Item(R.drawable.arms, "3 días", "BRAZOS", "EQUIPAMIENTO GIMNASIO"));


        adapter = new MyAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
