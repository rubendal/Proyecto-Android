package com.example.represmash.appdoctor;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment implements AsyncMethodActivity{


    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    public void registrarPaciente2(View view){
        EditText nombre = (EditText) getActivity().findViewById(R.id.nombre2);
        EditText telefono = (EditText) getActivity().findViewById(R.id.telefono2);
        EditText edad = (EditText) getActivity().findViewById(R.id.edad2);
        EditText contacto = (EditText) getActivity().findViewById(R.id.contacto2);
        EditText telefonoContacto = (EditText) getActivity().findViewById(R.id.telefono_contacto2);
        RadioGroup genero = (RadioGroup) getActivity().findViewById(R.id.genero2);

        int g = 0;
        switch(genero.getCheckedRadioButtonId()){
            case R.id.M2:
                g=0;
                break;
            case R.id.F2:
                g=1;
                break;
        }

        try {
            Paciente paciente = new Paciente(nombre.getText().toString(), telefono.getText().toString(),
                    Integer.parseInt(edad.getText().toString()), g, contacto.getText().toString(), telefonoContacto.getText().toString());

            if (paciente.isComplete()) {
                new PostAsyncTask(getActivity(), paciente.generatePOSTParams(Sesion.ID), "Registrando paciente",this,true).execute(Servidor.Direccion("/doctor/registro.php"));
            }else {
                Toast.makeText(getActivity(), R.string.faltan_datos, Toast.LENGTH_SHORT).show();
                //getActivity().getFragmentManager().popBackStack();
            }
        }catch(Exception e){
            Toast.makeText(getActivity(), R.string.faltan_datos, Toast.LENGTH_SHORT).show();
            //getActivity().getFragmentManager().popBackStack();
        }

    }


    @Override
    public void Haz(String res) {
        try {
            int i = Integer.parseInt(res);
            if (i != -1) {
                Toast.makeText(getActivity(), R.string.paciente_registrado, Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                getActivity().getFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            getActivity().getFragmentManager().popBackStack();
        }
    }
}
