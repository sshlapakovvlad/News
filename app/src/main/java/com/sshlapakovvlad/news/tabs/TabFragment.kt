package com.sshlapakovvlad.news.tabs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sshlapakovvlad.news.ArticlesAdapter
import com.sshlapakovvlad.news.R
import com.sshlapakovvlad.news.models.Article
import com.sshlapakovvlad.news.repositories.ArticlesRepository
import com.sshlapakovvlad.news.repositories.SharedPrefsUserDataRepository
import kotlinx.android.synthetic.main.tab_fragment.*

class TabFragment : Fragment() {

    companion object {
        private const val TAB_CATEGORY = "tab_category"

        fun newInstance(category: String) = TabFragment().apply {
            arguments = bundleOf(
                TAB_CATEGORY to category
            )
        }
    }

    private val tabCategory: String by lazy { arguments?.getString(TAB_CATEGORY) ?: "" }

    private val readTime: Int by lazy {
        SharedPrefsUserDataRepository(
            requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE),
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        ).readUserReadTime()
    }

    private val articleClickListener: (Article) -> Unit = {
        CustomTabsIntent
            .Builder()
            .setShowTitle(true)
            .setToolbarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
            .build()
            .launchUrl(requireContext(), Uri.parse(it.url))
    }

    private val articleShareClickListener: (Article) -> Unit = {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, it.title + "\n\n" + it.url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
    }

    private val articlesAdapter by lazy {
        ArticlesAdapter(
            requireContext(),
            emptyList(),
            articleClickListener,
            articleShareClickListener
        )
    }

    private val viewModel: TabViewModel by viewModels {
        TabViewModelFactory(readTime, tabCategory, ArticlesRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            articlesAdapter.setData(it!!)
        })
    }
}
