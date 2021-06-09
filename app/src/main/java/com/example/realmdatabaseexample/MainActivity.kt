package com.example.realmdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var realm: Realm? = null
    val dataModel = DataModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        btn_insertdata.setOnClickListener(this)
        btn_readdata.setOnClickListener(this)
        btn_updatedata.setOnClickListener(this)
        btn_deletedata.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_insertdata -> {
                addData()
            }

            R.id.btn_readdata -> {
                readData()
            }

            R.id.btn_updatedata -> {
                updateData()
            }

            R.id.btn_deletedata -> {
                deleteData()
            }
        }
    }

    fun deleteData() {

        try {
            val id: Long = edt_id.text.toString().toLong()
            val dataModel =
                realm!!.where(DataModel::class.java).equalTo("id", id).findFirst()
            realm!!.executeTransaction {
                dataModel?.deleteFromRealm()
            }
            clearFields()

            Log.d("Status","Data deleted !!!")

        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun updateData() {

        try {

            val id: Long = edt_id.text.toString().toLong()
            val dataModel =
                realm!!.where(DataModel::class.java).equalTo("id", id).findFirst()

            edt_name.setText(dataModel?.name)
            edt_email.setText(dataModel?.email)

            Log.d("Status","Data Fetched !!!")
        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun addData() {

        try {

            dataModel.id = edt_id.text.toString().toInt()
            dataModel.name = edt_name.text.toString()
            dataModel.email = edt_email.text.toString()

            realm!!.executeTransaction { realm -> realm.copyToRealm(dataModel) }

            clearFields()

            Log.d("Status","Data Inserted !!!")

        }catch (e:Exception){
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun readData() {

        try {

            val dataModels: List<DataModel> =
                realm!!.where(DataModel::class.java).findAll()

            for (i in dataModels.indices) {
                edt_id?.setText("" + dataModels[i].id)
                edt_name?.setText(dataModels[i].name)
                edt_email?.setText(dataModels[i].email)
            }

            Log.d("Status","Data Fetched !!!")

        } catch (e: Exception) {
            Log.d("Status","Something went Wrong !!!")
        }
    }

    fun clearFields(){
        edt_id.setText("")
        edt_name.setText("")
        edt_email.setText("")
    }
}