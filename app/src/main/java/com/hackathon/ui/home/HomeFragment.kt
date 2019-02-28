package com.hackathon.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackathon.databinding.HomeFragmentBinding
import com.hackathon.di.ILogger
import com.hackathon.ui.base.BaseFragment
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class) {
    private val logger: ILogger by inject()
    private lateinit var dataBinding: HomeFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Base Fragment on create view, calls view model on screen created
        super.onCreateView(inflater, container, savedInstanceState)

        // Bind View Model to the layout
        dataBinding = HomeFragmentBinding.inflate(inflater, container, false)
        dataBinding.setLifecycleOwner(this)

        // Bind text fields
//        dataBinding.usernameEditText.bindWriteOnly(viewModel.username)
//        dataBinding.passwordEditText.bindWriteOnly(viewModel.password)

        // Bind on click
//        dataBinding.loginButton.setOnClickListener {
//            viewModel.onLoginClick()
//        }
//        dataBinding.signupButton.setOnClickListener {
//            this.navigate(HomeFragmentDirections.actionLoginFragmentToTosFragment())
//        }

        return dataBinding.root
    }

    /**
     * Bind view model commands
     */
    override fun bindCommands() {
        super.bindCommands()

        // Example for loading
        viewModel.onLoginFinished.runWhenFinished(this,
                onSuccess = {
                    // Do something
                },
                onError = {
                    // Do something
                })
    }
}
