package com.example.appturnos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        mAuth = FirebaseAuth.getInstance();

        EditText emailField = view.findViewById(R.id.et_email);
        EditText passwordField = view.findViewById(R.id.et_password);
        EditText confirmPasswordField = view.findViewById(R.id.et_confirm_password);
        EditText direccionField = view.findViewById(R.id.et_direccion);
        Button registerButton = view.findViewById(R.id.btn_register);
        Button loginButton = view.findViewById(R.id.btn_login);


        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();
            String direccion = direccionField.getText().toString();

            emailField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            direccionField.setText("");

            if (!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                if (password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    registrarUsuario(direccion, email);
                                    NavController navController = Navigation.findNavController(view);
                                    navController.navigate(R.id.action_registroFragment_to_turnosFragment);
                                } else {
                                    Toast.makeText(getContext(), "Error en el registro de usuario", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Faltan datos por completar", Toast.LENGTH_SHORT).show();
            }
        });


        loginButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_registroFragment_to_loginFragment);
        });

        return view;
    }

    private void registrarUsuario(String direccion, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userData = new HashMap<>();
        userData.put("direccion", direccion);
        userData.put("email", email);



        db.collection("Usuarios").add(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "No se pudo guardar la dirección", Toast.LENGTH_SHORT).show();
                });
    }
}
