package com.example.appturnos;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.appturnos.R;
import com.example.appturnos.models.Turno;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TurnoFormFragment extends Fragment {

    private EditText etNombreCliente, etDniCliente, etDetalle, etDireccion, etFecha, etEmail;
    private Button btnGuardarTurno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_turno_fragment, container, false);

        etNombreCliente = view.findViewById(R.id.et_nombre_cliente);
        etDniCliente = view.findViewById(R.id.et_dni_cliente);
        etDetalle = view.findViewById(R.id.et_detalle);
        etDireccion = view.findViewById(R.id.et_direccion);
        etFecha = view.findViewById(R.id.et_fecha_hora);
        etEmail = view.findViewById(R.id.et_email_cliente);
        btnGuardarTurno = view.findViewById(R.id.btn_guardar_turno);
        Turno turno;
        if (getArguments() != null) {
            turno = (Turno) getArguments().getSerializable("turno");
            if (turno != null) {
                autocompletarCampos(turno);
            }
        } else {
            turno = null;
        }

        btnGuardarTurno.setOnClickListener(v -> guardarTurno(turno));

        UsarDireccionGuardada(view);

        etFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            // Seleccionar fecha
            DatePickerDialog datePicker = new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calendar.get(Calendar.MINUTE))
                        .setTitleText("Seleccionar hora")
                        .build();

                timePicker.addOnPositiveButtonClickListener((button) -> {
                    @SuppressLint("DefaultLocale") String fechaHora = String.format(
                            "%02d/%02d/%d %02d:%02d",
                            dayOfMonth, month + 1, year,
                            timePicker.getHour(), timePicker.getMinute()
                    );
                    etFecha.setText(fechaHora);
                });

                timePicker.show(getParentFragmentManager(), "time_picker");
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            // Restringir fechas previas
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePicker.show();
        });

        return view;
    }
    private void guardarTurno(Turno turno) {
        String nombreCliente = etNombreCliente.getText().toString();
        String dniCliente = etDniCliente.getText().toString();
        String detalle = etDetalle.getText().toString();
        String direccion = etDireccion.getText().toString();
        String fecha = etFecha.getText().toString();
        String emailCliente = etEmail.getText().toString();

        if (nombreCliente.isEmpty() || dniCliente.isEmpty() || detalle.isEmpty() ||
                direccion.isEmpty() || fecha.isEmpty() || emailCliente.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Timestamp timestamp;
        try {
            Date date = sdf.parse(fecha);
            timestamp = new Timestamp(date);
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Error en el formato de fecha y hora", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> turnoData = new HashMap<>();
        turnoData.put("nombreCliente", nombreCliente);
        turnoData.put("dniCliente", dniCliente);
        turnoData.put("detalle", detalle);
        turnoData.put("direccion", direccion);
        turnoData.put("emailCliente", emailCliente);
        turnoData.put("emailUsuario", emailUsuario);
        turnoData.put("fechaTurno", timestamp);

        if (turno != null && turno.getId() != null) {
            db.collection("AppTurnos").document(turno.getId())
                    .update(turnoData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Turno editado con éxito", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).navigate(R.id.action_FormTurnosFragment_to_TurnosFragment);
                    });
        } else {
            db.collection("AppTurnos")
                    .add(turnoData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "Turno guardado con éxito", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireView()).navigate(R.id.action_FormTurnosFragment_to_TurnosFragment);
                    });
        }
    }

    private void autocompletarCampos(Turno turno) {
        etNombreCliente.setText(turno.getNombreCliente());
        etDniCliente.setText(turno.getDniCliente());
        etDetalle.setText(turno.getDetalle());
        etDireccion.setText(turno.getDireccion());
        etEmail.setText(turno.getEmailCliente());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        etFecha.setText(sdf.format(turno.getFechaTurno()));
    }

    private void UsarDireccionGuardada(View view) {
        CheckBox checkUsarDireccionGuardada = view.findViewById(R.id.check_usar_direccion_guardada);

        checkUsarDireccionGuardada.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Log.d("coso", email);

                db.collection("Usuarios").whereEqualTo("email",email).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d("coso",task.getResult().getDocuments().toString());

                        QuerySnapshot querySnapshot = task.getResult();
                        for (DocumentSnapshot turno : task.getResult().getDocuments()){
                            Log.d("coso",turno.toString());
                            if (turno.exists()) {
                                String direccionGuardada = turno.getString("direccion");
                                etDireccion.setText(direccionGuardada);
                                etDireccion.setEnabled(false);
                            } else {
                                Toast.makeText(getContext(), "No se encuentra dirección registrada", Toast.LENGTH_SHORT).show();
                                checkUsarDireccionGuardada.setChecked(false);
                            }
                        }
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                    checkUsarDireccionGuardada.setChecked(false);
                });
            } else {
                etDireccion.setEnabled(true);
                etDireccion.setText("");
            }
        });
    }
}
