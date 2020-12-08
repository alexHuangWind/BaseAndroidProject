package com.publica.baseproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment(){
    protected abstract val mViewModel: VM

    protected lateinit var mViewBinding: VB


    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getViewBinding()
    }
}