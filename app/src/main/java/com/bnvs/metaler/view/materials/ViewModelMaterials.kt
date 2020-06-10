package com.bnvs.metaler.view.materials

import androidx.lifecycle.ViewModel
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.source.repository.PostsRepository

class ViewModelMaterials(
    private val postRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel()