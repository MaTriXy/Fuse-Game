package com.fuseteam.games.belote.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.fuseteam.games.belote.R
import com.fuseteam.games.belote.data.types.Player
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

    val RC_SIGN_IN: Int = 78
    lateinit var loginWithGoogleButton: Button
    lateinit var joinToQue: Button
    var mGoogleApiClient: GoogleApiClient? = null
    var currentUserInQue = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginWithGoogleButton = view!!.findViewById<Button>(R.id.login_with_google)
        joinToQue = view.findViewById<Button>(R.id.join_to_que)
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            loginWithGoogleButton.visibility = View.GONE
            joinToQue.visibility = View.VISIBLE
            Toast.makeText(context, "hello" + currentUser.displayName, Toast.LENGTH_SHORT).show()
        } else {
            loginWithGoogleButton.setOnClickListener {
                signIn()
            }
        }

        joinToQue.setOnClickListener {
            if (!currentUserInQue) {
                val database = FirebaseDatabase.getInstance()
                var player = Player()
                var currentUser1 = FirebaseAuth.getInstance().currentUser
                player.id = currentUser1!!.uid
                player.email = currentUser1!!.email
                database.getReference(Player.TABLE + "/" + currentUser1.uid).setValue(player)
                currentUserInQue = true
                joinToQue.text = "leave que"
            } else {
                val database = FirebaseDatabase.getInstance()
                database.getReference(Player.TABLE + "/" + FirebaseAuth.getInstance().currentUser!!.uid).removeValue()
                currentUserInQue = false
                joinToQue.text = "Joint to Que"
            }
        }

    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity, { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = FirebaseAuth.getInstance().currentUser
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                        loginWithGoogleButton.visibility = View.GONE
                        joinToQue.visibility = View.VISIBLE
//                        activity.onBackPressed()
                    } else {
                        Toast.makeText(context, "falied", Toast.LENGTH_SHORT).show()
                    }

                    // ...
                })
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
