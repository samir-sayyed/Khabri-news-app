package com.sam.khabri.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    private var _viewBinding: ViewBinding? = null
    protected val viewBinding get() = _viewBinding

    protected lateinit var viewModel: ViewModel

    protected abstract fun viewModelClass(): Class<ViewModel>

    open var useSharedViewModel: Boolean = false

    @get:LayoutRes
    protected abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = if (useSharedViewModel)
                ViewModelProvider(requireActivity())[viewModelClass()]
        else
            ViewModelProvider(this)[viewModelClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return _viewBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initApiCalls()
        setUpViews()
        observeData()
    }

    open fun setUpViews() = Unit
    open fun observeData() = Unit
    open fun initApiCalls() = Unit


    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}