package org.bazara.saudigitus.bazaratranscliente.registo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.bazara.saudigitus.bazaratranscliente.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment implements BlockingStep {

    private static final String TAG = "PasswordFragment";
    private DataManager dataManager;
    private Unbinder unbinder;
    @BindView(R.id.tv_palavra_passe)
    EditText editTextPalavraPasse;

    public PasswordFragment() {
        // Required empty public constructor
    }

    public static PasswordFragment newInstance() {
        PasswordFragment fragment = new PasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if(editTextPalavraPasse.getText().toString().length()<=0)
            return new VerificationError("Informe a palavra-passe");

        return (editTextPalavraPasse.getText().toString().length() > 1) && (editTextPalavraPasse.getText().toString().length() < 6) ?
                new VerificationError("Deve conter 6 dígitos no mínimo"): null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Log.w(TAG, "onNextClicked: ");
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        Log.w(TAG, "onCompleteClicked: " );
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.getStepperLayout().setCurrentStepPosition(2);
        callback.goToPrevStep();
    }
}
