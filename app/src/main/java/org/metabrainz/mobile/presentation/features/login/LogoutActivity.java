package org.metabrainz.mobile.presentation.features.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.metabrainz.mobile.R;
import org.metabrainz.mobile.databinding.ActivityLoginBinding;
import org.metabrainz.mobile.presentation.features.KotlinDashboard.KotlinDashboardActivity;
import org.metabrainz.mobile.presentation.features.settings.SettingsActivity;

import java.util.Objects;

public class LogoutActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());

        binding.loginPromptId.setText(R.string.logout_prompt);
        binding.loginBtn.setText(R.string.logout);
        if(LoginSharedPreferences.getLoginStatus() == LoginSharedPreferences.STATUS_LOGGED_OUT)
            startActivity(new Intent(this,LoginActivity.class));
        else
            binding.loginBtn.setOnClickListener( v -> logoutUser());

        super.onCreate(savedInstanceState);
    }

    private void logoutUser() {
        LoginSharedPreferences.logoutUser();
        Toast.makeText(getApplicationContext(),
                "User has successfully logged out.",
                Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, KotlinDashboardActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_preferences:  {startActivity(new Intent(this, SettingsActivity.class));
                                        return true; }
            case android.R.id.home : {
                this.finish();
                return true;
            }

            default:   return super.onOptionsItemSelected(item);

        }
    }

}