package com.example.expensemanager

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var deletedTransaction: transactionDataHold
    private  lateinit var transactions : List<transactionDataHold>
    private lateinit var oldTransactions : List<transactionDataHold>

    private  lateinit var transactionAdapter: transactionAdapter
    private lateinit var  linearLayoutManager: LinearLayoutManager
        private  lateinit var  db: DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen+



        transactions = arrayListOf(


        )
       transactionAdapter = transactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)
        db = Room.databaseBuilder(this,
        DataBase::class.java, "transactions").build()
        recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager

        }
        // swipe to remove
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(recyclerview)


        addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }


    }

    private fun deleteTransaction(transactionDataHold: transactionDataHold) {
        deletedTransaction = transactionDataHold
        oldTransactions = transactions

        GlobalScope.launch {
            db.transactionDataobject().delete(transactionDataHold)

            transactions = transactions.filter { it.id != transactionDataHold.id }
            runOnUiThread {
                updateDashboard()
                transactionAdapter.setData(transactions)

            }
        }

    }

    private fun fatchAll(){
        GlobalScope.launch {
            transactions = db.transactionDataobject().getAll()

            runOnUiThread{
                updateDashboard()
                transactionAdapter.setData(transactions)
            }
        }

    }
    private fun updateDashboard(){
        val totalamount   = transactions.map { it.amount }.sum()
        val budugetAmmount = transactions.filter { it.amount>0 }.map { it.amount }.sum() //list of all amount grater then 0
        val expanseAmmount = totalamount-budugetAmmount

        //connect to xml
        balance.text = "$%.2f".format(totalamount)
        budget.text = "$%.2f".format(budugetAmmount)
        //expense.text = "$%.2f".format(expanseAmmount)

    }

    override fun onResume() {
        super.onResume()
        fatchAll()
    }

}