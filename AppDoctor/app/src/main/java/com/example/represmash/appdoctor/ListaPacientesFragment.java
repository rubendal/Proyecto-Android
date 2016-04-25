package com.example.represmash.appdoctor;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPacientesFragment extends ListFragment implements AsyncMethodActivity{


    public ListaPacientesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        HashMap<String, String> params = new HashMap<>();
        params.put("id_doctor", Integer.toString(Sesion.ID));

        new PostAsyncTask(getActivity(), params, "Obteniendo lista", this, true).execute(Servidor.Direccion("/doctor/pacientes.php"));

        return inflater.inflate(R.layout.fragment_lista_pacientes, container, false);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Paciente paciente = (Paciente)getListAdapter().getItem(position);

        InitActivity i = (InitActivity) getActivity();

        i.mFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("paciente", paciente);
        i.mFragment.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction().replace(R.id.contenido, i.mFragment).addToBackStack("").commit();

    }

    @Override
    public void Haz(String res) {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            try {
                JSONArray jsonArray = new JSONArray(res);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    int id = data.getInt("id");
                    String nombre = data.getString("nombre");
                    String telefono = data.getString("telefono");
                    int edad = data.getInt("edad");
                    int genero = data.getInt("genero");
                    String contacto_emergencia = data.getString("contacto_emergencia");
                    String telefono_emergencia = data.getString("telefono_emergencia");

                    Paciente paciente = new Paciente(nombre, telefono, edad, genero, contacto_emergencia, telefono_emergencia);
                    paciente.setId(id);
                    pacientes.add(paciente);
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
                //getActivity().getFragmentManager().beginTransaction().replace(R.id.contenido,new BienvenidaFragment()).commit();
            }
        }catch(Exception e){
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
            //getActivity().getFragmentManager().beginTransaction().replace(R.id.contenido,new BienvenidaFragment()).commit();*/
        }
        setListAdapter(new PacienteAdapter(getActivity(),pacientes));
    }
}
