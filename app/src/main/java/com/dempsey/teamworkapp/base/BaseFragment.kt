package com.dempsey.teamworkapp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dempsey.teamworkapp.view.MainActivity

abstract class BaseFragment<P: BasePresenter> : Fragment() {

    abstract fun layoutId(): Int

    abstract fun setUpUi()

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    open fun showLoading() {
        activity ?: (activity as MainActivity).updateLoading(show = true)
    }

    open fun hideLoading() {
        activity ?: (activity as MainActivity).updateLoading(show = false)
    }

    open fun checkForNetworkThenCall(func: ()-> Unit) =
            (context as MainActivity).isConnectedOrConnecting { func }

    abstract fun instantiatePresenter(): P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }
}