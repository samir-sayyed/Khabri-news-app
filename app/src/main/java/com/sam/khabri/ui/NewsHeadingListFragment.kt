package com.sam.khabri.ui

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.sam.khabari.R
import com.sam.khabari.databinding.FragmentNewsHeadingListBinding
import com.sam.khabri.base.BaseFragment
import com.sam.khabri.data.model.response.Article
import com.sam.khabri.ui.adapter.HeadingsAdapter
import com.sam.khabri.utils.dismissLoadingDialog
import com.sam.khabri.utils.showLoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsHeadingListFragment : BaseFragment<FragmentNewsHeadingListBinding, HomeViewModel>() {

    private var mHeadingsAdapter: HeadingsAdapter? = null
    private var mHeadingList: ArrayList<Article> = arrayListOf()

    override fun initApiCalls() {
        super.initApiCalls()
        showLoadingDialog()
        callApi()
        mHeadingsAdapter = HeadingsAdapter()
        viewBinding?.headingRecyclerView?.adapter = mHeadingsAdapter
        setListener()
    }


    override fun observeData() {
        super.observeData()
        viewModel.headings.observe(viewLifecycleOwner) {
            if (it != null) {
                mHeadingList = it as ArrayList<Article>
                mHeadingsAdapter?.updateList(mHeadingList)
            }
            dismissLoadingDialog()
            viewBinding?.swipeView?.isRefreshing = false
        }
    }


    private fun setListener() {
        viewBinding?.swipeView?.setOnRefreshListener {
            callApi()
        }

        mHeadingsAdapter?.listener = { article ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToArticleDetailsFragment(
                    article
                )
            )
        }

        viewBinding?.searchEditText?.doAfterTextChanged { text ->
            if (text?.isNotEmpty() == true) {
                mHeadingsAdapter?.updateList(mHeadingList.filter { heading ->
                    heading.title?.contains(
                        text,
                        true
                    ) ?: false
                } as ArrayList<Article>)

            } else
                mHeadingsAdapter?.updateList(mHeadingList)

        }
    }

    private fun callApi() {
        val category = arguments?.getString(CATEGORY)
        if (category != null)
            viewModel.getHeadings(category, requireContext())
    }


    companion object {

        private const val TAG = "NewsHeadingListFragment"
        private const val CATEGORY = "category"

        @JvmStatic
        fun newInstance(category: String) = NewsHeadingListFragment().apply {
            arguments = Bundle().apply {
                putString(CATEGORY, category)
            }
        }
    }

    override fun viewModelClass() = HomeViewModel::class.java
    override val layoutId: Int = R.layout.fragment_news_heading_list

}