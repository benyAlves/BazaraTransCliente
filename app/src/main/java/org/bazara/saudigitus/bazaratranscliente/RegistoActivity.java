package org.bazara.saudigitus.bazaratranscliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.internal.feedback.StepperFeedbackType;

import org.bazara.saudigitus.bazaratranscliente.model.Transportador;
import org.bazara.saudigitus.bazaratranscliente.model.User;
import org.bazara.saudigitus.bazaratranscliente.registo.DataManager;
import org.bazara.saudigitus.bazaratranscliente.registo.StepperAdapter;

import butterknife.ButterKnife;


public class RegistoActivity extends AppCompatActivity implements StepperLayout.StepperListener, DataManager{

    private StepperLayout mStepperLayout ;
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final String DATA = "data";
    private Transportador data = null;
    private boolean otpSent;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress;
    private boolean verificationFailed;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String numeroTelemovel;
    private StepperLayout.OnNextClickedCallback stepperCallback;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int startingStepPosition = savedInstanceState != null? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY):0;
        data = savedInstanceState != null? data = savedInstanceState.getParcelable(DATA): null;

        setContentView(R.layout.activity_registo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this), startingStepPosition );
        mStepperLayout.setListener(this);

        int feedbackMask = StepperFeedbackType.TABS;
        feedbackMask |= StepperFeedbackType.CONTENT_PROGRESS;
        feedbackMask |= StepperFeedbackType.CONTENT_FADE;
        feedbackMask |= StepperFeedbackType.CONTENT_OVERLAY;
        feedbackMask |= StepperFeedbackType.DISABLED_BOTTOM_NAVIGATION;
        feedbackMask |= StepperFeedbackType.DISABLED_CONTENT_INTERACTION;
        mStepperLayout.setFeedbackType(feedbackMask);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("users");
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
//                Toast.makeText(RegistoActivity.this, "Verificação completa", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegistoActivity.this, "A verificação falhou", Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    verificationFailed = true;
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(RegistoActivity.this, "O código de verificação foi enviado para o número "+numeroTelemovel, Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(RegistoActivity.this,"Verificação efectuada",Toast.LENGTH_SHORT).show();
                              stepperCallback.goToNextStep();
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(RegistoActivity.this,"Verificação inválida",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.getCurrentStepPosition());
        outState.putParcelable(DATA, data);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        int currentStepPosition = mStepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            if (!mStepperLayout.isInProgress()) {
                 mStepperLayout.onBackClicked();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "Registo Completo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {
        //Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void setTransportador(Transportador t) {
        data = t;
    }

    @Override
    public Transportador getTransportador() {
        return data;
    }

    @Override
    public void isOTPsent(boolean sent) {
        this.otpSent = sent;
    }

    @Override
    public boolean getOTPSent() {
        return otpSent;
    }

    @Override
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks getCallbacks() {
        return mCallbacks;
    }

    @Override
    public void setNumero(String s) {
        numeroTelemovel = s;
    }

    @Override
    public void setStepperCallback(StepperLayout.OnNextClickedCallback callback) {
        this.stepperCallback = callback;
    }


    @Override
    public void verificarNumero(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    @Override
    public void saveInFireBase(String password) {
//        User user = new User(name, numeroTelemovel, password);
//
//        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(RegistoActivity.this, "Utilizador Registado com exito", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(RegistoActivity.this, MainActivity.class));
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RegistoActivity.this, "Fallha ao registar", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void setNome(String s) {
        name = s ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
