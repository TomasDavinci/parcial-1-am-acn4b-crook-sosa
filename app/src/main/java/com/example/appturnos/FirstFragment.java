package com.example.appturnos;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appturnos.databinding.FragmentFirstBinding;
import com.example.appturnos.models.Turno;

import java.util.Arrays;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout container = requireView().findViewById(R.id.linearTurnos);

        List<Turno> turnos = Arrays.asList(
                new Turno("Tomás Sosa - 41564756", "8:00 AM - 9:00 AM", "Dirección 1"),
                new Turno("Turno 2", "9:00 AM - 10:00 AM", "Dirección 2"),
                new Turno("Turno 3", "10:00 AM - 11:00 AM", "Dirección 3"),
                new Turno("Turno 4", "10:00 AM - 11:00 AM", "Dirección 4"),
                new Turno("Turno 5", "10:00 AM - 11:00 AM", "Dirección 5"),
                new Turno("Turno 6", "10:00 AM - 11:00 AM", "Dirección 6"),
                new Turno("Turno 7", "10:00 AM - 11:00 AM", "Dirección 7"),
                new Turno("Turno 8", "10:00 AM - 11:00 AM", "Dirección 8"),
                new Turno("Turno 9", "10:00 AM - 11:00 AM", "Dirección 9"),
                new Turno("Turno 10", "10:00 AM - 11:00 AM", "Dirección 10"),
                new Turno("Turno 11", "10:00 AM - 11:00 AM", "Dirección 11"),
                new Turno("Turno 12", "10:00 AM - 11:00 AM", "Dirección 12"),
                new Turno("Turno 13", "10:00 AM - 11:00 AM", "Dirección 13")
        );

        for (Turno turno : turnos) {
            LinearLayout turnoLayout = new LinearLayout(requireActivity());
            turnoLayout.setOrientation(LinearLayout.VERTICAL);
            turnoLayout.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.turno_background));
            turnoLayout.setPadding(20, 30, 20, 30);

            // Crea un GradientDrawable
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setColor(ContextCompat.getColor(requireActivity(), R.color.turno_background)); // Establece el color de fondo
            drawable.setCornerRadius(20f); // Establece el radio de las esquinas (20dp en este caso)
            drawable.setStroke(2, ContextCompat.getColor(requireActivity(), R.color.black)); // Establece el borde (2px de grosor, color negro)

            turnoLayout.setBackground(drawable);

            TextView titulo = new TextView(requireActivity());
            titulo.setText(turno.getTitulo());
            titulo.setTextSize(18f);
            titulo.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
            titulo.setTypeface(null, Typeface.BOLD);

            TextView subtitulo = new TextView(requireActivity());
            subtitulo.setText(turno.getHorario());
            subtitulo.setTextSize(16f);
            subtitulo.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));


            TextView body = new TextView(requireActivity());
            body.setText(turno.getDireccion());
            body.setTextSize(14f);
            body.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));


            turnoLayout.addView(titulo);
            turnoLayout.addView(subtitulo);
            turnoLayout.addView(body);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(50, 10, 50, 0);
            turnoLayout.setLayoutParams(layoutParams);

            container.addView(turnoLayout);
        }



        /*binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}