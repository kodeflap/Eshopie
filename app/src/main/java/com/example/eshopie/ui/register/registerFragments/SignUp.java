package com.example.eshopie.ui.register.registerFragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.example.eshopie.HomeActivity;
import com.example.eshopie.R;
import com.example.eshopie.ui.register.registerFragments.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends Fragment {

    public SignUp() {
        // Required empty public constructor
    }

    private TextView register_login;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText conPassword;

    private ImageButton closeBtn;
    private Button registerBtn;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        register_login = view.findViewById(R.id.register_login);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);

        email = view.findViewById(R.id.register_email);
        fullName = view.findViewById(R.id.register_name);
        password = view.findViewById(R.id.register_password);
        conPassword = view.findViewById(R.id.register_confirm_password);

        closeBtn = view.findViewById(R.id.register_close);
        registerBtn = view.findViewById(R.id.register_button);

        progressBar = view.findViewById(R.id.signUp_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignIn());
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentFunction();
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
        fullName.addTextChangedListener(new TextWatcher() {
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
        conPassword.addTextChangedListener(new TextWatcher() {
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

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : send data to firebase
                checkEmailAndPassword();
            }
        });

    }

    private void checkEmailAndPassword() {

        Drawable customError = getResources().getDrawable(R.drawable.error_icon);
        customError.setBounds(0,0,customError.getIntrinsicWidth(),customError.getIntrinsicHeight());

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(conPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                registerBtn.setEnabled(false);
                registerBtn.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<Object,String> userData = new HashMap<>();
                                    userData.put("fullname",fullName.getText().toString());

                                    firebaseFirestore.collection("USERS")
                                            .add(userData)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()){
                                                        intentFunction();
                                                    }
                                                    else {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        registerBtn.setEnabled(true);
                                                        registerBtn.setTextColor(Color.rgb(255,255,255));
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });


                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    registerBtn.setEnabled(true);
                                    registerBtn.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                conPassword.setError("Password doesn't match",customError);
            }
        }
        else {
            email.setError("Invalid Email",customError);
        }
    }

    private void intentFunction() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullName.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.length() >= 8){
                    if(!TextUtils.isEmpty(conPassword.getText())){
                        registerBtn.setEnabled(true);
                        registerBtn.setTextColor(Color.rgb(255,255,255));
                    }
                    else {
                        registerBtn.setEnabled(false);
                        registerBtn.setTextColor(Color.argb(50,255,255,255));
                    }
                }
                else {
                    registerBtn.setEnabled(false);
                    registerBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }
            else {
                registerBtn.setEnabled(false);
                registerBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }
        else {
            registerBtn.setEnabled(false);
            registerBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}