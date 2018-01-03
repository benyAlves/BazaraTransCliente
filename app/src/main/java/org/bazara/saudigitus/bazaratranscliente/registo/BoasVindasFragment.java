package org.bazara.saudigitus.bazaratranscliente.registo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.bazara.saudigitus.bazaratranscliente.R;


public class BoasVindasFragment extends Fragment implements Step {


    private RadioButton rvCliente;
    private RadioButton rvTransporte;
    private RadioGroup groupViewTipo;
    private Animation anim;

    public  BoasVindasFragment() {
        // Required empty public constructor
    }

    public static BoasVindasFragment newInstance() {
        BoasVindasFragment fragment = new BoasVindasFragment();
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
        return inflater.inflate(R.layout.fragment_boas_vindas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCliente = getView().findViewById(R.id.radio_cliente);
        rvTransporte =  getView().findViewById(R.id.radio_transporter);
        groupViewTipo = getView().findViewById(R.id.tipo_user);

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
