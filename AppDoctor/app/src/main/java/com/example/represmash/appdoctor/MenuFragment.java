package com.example.represmash.appdoctor;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.represmash.appdoctor.db.DBServer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private Paciente paciente;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        TextView nombre = (TextView) getView().findViewById(R.id.nombre_paciente2);
        TextView contacto = (TextView) getView().findViewById(R.id.contacto_emergencia2);
        TextView telefono = (TextView) getView().findViewById(R.id.telefono_emergencia2);

        if (bundle != null){
            paciente = (Paciente) bundle.getSerializable("paciente");
            nombre.setText(paciente.getNombre());
            contacto.setText(paciente.getNombre_emergencia());
            telefono.setText(paciente.getTelefono_emergencia());
            DBServer.download(getActivity(),paciente);
            Graph.showGraph(getActivity(), R.id.graph2, paciente);
        }
    }

    public void llamar2(View v){

        //Llamar al telefono del paciente
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + paciente.getTelefono()));
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        }
    }

    public void mandarAlerta2(View view){

        InitActivity i = (InitActivity) getActivity();
        i.aFragment = new AlertaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("paciente", paciente);
        i.aFragment.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction().replace(R.id.contenido, i.aFragment).addToBackStack(".").commit();

    }
}
