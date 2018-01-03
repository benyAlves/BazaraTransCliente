package org.bazara.saudigitus.bazaratranscliente.registo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.bazara.saudigitus.bazaratranscliente.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.philio.pinentry.PinEntryView;


public class OtpFragment extends Fragment implements BlockingStep {

    @BindView(R.id.codigo_verificar)
    PinEntryView tvPinEntry;

    @BindView(R.id.btn_reenviar)
    Button btnReenviar;

    @BindView(R.id.view_segundos)
    TextView tvSegundos;

    private Unbinder unbinder;
    private DataManager dataManager;
    private CountDownTimer count;

    public OtpFragment() {
        // Required empty public constructor
    }

    public static OtpFragment newInstance() {
        OtpFragment fragment = new OtpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @OnClick(R.id.btn_verificar)
    public void onVerificarNumero(View view) {
        dataManager.verificarNumero(tvPinEntry.getText().toString());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp, container, false);


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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return  tvPinEntry.getText().toString().length() > 0 ?
                null: new VerificationError("Informe o código de confirmação");
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            btnReenviar.setEnabled(false);
             count = new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    tvSegundos.setText("(" + millisUntilFinished / 1000 + "s)");
                }

                public void onFinish() {
                    tvSegundos.setText("");
                    btnReenviar.setEnabled(true);
                }
            };
            count.start();
        }else {
            count = null;
        }
    }

    @UiThread
    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count.cancel();
                count = null;
                callback.goToNextStep();
            }
        }, 32000L);
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        count.cancel();
        count = null;
        callback.goToPrevStep();
    }
}
