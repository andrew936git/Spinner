package com.example.spinner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private val role = listOf(
        "Мастер",
        "Механик",
        "Инженер по гарантии",
        "Продавец",
        "Администратор",
        "Системный администратор"
    )

    var textRole: String? = null
    var sortText: String? = null
    private val personList = mutableListOf<Person>()
    private var listAdapter: ListAdapter? = null
    private var person: Person? = null

    private lateinit var nameEditTextET: EditText
    private lateinit var surnameEditTextET: EditText
    private lateinit var ageEditTextET: EditText
    private lateinit var spinnerSP: Spinner
    private lateinit var toolbarSpinner: Spinner
    private lateinit var toolbar: Toolbar
    private lateinit var saveButtonBT: Button
    private lateinit var listViewLV: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("ResourceType")
    private fun init(){
        nameEditTextET = findViewById(R.id.nameEditTextET)
        surnameEditTextET = findViewById(R.id.surnameEditTextET)
        ageEditTextET = findViewById(R.id.ageEditTextET)
        spinnerSP = findViewById(R.id.spinnerSP)
        toolbarSpinner = findViewById(R.id.toolbarSpinner)
        saveButtonBT = findViewById(R.id.saveButtonBT)
        listViewLV = findViewById(R.id.listViewLV)
        toolbar = findViewById(R.id.toolbar)
        title = "Подбор персонала"
        setSupportActionBar(toolbar)

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, role)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSP.adapter = adapterSpinner

        val toolbarAdapter = ArrayAdapter(this@MainActivity,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.post))
        toolbarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toolbarSpinner.adapter = toolbarAdapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    textRole = item
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        val itemSelectedListenerToolbar: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    sortText = item
                    if (sortText == "Сброс") {
                        listViewLV.adapter = listAdapter
                    } else {
                        val list = mutableListOf<Person>()
                        for (i in personList){
                            if (i.post == sortText) list.add(i)
                        }
                        val adapterSort = ListAdapter(this@MainActivity, list)
                        listViewLV.adapter = adapterSort
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        spinnerSP.onItemSelectedListener = itemSelectedListener
        toolbarSpinner.onItemSelectedListener = itemSelectedListenerToolbar

        saveButtonBT.setOnClickListener{
            person = Person(nameEditTextET.text.toString(),
                surnameEditTextET.text.toString(), ageEditTextET.text.toString(), textRole!!)
            personList.add(person!!)

            listAdapter = ListAdapter(this@MainActivity, personList)
            listViewLV.adapter = listAdapter
            listAdapter!!.notifyDataSetChanged()

            nameEditTextET.text.clear()
            surnameEditTextET.text.clear()
            ageEditTextET.text.clear()
        }

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val note = listAdapter!!.getItem(position)
                listAdapter!!.remove(note)
                Toast.makeText(this, "Пользователь удален", Toast.LENGTH_LONG).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.exit_menu -> {
                finishAffinity()
                finish()
            }
        }
            return super.onOptionsItemSelected(item)
    }

}