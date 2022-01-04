package com.example.eshopie;

import static com.example.eshopie.RegisterActivity.onResetPasswordFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends Fragment {


    public SignIn() {
        // Required empty public constructor
    }

    private TextView login_register;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;

    private ImageButton close_button;
    private Button loginButton;

    private  TextView forgotPassword;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
       login_register = view.findViewById(R.id.login_register);
       parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);

       email = view.findViewById(R.id.login_email);
       password = view.findViewById(R.id.login_password);

       close_button = view.findViewById(R.id.login_close);
       loginButton = view.findViewById(R.id.login_button);

       forgotPassword = view.findViewById(R.id.login_forgot_password);

       firebaseAuth = FirebaseAuth.getInstance();

       progressBar = view.findViewById(R.id.signIn_progressBar);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUp());
            }
        });

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentFunction();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPassword());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

    }

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.length() >= 8){

                progressBar.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                loginButton.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    intentFunction();
                                }
                                else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    loginButton.setEnabled(true);
                                    loginButton.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                loginButton.setEnabled(true);
                loginButton.setTextColor(Color.rgb(255,255,255));
            }
            else {
                loginButton.setEnabled(false);
                loginButton.setTextColor(Color.argb(50,25,255,255));
            }
        }
        else {
            loginButton.setEnabled(false);
            loginButton.setTextColor(Color.argb(50,25,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);

        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    private void intentFunction() {
        Intent intent = new Intent(getActivity(),HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}