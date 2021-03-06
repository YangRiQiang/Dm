package com.yang.kotlin.ui.system

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yang.kotlin.R
import com.yang.kotlin.base.KotlinFragment
import com.yang.kotlin.model.bean.SystemModel
import com.yang.kotlin.ui.adpater.SystemAdapter
import com.yang.kotlin.utils.Constants
import com.yang.sdk.utils.rxUtils.RxBus
import kotlinx.android.synthetic.main.fragment_kt_system.*

/**
 * Describe: java文件说明
 * Created by Yang on 2019/6/21  17:55
 */
class KtSystemFragment : KotlinFragment<KtSystemViewModule>() {
    override fun providerVMClass(): Class<KtSystemViewModule>? = KtSystemViewModule::class.java
    private val mAdapter by lazy { SystemAdapter() }

    private lateinit var mFragment: Fragment
    override fun bindLayout(): Int {
        return R.layout.fragment_kt_system
    }

    override fun initView() {
        systemRcy.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mAdapter
        }
        systemSrl.run {
            isRefreshing = true
            setOnRefreshListener { mViewModel.getSystemTree() }
        }
        mViewModel.getSystemTree()
    }

    override fun startObserve() {
        mViewModel.run {
            mSystemModelList.observe(this@KtSystemFragment, Observer { it ->
                it?.let { setSystemTree(it) }
            })
        }
    }

    /**
     * 设置体系数据
     */
    private fun setSystemTree(it: List<SystemModel>) {
        systemSrl.isRefreshing = false
        mAdapter.setNewData(it)
        mAdapter.setOnItemClickListener { _, _, position ->
            RxBus.getInstanceBus().post(Constants.RX_SYSTEM_TYPE_ATTACH, mAdapter.data[position])
        }
    }
}

