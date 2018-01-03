package org.bazara.saudigitus.bazaratranscliente.registo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.bazara.saudigitus.bazaratranscliente.R;


public class NumeroFragment extends Fragment implements BlockingStep {

    private static final String TAG = "NumeroFragment";
    private CountryCodePicker numeroTelemovelView;
    private TextView tvValidity;
    private EditText tvNumeroTelemovel;
    private boolean numeroValido;
    private LinearLayout linearLayoutNumero;

//    private FirebaseAuth mAuth;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private boolean mVerificationInProgress;
//    private String mVerificationId;
//    private PhoneAuthProvider.ForceResendingToken mResendToken;
//    private boolean verificationFailed;
//    private boolean verificationCompleted;
    private DataManager dataManager;

    public NumeroFragment() {
        // Required empty public constructor
    }


    public static NumeroFragment newInstance() {
        NumeroFragment fragment = new NumeroFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_numero, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numeroTelemovelView = getView().findViewById(R.id.code_telemovel);
        tvNumeroTelemovel = getView().findViewById(R.id.numero_telemovel);
        linearLayoutNumero = getView().findViewById(R.id.linearLayoutNumero);

        numeroTelemovelView.registerCarrierNumberEditText(tvNumeroTelemovel);
        numeroTelemovelView.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (isValidNumber) {
                    numeroValido = true;
                } else {
                    numeroValido = false;
                }
            }
        });



    }




    @Nullable
    @Override
    public VerificationError verifyStep() {
        return numeroValido && tvNumeroTelemovel.getText().toString().length() > 0 ?
                null: new VerificationError("Número invalido");
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        numeroTelemovelView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
    }


    @UiThread
    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        callback.getStepperLayout().showProgress("A enviar PIN, Aguarde por favor...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataManager.setNumero(numeroTelemovelView.getSelectedCountryCode()+""+tvNumeroTelemovel.getText().toString());
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        numeroTelemovelView.getSelectedCountryCode()+""+tvNumeroTelemovel.getText().toString(),
                        60,
                        java.util.concurrent.TimeUnit.SECONDS,
                        getActivity(),
                        dataManager.getCallbacks());



                        dataManager.setStepperCallback(callback);
                        callback.goToNextStep();
                        callback.getStepperLayout().hideProgress();
            }
        }, 2000L);
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManager) {
            dataManager = (DataManager) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }
}
