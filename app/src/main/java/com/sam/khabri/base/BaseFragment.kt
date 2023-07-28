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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {

    private var _viewBinding: ViewBinding? = null
    protected val viewBinding get() = _viewBinding

    protected lateinit var viewModel: ViewModel

    protected abstract fun viewModelClass(): Class<ViewModel>

    open var useSharedViewModel: Boolean = false

    private val analytics = Firebase.analytics
    private val crashlytics = Firebase.crashlytics

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
        crashlytics.setCustomKeys {
            key("screen_view", this::class.java.name)
        }

        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW){
            param(FirebaseAnalytics.Param.SCREEN_NAME, this::class.java.name)
        }
        initApiCalls()
        setUpViews()
        observeData()
    }

    open fun setUpViews() = Unit
    open fun observeData() = Unit
    open fun initApiCalls() = Unit

    private fun addCrashlyticsLog(key: String, value: String) {
        crashlytics.setCustomKeys {
            key(key, value)
        }
    }

    private fun addAnalyticsLog(event: String, params: List<Param>? = null) {
        analytics.logEvent(event){
        }

        analytics.logEvent(event) {
            params?.forEach {
                param(it.key, it.value)
            }
        }
    }

    fun addCrashlyticsAnalyticsLogs(key: String, value: String) {
        addCrashlyticsLog("action", "$key: $value")
        addAnalyticsLog(
            key, listOf(
                Param(
                    FirebaseAnalytics.Param.SCREEN_NAME,
                    this::class.java.name
                ),
                Param("view", value)
            )
        )
    }

    data class Param(
        val key: String,
        val value: String
    )


    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}