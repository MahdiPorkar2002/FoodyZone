package com.example.dunifoodcomeback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunifoodcomeback.databinding.ActivityMainBinding
import com.example.dunifoodcomeback.databinding.DialogAddNewItemBinding
import com.example.dunifoodcomeback.databinding.DialogDeleteItemBinding
import com.example.dunifoodcomeback.databinding.DialogUpdateItemBinding

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val foodList = arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )

        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogAddNewItemBinding = DialogAddNewItemBinding.inflate(layoutInflater)

            dialog.setView(dialogAddNewItemBinding.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogAddNewItemBinding.dialogBtnDone.setOnClickListener {

                if (dialogAddNewItemBinding.dialogEdtNameFood.length() > 0 &&
                    dialogAddNewItemBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogAddNewItemBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogAddNewItemBinding.dialogEdtFoodDistance.length() > 0
                ) {
                    val txtName = dialogAddNewItemBinding.dialogEdtNameFood.text.toString()
                    val txtPrice = dialogAddNewItemBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogAddNewItemBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogAddNewItemBinding.dialogEdtFoodCity.text.toString()

                    val txtRatersCount = (1..150).random()
                    val txtRatingStars = (1..5).random().toFloat()

                    val randomForURL = (0..11).random()
                    val urlImage = foodList[randomForURL].urlImage

                    val newFood = Food(
                        txtName,
                        txtPrice,
                        txtDistance,
                        txtCity,
                        urlImage,
                        txtRatersCount,
                        txtRatingStars
                    )
                    myAdapter.addFood(newFood)

                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)


                } else {
                    Toast.makeText(this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show()
                }
            }


        }

        binding.edtSearch.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                // filter data
                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { foodItem ->
                    foodItem.txtSubject.contains(it.toString())
                }

                myAdapter.setData(filteredList as ArrayList<Food>)
            } else {
                // show all data
                myAdapter.setData(foodList.clone() as ArrayList<Food>)
            }
        }

    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogUpdateBinding = DialogUpdateItemBinding.inflate(layoutInflater)
        dialog.setView(dialogUpdateBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogUpdateBinding.dialogEdtNameFood.setText(food.txtSubject)
        dialogUpdateBinding.dialogEdtFoodCity.setText(food.txtCity)
        dialogUpdateBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        dialogUpdateBinding.dialogEdtFoodDistance.setText(food.txtDistance)


        dialogUpdateBinding.dialogBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogUpdateBinding.dialogUpdateBtnDone.setOnClickListener {

            if (dialogUpdateBinding.dialogEdtNameFood.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodCity.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodPrice.length() > 0 &&
                dialogUpdateBinding.dialogEdtFoodDistance.length() > 0
            ) {
                food.txtSubject = dialogUpdateBinding.dialogEdtNameFood.text.toString()
                food.txtPrice = dialogUpdateBinding.dialogEdtFoodPrice.text.toString()
                food.txtDistance = dialogUpdateBinding.dialogEdtFoodDistance.text.toString()
                food.txtCity = dialogUpdateBinding.dialogEdtFoodCity.text.toString()
                dialog.dismiss()
                myAdapter.updateFood(position)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onFoodLongClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()


        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food, position)

        }


    }


}