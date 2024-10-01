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

import com.example.appturnos.databinding.FragmentTurnosBinding;
import com.example.appturnos.models.Turno;

import java.util.Arrays;
import java.util.List;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout container = requireView().findViewById(R.id.linearTurnos);

        List<Turno> turnos = Arrays.asList(
                new Turno("Cliente 1 - 41564756", "8:00 AM - 9:00 AM", "Detalle 1"),
                new Turno("Cliente 2 - 14651871", "9:05 AM - 9:30 AM", "Detalle 2"),
                new Turno("Cliente 3 - 66847454", "9:50 AM - 10:30 AM", "Detalle 3"),
                new Turno("Cliente 4 - 65496875", "10:35 AM - 11:30 AM", "Detalle 4"),
                new Turno("Cliente 5 - 33114478", "12:30 AM - 01:00 PM", "Detalle 5"),
                new Turno("Cliente 6 - 51467441", "01:20 PM - 02:00 PM", "Detalle 6"),
                new Turno("Cliente 7 - 51546874", "02:00 PM - 02:40 PM", "Detalle 7"),
                new Turno("Cliente 8 - 33694577", "03:00 PM - 03:00 PM", "Detalle 8"),
                new Turno("Cliente 9 - 14156558", "05:00 PM - 05:30 PM", "Detalle 9"),
                new Turno("Cliente 10 - 46235451", "06:00 PM - 06:30 PM", "Detalle 10"),
                new Turno("Cliente 11 - 86247566", "07:00 PM - 07:30 PM", "Detalle 11")
        );

        for (Turno turno : turnos) {
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
            titulo.setText(turno.getTitulo());
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}