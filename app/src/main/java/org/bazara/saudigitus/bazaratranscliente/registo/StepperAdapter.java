package org.bazara.saudigitus.bazaratranscliente.registo;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by dalves on 12/3/17.
 */

public class StepperAdapter extends AbstractFragmentStepAdapter {


    private static final String CURRENT_STEP_POSITION_KEY = "position";

    public StepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        Step step = null;

        switch (position){
            case 0: step = new DadosFragment().newInstance(); break;
            case 1: step = new NumeroFragment().newInstance(); break;
            case 2: step = new OtpFragment().newInstance(); break;
            case 3: step = new PasswordFragment().newInstance(); break;
        }
        return step;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        StepViewModel stepModel = null;
        switch (position){
            case 0: stepModel = new StepViewModel.Builder(context)
                    .setTitle("Nome Completo")
                    .setSubtitle("Escreva o seu nome")
                    .setEndButtonLabel("Próximo")
                    .setBackButtonLabel("Cancelar")
                    .create(); break;
            case 1: stepModel = new StepViewModel.Builder(context)
                    .setTitle("Telefone")
                    .setSubtitle("Digite o seu número")
                    .setEndButtonLabel("Próximo")
                    .setBackButtonLabel("Anterior")
                    .create(); break;
            case 2: stepModel = new StepViewModel.Builder(context)
                    .setTitle("Confirmação")
                    .setSubtitle("Confirme o seu número")
                    .setEndButtonLabel("Próximo")
                    .setBackButtonLabel("Anterior")
                    .create(); break;
            case 3: stepModel = new StepViewModel.Builder(context)
                    .setTitle("Palavra-passe")
                    .setSubtitle("Defina a Palavra-passe")
                    .setEndButtonLabel("Terminar")
                    .setBackButtonLabel("Anterior")
                    .create(); break;
        }
        return stepModel;
    }


}
