package com.yang.kotlin.model.bean

/**
 * Describe: java文件说明
 * Created by Yang on 2019/7/3  15:58
 */
data class ArticleListModel(
        val curPage: Int,
        val datas: List<Article>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)
