package com.example.appturnos;
import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.SimpleDateFormat;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Locale;


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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        binding.add.setOnClickListener(fragment -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_TurnosFragment_to_FormTurnoFragment);
        });


        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        LinearLayout container = requireView().findViewById(R.id.linearTurnos);

        db.collection("AppTurnos").whereEqualTo("emailUsuario",user.getEmail()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    String idTurno = document.getId();
                    Turno turno = new Turno(
                            idTurno,
                            document.get("nombreCliente", String.class),
                            document.get("dniCliente", String.class),
                            document.get("detalle", String.class),
                            document.get("direccion", String.class),
                            document.get("fechaTurno", Date.class),
                            document.get("titulo", String.class),
                            document.get("horario", String.class),
                            document.get("emailUsuario", String.class),
                            document.get("emailCliente", String.class),
                            document.get("telefono", String.class)
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


                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String fechaFormateada = sdf.format(turno.getFechaTurno());

                    TextView subtitulo = new TextView(requireActivity());
                    subtitulo.setText(fechaFormateada);
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

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("turno", turno);

                    turnoLayout.setOnClickListener(v -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        navController.navigate(R.id.action_TurnosFragment_to_DetalleFragment, bundle);
                    });

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