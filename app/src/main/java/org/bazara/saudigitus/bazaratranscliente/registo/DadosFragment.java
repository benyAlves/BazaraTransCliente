package org.bazara.saudigitus.bazaratranscliente.registo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.bazara.saudigitus.bazaratranscliente.R;
import org.bazara.saudigitus.bazaratranscliente.model.Transportador;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DadosFragment extends Fragment implements BlockingStep {

    @BindView(R.id.tv_nome_completo)
    EditText tvNomeCompleto;


    private Unbinder unbinder;
    DataManager dataManager;



    public DadosFragment() {
        // Required empty public constructor
    }

    public static DadosFragment newInstance() {
        DadosFragment fragment = new DadosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dados, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
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

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return tvNomeCompleto.getText().toString().length() > 0 ?
                null: new VerificationError("Digite o nome completo");
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        dataManager.setNome(tvNomeCompleto.getText().toString());
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
