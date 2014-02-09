package com.personal.maps.kenyapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elisha on 2/2/14.
 */
public class ProvinceFragment extends ListFragment implements AdapterView.OnItemClickListener{

    Activity activity;
    public static List<String> provinces = new ArrayList<String>(){{
        add("CENTRAL");
        add("NAIROBI");
        add("R VALLEY");
        add("COAST");
        add("NYANZA");
        add("WESTERN");
        add("EASTERN");
        add("N EASTERN");
    }};

    List<String> coords = new ArrayList<String>(){{
        add("0.1508 37.3075");
        add("1.2833 36.8167");
        add("0.3000 36.0667");
        add("-4.0500 39.6667");
        add("-0.1691 34.7500");
        add("0.4531 34.1250");
        add("0.5333 37.4500");
        add("0.4569 39.6583");
    }};


    public static ArrayList<String> details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.provinces_frag, container, false);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        Log.i("MAPS", "onAttach executed successfully");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.simple_row_item, provinces);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(activity, "Clicked "+ position, Toast.LENGTH_LONG).show();
        details = new ArrayList<String>(){};
        details.add(provinces.get(position));
        details.add(coords.get(position));

        try{
            ((OnProvinceSelectedListener) activity).loadProvinceDetails(details);
        }catch (ClassCastException cce){

        }
    }

    public interface OnProvinceSelectedListener{
        public void loadProvinceDetails(ArrayList<String> details);
    }
}
