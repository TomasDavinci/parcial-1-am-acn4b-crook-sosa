package com.example.appturnos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.appturnos.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TurnoFormFragment extends Fragment {

    private EditText etNombreCliente, etDniCliente, etDetalle, etDireccion, etFecha, etHorario;
    private Button btnGuardarTurno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_turno_fragment, container, false);

        etNombreCliente = view.findViewById(R.id.et_nombre_cliente);
        etDniCliente = view.findViewById(R.id.et_dni_cliente);
        etDetalle = view.findViewById(R.id.et_detalle);
        etDireccion = view.findViewById(R.id.et_direccion);
        etFecha = view.findViewById(R.id.et_fecha);
        etHorario = view.findViewById(R.id.et_horario);
        btnGuardarTurno = view.findViewById(R.id.btn_guardar_turno);

        btnGuardarTurno.setOnClickListener(v -> guardarTurno());

        return view;
    }

    private void guardarTurno() {
        String nombreCliente = etNombreCliente.getText().toString();
        String dniCliente = etDniCliente.getText().toString();
        String detalle = etDetalle.getText().toString();
        String direccion = etDireccion.getText().toString();
        String fecha = etFecha.getText().toString();
        String horario = etHorario.getText().toString();

        if (nombreCliente.isEmpty() || dniCliente.isEmpty() || detalle.isEmpty() ||
                direccion.isEmpty() || fecha.isEmpty() || horario.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> turno = new HashMap<>();
        turno.put("nombreCliente", nombreCliente);
        turno.put("dniCliente", dniCliente);
        turno.put("detalle", detalle);
        turno.put("direccion", direccion);
        turno.put("fecha", fecha);
        turno.put("horario", horario);
        turno.put("timestamp", Timestamp.now());

        db.collection("AppTurnos")
                .add(turno)
                .addOnSuccessListener(documentReference ->{
                    Toast.makeText(getContext(), "Turno guardado con éxito", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_FormTurnosFragment_to_TurnosFragment);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error al guardar el turno", Toast.LENGTH_SHORT).show());
    }

}
