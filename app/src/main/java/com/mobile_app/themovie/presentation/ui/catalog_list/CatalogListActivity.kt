package com.mobile_app.themovie.presentation.ui.catalog_list

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.databinding.ActivityMainBinding
import com.mobile_app.themovie.domain.entity.RemoveCatalogRequest
import com.mobile_app.themovie.presentation.di.AppModule
import com.mobile_app.themovie.presentation.di.DaggerAppComponent
import com.mobile_app.themovie.presentation.ui.view_model.CatalogViewModel
import com.mobile_app.themovie.presentation.util.showMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mobile_app.themovie.presentation.navigation.AppRouter
import com.mobile_app.themovie.presentation.util.showEmptyState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CatalogListActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()
    private val catalogClickListener = PublishSubject.create<Int>()
    private val removeMovieListener =
        PublishSubject.create<Catalog>()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var catalogModel: CatalogViewModel

    @Inject
    lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DaggerAppComponent.builder()
            .appModule(AppModule(application, this))
            .build()
            .inject(this)

        // Popular
        setUpPopularBtn()

        // Other Catalogs
        val adapter = CatalogListAdapter(
            { catalogClickListener.onNext(it) },
            { removeMovieListener.onNext(it) }
        )
        binding.rvCatalog.adapter = adapter
        binding.rvCatalog.layoutManager = LinearLayoutManager(this)

        // Set up tab bar
        binding.tabSection.setText(resources.getString(R.string.catalogs))
        binding.tabSection.setIcon(resources.getDrawable(R.drawable.ic_folder_special_white_34dp, null))


        disposable.addAll(
            catalogClickListener.subscribe { appRouter.navigateToCatalog(it) },
            removeMovieListener.subscribe { removeCatalog(it) }
        )

        catalogModel.getAll().observe(this,
            Observer {
                binding.emptyState.showEmptyState(it.isEmpty())
                adapter.setCatalogs(it)
            }
        )

        binding.fab.setOnClickListener { showDialog() }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun setUpPopularBtn() {
        binding.btMainPopular.tvCatalogName.setText(R.string.bt_main_popular)
        binding.btMainPopular.cvCatalog.setOnClickListener {
            if (!isNetworkConnected()) {
                showMessage(this, R.string.no_network_connection)
            } else {
                appRouter.navigateToPopular()
            }
        }
    }

    private fun showDialog() {

        val promptsView = LayoutInflater.from(this).inflate(R.layout.add_catalog_view, null)

        //create dialog
        val dialog =
            MaterialAlertDialogBuilder(
                this,
                R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog
            )
                .setView(promptsView)
                .setCancelable(true)
                .setTitle(getString(R.string.type_catalog_name))
                .setPositiveButton(getString(R.string.ok), null)
                .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .create()

        dialog.setOnShowListener {
            val button = dialog.getButton(BUTTON_POSITIVE)
            button.setOnClickListener {
                val userInput = promptsView.findViewById<TextInputEditText>(R.id.editText)
                val userInputLayout = promptsView.findViewById<TextInputLayout>(R.id.editTextLayout)
                if (userInput.text.toString().trim().isEmpty()) {
                    userInputLayout.error = getString(R.string.error_message)
                } else {
                    saveCatalog(userInput.text.toString())
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun saveCatalog(userInput: String) {
        val catalog = Catalog(title = userInput.trim())
        disposable.add(
            catalogModel.save(catalog)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showMessage(
                        this,
                        getString(R.string.catalog) + " " + userInput + " " + getString(R.string.is_saved)
                    )
                }
        )
    }

    private fun removeCatalog(catalog: Catalog) {
        catalog.id?.let {
            disposable.add(
                catalogModel.removeCatalog(RemoveCatalogRequest(it, this))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
        showMessage(this, catalog.title + " " + getString(R.string.is_removed))
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}
