package com.adammcneilly.cashcaretaker.transaction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.adammcneilly.cashcaretaker.DividerItemDecoration
import com.adammcneilly.cashcaretaker.R
import com.adammcneilly.cashcaretaker.entity.EntityPresenter
import com.androidessence.utility.hide
import com.androidessence.utility.show

/**
 * Fragment that displays a list of Transactions.
 */
class TransactionFragment: Fragment(), TransactionController {
    private val adapter = TransactionAdapter()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: EntityPresenter<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accountName = arguments?.getString(ARG_ACCOUNT).orEmpty()
        presenter = TransactionPresenterImpl(this, TransactionInteractorImpl(), accountName)

        val title = if (accountName.isEmpty()) getString(R.string.app_name) else getString(R.string.account_transactions, accountName)
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        progressBar = view.findViewById<ProgressBar>(R.id.progress) as ProgressBar
        recyclerView = view.findViewById<RecyclerView>(R.id.transactions) as RecyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        //TODO: If context were null?
        recyclerView.addItemDecoration(DividerItemDecoration(context!!))

        return view
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun setTransactions(transactions: List<Transaction>) {
        adapter.items = transactions
    }

    companion object {
        val FRAGMENT_NAME: String = TransactionFragment::class.java.simpleName
        private val ARG_ACCOUNT = "accountName"

        fun newInstance(accountName: String): TransactionFragment {
            val args = Bundle()
            args.putString(ARG_ACCOUNT, accountName)

            val fragment = TransactionFragment()
            fragment.arguments = args

            return fragment
        }
    }
}