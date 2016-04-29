package com.example.represmash.appdoctor;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertaFragment extends Fragment implements AsyncMethodActivity{

    private Paciente paciente;
    private EditText alerta;

    public AlertaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alerta, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alerta = (EditText) getView().findViewById(R.id.alerta2);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            paciente = (Paciente) bundle.getSerializable("paciente");
        }

    }

    public void enviar2(View v){

        HashMap<String,String> params = new HashMap<>();
        params.put("paciente", paciente.getId() + "");
        params.put("alerta", alerta.getText().toString());
        new PostAsyncTask(getActivity(), params, getString(R.string.enviando_alerta),this).execute(Servidor.Direccion("/doctor/alerta.php"));

    }

    @Override
    public void Haz(String res) {
        try{
            int i = Integer.parseInt(res);
            if (i != -1) {
                Toast.makeText(getActivity(), R.string.alerta_enviada, Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            }
        }catch(Exception e){
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        }
    }
}
