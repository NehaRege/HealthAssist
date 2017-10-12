package com.test.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceIdService;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int FACEBOOK_SIGNOUT_REQ = 1;
    private static final int GMAIL_SIGNOUT_REQ = 2;
    private static final int SIGNOUT_REQ = 232;
    private static final int GMAIL_RC_SIGN_IN = 9001;

    private static final String TAG = "MainActivity";

    private Button buttonLogin;
    //    private TextView textViewSignupLink;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private LoginButton fbLoginButton;
    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton gmailSignInButton;

    private String idTokenString = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        setUpGoogleApiClient();

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    Log.i(TAG, "onAuthStateChanged: signed_in");

//                    String name = user.getDisplayName();
//                    String email = user.getEmail();
//                    Uri photoUrl = user.getPhotoUrl();
//
//                    // Check if user's email is verified
//                    boolean emailVerified = user.isEmailVerified();
//
//                    Toast.makeText(MainActivity.this, "Logged in via Facebook as: " + user.getEmail(), Toast.LENGTH_SHORT).show();
//
////                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                    intent.putExtra("user_email", email);
//                    intent.putExtra("user_name", name);
//                    intent.putExtra("user_photo", photoUrl);
//                    startActivityForResult(intent, FACEBOOK_SIGNOUT_REQ);

                } else {
                    Log.i(TAG, "onAuthStateChanged: signed_out");
                }
            }
        };

        fbLoginButton.setReadPermissions("email", "public_profile");

        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    String name;
                    String email;
                    Uri photoUrl;

                    if (user.getDisplayName() != null) {
                        name = user.getDisplayName();
                    } else {
                        name = "Display name not available";
                    }

                    if (user.getDisplayName() != null) {
                        email = user.getEmail();
                    } else {
                        email = "Email not available";
                    }


                    // Check if user's email is verified
                    boolean emailVerified = user.isEmailVerified();

//                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("user_email", email);
                    intent.putExtra("user_name", name);
//                    intent.putExtra("user_photo", photoUrl);
                    startActivityForResult(intent, FACEBOOK_SIGNOUT_REQ);

                } else {
                    Log.i(TAG, "onAuthStateChanged: logged_out");
                }
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                String username = usernameWrapper.getEditText().getText().toString();
                String password = usernameWrapper.getEditText().getText().toString();

                if (!validateEmail(username)) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });

//        textViewSignupLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
//                startActivity(intent);
//            }
//        });

        gmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmailSignIn();
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
    }

    private void initializeViews() {
        buttonLogin = (Button) findViewById(R.id.activity_main_login_button);
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
//        textViewSignupLink = (TextView) findViewById(R.id.link_signup);

        fbLoginButton = (LoginButton) findViewById(R.id.button_facebook_login);

        gmailSignInButton = (SignInButton) findViewById(R.id.sign_in_button_gmail);
        gmailSignInButton.setSize(SignInButton.SIZE_STANDARD);
        gmailSignInButton.setColorScheme(SignInButton.COLOR_AUTO);
//        gmailSignInButton.setColorScheme(SignInButton.COLOR_DARK);
//        gmailSignInButton.setColorScheme(SignInButton.COLOR_LIGHT);

    }

    private void setUpGoogleApiClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestServerAuthCode(getString(R.string.default_web_client_id),false)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    public void doLogin() {
//        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        setResult(SIGNOUT_REQ);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FACEBOOK_SIGNOUT_REQ) {
            if (resultCode == RESULT_OK) {
                signOutFacebook();
            }
        }

        if (requestCode == GMAIL_SIGNOUT_REQ) {
            if (resultCode == RESULT_OK) {
//                signOutGmail();
                logoutGmail();
            }
        }

        if (requestCode == GMAIL_RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            GoogleSignInAccount account = result.getSignInAccount();
//
//            firebaseAuthWithGoogle(account);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGmailSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: Gmail" + connectionResult);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: user = " + user.getEmail());
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
        } else {
            findViewById(R.id.button_facebook_login).setVisibility(View.VISIBLE);
        }
    }

    public void signOutFacebook() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }

    private void gmailSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GMAIL_RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "Google User Id :" + acct.getId());

        // --------------------------------- //
        // BELOW LINE GIVES YOU JSON WEB TOKEN, (USED TO GET ACCESS TOKEN) :
        Log.d(TAG, "Google JWT : " + acct.getIdToken());


        // --------------------------------- //

        // Save this JWT in global String :
        idTokenString = acct.getIdToken();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful()){
                            // --------------------------------- //
                            // BELOW LINE GIVES YOU FIREBASE TOKEN ID :
                            Log.d(TAG, "Firebase User Access Token : " + task.getResult().getUser().getToken(true));
                            // --------------------------------- //
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        else {
                            Log.w(TAG, "Authentication failed: ", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void handleGmailSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();


            if (acct != null) {
                Log.d(TAG, "handleGmailSignInResult: Google User Id: " + acct.getId());

                // BELOW LINE GIVES YOU JSON WEB TOKEN, (USED TO GET ACCESS TOKEN) :



                Toast.makeText(MainActivity.this, "Server Auth Token = "+ acct.getServerAuthCode(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Server Auth Token = "+ acct.getIdToken(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "handleGmailSignInResult: ********************************");
                Log.d(TAG, "handleGmailSignInResult: Server auth token = "+acct.getServerAuthCode());
                Log.d(TAG, "handleGmailSignInResult: Google Id token =  " + acct.getIdToken());

                Log.d(TAG, "handleGmailSignInResult: Photo Url = "+acct.getPhotoUrl());

                // Save this JWT in global String :
                idTokenString = acct.getIdToken();

                Toast.makeText(MainActivity.this, "Access token: "+idTokenString, Toast.LENGTH_SHORT).show();

                String name;
                String email;
                Uri photoUrl;

                if (acct.getDisplayName() != null) {
                    name = acct.getDisplayName();
                } else {
                    name = "Display Name not available";
                }

                if (acct.getDisplayName() != null) {
                    email = acct.getEmail();
                } else {
                    email = "Email not available";
                }


                Toast.makeText(MainActivity.this, "Logged in via Gmail as: " + acct.getEmail(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("user_email_gmail", email);
                intent.putExtra("user_name_gmail", name);
//                intent.putExtra("user_photo_gmail", photoUrl);
                startActivityForResult(intent, GMAIL_SIGNOUT_REQ);

            }

            updateUIGmail(true);

        } else {

            updateUIGmail(false);

            Log.i(TAG, "handleGoogleSignInResult: gmail login failed !");


        }
    }

    private void updateUIGmail(boolean signedIn) {
        if (signedIn) {
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private void signOutGmail() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        Log.d(TAG, "onResult: Gmail Signout Successful ");
                        Toast.makeText(MainActivity.this, "Gmail Signout", Toast.LENGTH_SHORT).show();
//                        updateUIGmail(false);
                    }
                });
    }

    public void logoutGmail() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "User Gmail Logged out");
                                Toast.makeText(MainActivity.this, "Gmail Signout", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Google API Client Connection Suspended");
            }
        });
    }
}
