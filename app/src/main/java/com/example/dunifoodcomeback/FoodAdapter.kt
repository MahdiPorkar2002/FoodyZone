package com.example.dunifoodcomeback

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dunifoodcomeback.databinding.ItemFoodBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*
import kotlin.collections.ArrayList

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bindData(position: Int) {

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtDistance.text = data[position].txtDistance + " miles from you"
            binding.itemTxtPrice.text = "$" + data[position].txtPrice + " vip"
            binding.ratingBar.rating = data[position].rating
            binding.itemRatersCount.text = "(" + data[position].ratersCount.toString() + " Ratings)"

            Glide.with(binding.root.context).load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4)).into(binding.itemImgMain)


            itemView.setOnClickListener {
                foodEvents.onFoodClicked(data[adapterPosition], adapterPosition)
            }
            itemView.setOnLongClickListener {
                foodEvents.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood: Food) {

        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(position: Int) {
        notifyItemChanged(position)
    }

    fun setData(newList: ArrayList<Food>) {
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvents {
        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, position: Int)
    }
}