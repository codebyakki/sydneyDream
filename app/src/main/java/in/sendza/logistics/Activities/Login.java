package in.sendza.logistics.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import in.sendza.logistics.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore firebaseFirestore;
    private TextInputEditText email,password;
    private ACProgressFlower dialog;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAnalytics=FirebaseAnalytics.getInstance(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(this);
        dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .textSize(15)
                .text("Authenticating Please wait...")
                .fadeColor(Color.DKGRAY).build();


    }


    private boolean checkInput()
    {
        if (email.getText().toString().equals("")||password.getText().toString().equals(""))
        return false;
        else
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login:
                    if (checkInput())
                    {
                        initiateLogin();
                    }
                    else {
                        Toast.makeText(this,"Please enter email/password",Toast.LENGTH_LONG).show();
                    }
                break;
        }
    }

    private void initiateLogin() {
        dialog.show();
        Log.d(Login.class.getSimpleName(),"initiate login now");
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgresDialog();
                if (task.isSuccessful())
                {
                    Bundle b=new Bundle();
                    b.putString(FirebaseAnalytics.Event.LOGIN,task.getResult().getUser().getEmail());
                    firebaseAnalytics.logEvent("OnLoginComplete",b);
                    Log.wtf(Login.class.getSimpleName(),task.getResult().getUser().getUid());
                    startActivity(new Intent(Login.this,Dashboard.class));
                    finish();
                }
                else
                {
                    Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void hideProgresDialog()
    {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
