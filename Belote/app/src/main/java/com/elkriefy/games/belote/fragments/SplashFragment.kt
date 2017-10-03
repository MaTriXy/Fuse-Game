package com.elkriefy.games.belote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elkriefy.games.belote.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.timer


class SplashFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var currentUserInQue = false
    private lateinit var loadingView: View


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
        loadingView = inflater.inflate(R.layout.loading_view, container, false) as View
        return loadingView

    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer(null, false, 5000, 1000, {
            var currentUser = FirebaseAuth.getInstance().currentUser
            when (currentUser) {
                null -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment, MainActivityFragment())?.commit()
                }
                else -> {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment, GameFragment())?.commit()
                }
            }
            onDestroy()
        })

    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
        } catch (e: Throwable) {
        }
    }
}
