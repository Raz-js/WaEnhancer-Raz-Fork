package com.wmods.wppenhacer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.wmods.wppenhacer.R
import android.widget.Button
import com.wmods.wppenhacer.auth.AuthHelper

class SignInActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(
                Scope("https://www.googleapis.com/auth/gmail.readonly"),
                Scope("https://www.googleapis.com/auth/gmail.send"),
                Scope("https://www.googleapis.com/auth/gmail.modify")
            )
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.btn_sign_in).setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account = task.result?.account
                if (account != null) {
                    // Obtain an OAuth access token on a background thread
                    Thread {
                        try {
                            val scopes = listOf(
                                "https://www.googleapis.com/auth/gmail.readonly",
                                "https://www.googleapis.com/auth/gmail.send",
                                "https://www.googleapis.com/auth/gmail.modify"
                            )
                            val token = AuthHelper.fetchAuthToken(this, account.name ?: "", scopes)
                            if (!token.isNullOrEmpty()) {
                                // store token temporarily
                                getSharedPreferences("auth", MODE_PRIVATE).edit().putString("gmail_token", token).apply()
                                runOnUiThread {
                                    startActivity(Intent(this, InboxActivity::class.java))
                                    finish()
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("SignInActivity", "Token fetch failed", e)
                        }
                    }.start()
                }
            }
        }
    }
}
