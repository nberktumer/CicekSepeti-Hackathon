package com.hackathon.ui.base

import com.hackathon.utils.hideKeyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavHost
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModelByClass
import kotlin.reflect.KClass

/**
 * Base Fragment type for all fragments inside the application (and possibly navigation)
 * @param VM View Model type for the fragment
 * @constructor Creates the base fragment based on Fragment
 * @property viewModel View Model instance for this fragment
 * @property navController [NavController] for this fragment,
 * may throw [IllegalStateException] if the fragment is not inside a [NavHost],
 * and may return null if view is not yet created
 */
abstract class BaseFragment<out VM : BaseViewModel>(
        viewModelClazz: KClass<VM>
) : Fragment() {
    protected var initialized: Boolean = false
        private set
    protected val viewModel: VM by viewModelByClass(viewModelClazz)

    protected val navController by lazy {
        findNavController()
    }

    protected fun navigate(directions: NavDirections) {
        this.navController.navigate(directions)
    }

    protected fun navigate(actionId: Int, bundle: Bundle) {
        this.navController.navigate(actionId, bundle)
    }

    /**
     * Call when creating view on super class, however this function returns null
     *
     * Overriding class should create their own implementation and return that
     */
    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onScreenCreated()
        this.bindCommands()

        this.initialized = true

        return null
    }

    @CallSuper
    protected open fun bindCommands() {

    }

    fun goBack() {
        (activity as AppCompatActivity).onSupportNavigateUp()
    }

    fun hideKeyboard() {
        (activity as AppCompatActivity).hideKeyboard()
    }

    fun showStatusBar() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    fun hideStatusBar() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN  //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}