package com.dempsey.teamworkapp.view

import android.os.Bundle
import com.dempsey.teamwork.data.model.Project
import com.dempsey.teamworkapp.R
import com.dempsey.teamworkapp.base.BaseDelegate
import com.dempsey.teamworkapp.presenter.project.ProjectDetailPresenter
import com.dempsey.teamworkapp.base.BaseFragment
import com.dempsey.teamworkapp.presenter.project.ProjectDetailContract
import com.dempsey.teamworkapp.utils.MessageBanner
import com.dempsey.teamworkapp.utils.MessageType
import kotlinx.android.synthetic.main.fragment_detail.project_description
import kotlinx.android.synthetic.main.fragment_detail.project_id
import kotlinx.android.synthetic.main.fragment_detail.project_name
import kotlinx.android.synthetic.main.fragment_detail.title_view
import kotlinx.android.synthetic.main.fragment_detail.update_button

class ProjectDetailFragment :
        BaseFragment<ProjectDetailPresenter>(),
        ProjectDetailContract.View {

    override fun layoutId(): Int = R.layout.fragment_detail

    private lateinit var project: Project

    private var delegate: BaseDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        project = arguments!![PROJECT_BUNDLE] as Project
    }

    override fun setUpUi() {
        title_view.text = project.name
        project_name.setText(project.name)
        project_id.setText(project.id)
        project_description.setText(project.description)
        update_button.setOnClickListener { validateInputAndSend() }
    }

    private fun validateInputAndSend() {
        presenter.updateProjectDetails(project)
    }

    override fun instantiatePresenter() = ProjectDetailPresenter.newInstance(this)

    override fun showLoading() {
        delegate?.updateLoading(show = true)
    }

    override fun hideLoading() {
        delegate?.updateLoading(show = false)
    }

    override fun showSuccess() {
        MessageBanner(context as MainActivity).showBanner("Success", MessageType.SUCCESS)
    }

    override fun showError() {
        MessageBanner(context as MainActivity).showBanner("Error", MessageType.ERROR)
    }

    companion object {

        private const val PROJECT_BUNDLE = "projectExtra"

        fun newInstance(project: Project, delegate: BaseDelegate) = ProjectDetailFragment().apply {
            arguments = Bundle().apply { putSerializable(PROJECT_BUNDLE, project) }
            this.delegate = delegate
        }
    }
}