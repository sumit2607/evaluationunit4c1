package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen+
       labelInput.addTextChangedListener {
           if (it!!.count()>0)
               labelLayout.error = null
       }
        amountInput.addTextChangedListener {
            if(it!!.count() > 0)
                amountLayout.error = null
        }
        addTransactionBtn.setOnClickListener{
            val label = labelInput.text.toString()
            val description = descriptionInput.text.toString()
            val amount = amountInput.text.toString().toDouble()
            if(label.isEmpty())
                labelLayout.error = "please enter a valid Description"

            else {
                val transaction  =transactionDataHold(0, label, amount, description)
                insert(transaction)
            }
            closeBtn.setOnClickListener{
                finish()
            }
        }




    }

    private fun insert(transaction: transactionDataHold) {
        val db = Room.databaseBuilder(this,
            DataBase::class.java,
            "transactions").build()

        GlobalScope.launch {
            db.transactionDataobject().insertAll(transaction)
            finish()
        }
    }
}