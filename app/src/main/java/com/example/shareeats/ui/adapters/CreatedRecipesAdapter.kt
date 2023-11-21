package com.example.shareeats.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shareeats.databinding.RecipeHomeListBinding
import com.example.shareeats.model.Recipe
import com.example.shareeats.model.Users
import com.example.shareeats.ui.CreatedRecipeActivity
import com.example.shareeats.ui.RecipeDetailsActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class CreatedRecipesAdapter(private val context: CreatedRecipeActivity, private var recipeList: ArrayList<Recipe> )
    : RecyclerView.Adapter<CreatedRecipesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, context)

    }

    override fun getItemCount(): Int {

        return recipeList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        recipeList[position].let {

            holder.bind(
                it,
                position
            )

        }

    }


    class ViewHolder(val binding: RecipeHomeListBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe, position: Int) {

            Glide.with(context)
                .load(recipe.imageUrl)
                .apply(RequestOptions().centerCrop().override(50, 50))
                .into(binding.imgRecipe)

            binding.tvRecipeName.text = recipe.name
            binding.tvCookingTime.text = "Cooking time : ${recipe.cookingTime}"


            var databaseRef = Firebase.database.reference
            var creatorID = recipe.createdBy.toString()


            val objectListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {



                    val userInfo = snapshot.getValue<Users>()
                    binding.tvCreatedBy.text = "Created by : @${userInfo?.name}"

                }



                override fun onCancelled(error: DatabaseError) {


                }

            }
            databaseRef
                .child("users")
                .child(creatorID)
                .addListenerForSingleValueEvent(objectListener)



            binding.linearParent.setOnClickListener {

                val objectListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {



                        val userInfo = snapshot.getValue<Users>()
                        val intent = Intent(context, RecipeDetailsActivity::class.java)
                        intent.putExtra("userName", userInfo)
                        intent.putExtra("Recipe", recipe)
                        context.startActivity(intent)
                    }



                    override fun onCancelled(error: DatabaseError) {


                    }

                }
                databaseRef
                    .child("users")
                    .child(creatorID)
                    .child("created_recipes")
                    .addListenerForSingleValueEvent(objectListener)


            }

        }
    }
}
