    package com.example.appturnos;

    import android.app.AlertDialog;
    import android.content.Intent;
    import android.icu.text.SimpleDateFormat;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.widget.AppCompatImageButton;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;

    import com.example.appturnos.databinding.FragmentDetalleBinding;

    import com.example.appturnos.models.Turno;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.Locale;

    public class DetalleFragment extends Fragment {

        private FragmentDetalleBinding binding;

        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState
        ) {
            binding = FragmentDetalleBinding.inflate(inflater, container, false);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Turno turno;
            if (getArguments() != null) {
                turno = (Turno) getArguments().getSerializable("turno");
            } else {
                turno = null;
            }

            TextView cliente = getView().findViewById(R.id.clienteTextView);
            cliente.setText(turno.getNombreCliente() + " - " + turno.getDniCliente());


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String fechaFormateada = sdf.format(turno.getFechaTurno());

            TextView fecha = getView().findViewById(R.id.fechaTextView);
            fecha.setText(fechaFormateada);



            TextView direccion = getView().findViewById(R.id.direccionTextView);
            direccion.setText(turno.getDireccion());

            TextView detalle = getView().findViewById(R.id.detalleTextView);
            detalle.setText(turno.getDetalle());

            AppCompatImageButton  phoneButton = getView().findViewById(R.id.phoneButton);
            AppCompatImageButton emailButton = getView().findViewById(R.id.emailButton);
            AppCompatImageButton deleteButton = getView().findViewById(R.id.deleteButton);
            AppCompatImageButton editButton = getView().findViewById(R.id.editButton);


            phoneButton.setOnClickListener(v -> {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + turno.getTelefono()));
                startActivity(callIntent);
            });

            deleteButton.setOnClickListener(v -> deleteTurno(turno.getId()));
            editButton.setOnClickListener(v -> editTurno(turno));
            emailButton.setOnClickListener(v -> sendEmail(turno));
        }

        private void sendEmail(Turno turno) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Adjuntar detalle")
                    .setMessage("¿Desea adjuntar el detalle al cuerpo del correo?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                        String fechaFormateada = sdf.format(turno.getFechaTurno());
                        String asunto = "Turno: " + turno.getDireccion() + " - " + fechaFormateada;
                        String body = "Señor/a: " + turno.getNombreCliente() + ", le recordamos que tiene un turno pendiente en " + turno.getDireccion() +
                                " el día " + fechaFormateada + "\n\n" +
                                turno.getDetalle();

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:" + turno.getEmailCliente() +
                                "?subject=" + Uri.encode(asunto) +
                                "&body=" + Uri.encode(body)));
                        startActivity(emailIntent);
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                        String fechaFormateada = sdf.format(turno.getFechaTurno());
                        String asunto = "Turno: " + turno.getDireccion() + " - " + fechaFormateada;
                        String body = "Señor/a: " + turno.getNombreCliente() + ", le recordamos que tiene un turno pendiente en " + turno.getDireccion() +
                                " el día " + fechaFormateada;

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:" + turno.getEmailCliente() +
                                "?subject=" + Uri.encode(asunto) +
                                "&body=" + Uri.encode(body)));
                        startActivity(emailIntent);
                    })
                    .show();
        }

        private void deleteTurno(String idTurno) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("AppTurnos").document(idTurno)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Turno eliminado con éxito", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireView());
                        navController.navigate(R.id.action_DetalleFragment_to_TurnosFragment);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Error al eliminar el turno", Toast.LENGTH_SHORT).show());
        }

        public void editTurno(Turno turno){
            Bundle bundle = new Bundle();
            bundle.putSerializable("turno", turno);

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_DetalleFragment_to_FormTurnoFragment, bundle);
        }
    }


