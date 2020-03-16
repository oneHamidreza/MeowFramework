/*
 * Copyright (C) 2020 Hamidreza Etebarian & Ali Modares.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.ui.user.index

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import meow.utils.*
import meow.widget.decoration.MeowDividerDecoration
import sample.R
import sample.data.User
import sample.databinding.ActivityUserIndexBinding
import sample.ui.base.BaseActivity

/**
 * [User]/Index Activity class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-02-29
 */

class UserIndexActivity : BaseActivity<ActivityUserIndexBinding, UserIndexViewModel>() {

    private lateinit var listAdapter: UserAdapter

    override fun layoutId() = R.layout.activity_user_index
    override fun viewModelClass() = UserIndexViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        observeViewModel()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration { position, outRect ->
                if (position == 0)
                    outRect.top = 24.dp()
            }
            addItemDecoration(MeowDividerDecoration(context))
            listAdapter = UserAdapter(application, viewModel)
            adapter = listAdapter
        }

        viewModel.callApi()
    }

    private fun observeViewModel() {
        binding.viewModel = viewModel
        viewModel.eventLiveData.safeObserve(this) {
            logD(m = "New Event Received : $it")
            when {
                it.isApiCancellation() -> {
                    hideLoading()
                    showError("Canceling is Working")
                }
                it.isApiError() -> {
                    hideLoading()
//                    showError(it.createErrorMessage(resources))
                }
                it.isApiLoading() -> {
                    showLoading()
                }
                it.isApiSuccess() -> {
                    hideLoading()
                }
            }
        }
    }

    private fun showLoading() {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
    }

    private fun hideLoading() {
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}