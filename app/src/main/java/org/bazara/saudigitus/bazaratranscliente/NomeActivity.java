package org.bazara.saudigitus.bazaratranscliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.bazara.saudigitus.bazaratranscliente.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NomeActivity extends AppCompatActivity {




    @BindView(R.id.tv_nome_completo)
    EditText tvNome;
    @BindView(R.id.verifyLayout)
    public LinearLayout verifyLayout;
    @BindView(R.id.loadingProgress)
    public LinearLayout loadingProgress;

    private String phone;
    private FirebaseDatabase db;
    private DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome);
        ButterKnife.bind(this);
        phone = getIntent().getExtras().getString("NUMERO");
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
    }


    @OnClick(R.id.btn_finalizar)
    public void finalizeRegister(View view){
        verifyLayout.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);

        User user = new User(tvNome.getText().toString(), phone);
        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NomeActivity.this, "Utilizador Registado com exito", Toast.LENGTH_LONG).show();
                startActivity(new Intent(NomeActivity.this, MapsActivity2.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NomeActivity.this, "Fallha ao registar", Toast.LENGTH_LONG).show();
                verifyLayout.setVisibility(View.VISIBLE);
                loadingProgress.setVisibility(View.GONE);
            }
        });
    }

}
