package com.example.appturnos;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.appturnos.databinding.FragmentTurnosBinding;
import com.example.appturnos.models.Turno;
import com.google.firebase.Firebase;

import java.util.Arrays;
import java.util.List;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.DocumentReference;


public class TurnosFragment extends Fragment {

    private FragmentTurnosBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTurnosBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        binding.add.setOnClickListener(fragment -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_TurnosFragment_to_FormTurnoFragment);
        });


        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /*
        List<Turno> turnos = Arrays.asList(
                new Turno(1L, "Cliente 1", "12345678", "Detalle 1", "Direccion 1", Timestamp.now(), "Titulo 1", "Horario 1"),
                new Turno(2L, "Cliente 2", "87654321", "Detalle 2", "Direccion 2", Timestamp.now(), "Titulo 2", "Horario 2"),
                new Turno(3L, "Cliente 3", "45678912", "Detalle 3", "Direccion 3", Timestamp.now(), "Titulo 3", "Horario 3")
        );

        WriteBatch batch = db.batch();

        for (Turno turno : turnos) {
            DocumentReference docRef = db.collection("AppTurnos").document(); // ID automático
            batch.set(docRef, turno);
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Registros subidos exitosamente");
            } else {
                Log.e("Firebase", "Error al subir registros", task.getException());
            }
        });*/





        LinearLayout container = requireView().findViewById(R.id.linearTurnos);

        db.collection("AppTurnos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {

                    Turno turno = new Turno(
                            document.get("idUsuario", Long.class),
                            document.get("nombreCliente", String.class),
                            document.get("dniCliente", String.class),
                            document.get("detalle", String.class),
                            document.get("direccion", String.class),
                            document.get("fechaTurno", Timestamp.class),
                            document.get("titulo", String.class),
                            document.get("horario", String.class)
                    );

                    LinearLayout turnoLayout = new LinearLayout(requireActivity());
                    turnoLayout.setOrientation(LinearLayout.VERTICAL);
                    turnoLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.primary));
                    turnoLayout.setPadding(20, 30, 20, 30);


                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    drawable.setColor(ContextCompat.getColor(requireActivity(), R.color.primary));
                    drawable.setCornerRadius(20f);
                    drawable.setStroke(2, ContextCompat.getColor(requireActivity(), R.color.black));

                    turnoLayout.setBackground(drawable);

                    TextView titulo = new TextView(requireActivity());
                    titulo.setText(turno.getNombreCliente() + " - " + turno.getDniCliente());
                    titulo.setTextSize(getResources().getDimension(R.dimen.titulo_size));
                    titulo.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    titulo.setTypeface(null, Typeface.BOLD);

                    TextView subtitulo = new TextView(requireActivity());
                    subtitulo.setText(turno.getHorario());
                    subtitulo.setTextSize(getResources().getDimension(R.dimen.subtitulo_size));
                    subtitulo.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));


                    TextView body = new TextView(requireActivity());
                    body.setText(turno.getDireccion());
                    body.setTextSize(getResources().getDimension(R.dimen.body_size));
                    body.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));


                    turnoLayout.addView(titulo);
                    turnoLayout.addView(subtitulo);
                    turnoLayout.addView(body);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(50, 10, 50, 10);
                    turnoLayout.setLayoutParams(layoutParams);

                    container.addView(turnoLayout);
                }
            } else {
                // Error al obtener la colección
                Log.d("prueba","no anduvo");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}