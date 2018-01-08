package org.bazara.saudigitus.bazaratranscliente.registo;

import com.google.firebase.auth.PhoneAuthProvider;
import com.stepstone.stepper.StepperLayout;

import org.bazara.saudigitus.bazaratranscliente.model.Transportador;

/**
 * Created by dalves on 12/13/17.
 */

public interface DataManager {
    void setTransportador(Transportador t);
    Transportador getTransportador();
    void isOTPsent(boolean sent);
    boolean getOTPSent();

    PhoneAuthProvider.OnVerificationStateChangedCallbacks getCallbacks();

    void setNumero(String s);

    void setStepperCallback(StepperLayout.OnNextClickedCallback callback);

    void verificarNumero(String otp);

    void saveInFireBase(String password);

    void setNome(String s);
}
