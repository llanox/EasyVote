package com.llanox.mobile.easyvote.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.llanox.mobile.easyvote.R;
import com.llanox.mobile.easyvote.data.StoreSession;
import com.llanox.mobile.easyvote.data.UserStore;
import com.llanox.mobile.easyvote.model.UserModel;

import ua.org.zasadnyy.zvalidations.Field;
import ua.org.zasadnyy.zvalidations.Form;
import ua.org.zasadnyy.zvalidations.validations.ConfirmField;
import ua.org.zasadnyy.zvalidations.validations.IsEmail;
import ua.org.zasadnyy.zvalidations.validations.NotEmpty;

public class RegisterActivity extends Activity {
    private UserStore userStore;

    @Override
    protected void onStart() {
        super.onStart();
        userStore = new UserStore(this);
        userStore.open();

    }

    @Override
    protected void onStop() {
        super.onStop();
        userStore.close();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


   public void register(View view){

       EditText email = (EditText)this.findViewById(R.id.et_register_email);
       EditText confirmEmail =(EditText)this.findViewById(R.id.et_register_confirm_email);

       EditText password =(EditText)this.findViewById(R.id.et_register_password);
       EditText confirmPassword =(EditText)this.findViewById(R.id.et_register_confirm_password);

       Form mForm = new Form(this);

       mForm.addField(Field.using(email).validate(NotEmpty.build(this)).validate(IsEmail.build(this)));
       mForm.addField(Field.using(confirmEmail).validate(NotEmpty.build(this)).validate(IsEmail.build(this)).validate(ConfirmField.build(this,email)));

       mForm.addField(Field.using(password).validate(NotEmpty.build(this)));
       mForm.addField(Field.using(confirmPassword).validate(NotEmpty.build(this)).validate(ConfirmField.build(this,password)));



       if (mForm.isValid()) {

           StoreSession<UserModel> store = new UserStore(this);

           UserModel userModel = new UserModel();
           userModel.setEmail(email.getText().toString());
           userModel.setId(email.getText().toString());
           userModel.setRole(UserModel.REGISTERED_USER_ROLE);
           userModel.setPassword(password.getText().toString());

           store.asyncInsert(userModel, new StoreSession.ResponseHandler<UserModel>() {
               @Override
               public void succesfull(UserModel item) {

                   Intent data = new Intent();
                   data.putExtra(EntryPointActivity.JUST_REGISTERED_USER_ID,item.getEmail());

                   RegisterActivity.this.setResult(RESULT_OK,data);
                   RegisterActivity.this.finish();

               }

               @Override
               public void error(Object error) {

               }
           });


       }





   }
}
