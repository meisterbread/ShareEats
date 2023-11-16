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
import com.example.shareeats.states.HomeState
import com.example.shareeats.ui.CreateRecipeActivity
import com.example.shareeats.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class HomeAdapter(private val context: MainActivity, private var recipeList: ArrayList<Recipe> )
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() { // - This line is an interface. Click on the "StudentsAdapters" class name and implement all the methods of the interface.


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder { // binibigyan ng relationship yung adapter doon sa UI na ginawa mo per each item (item_list)
        val binding =
            RecipeHomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun getItemCount(): Int { // Ilan ang items sa list natin

        return recipeList.size

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) { // nagbibind ng list to each item

        recipeList[position].let { // gets the position (the position of the list) binds it to the text views depending on the position.

            holder.bind(
                it,
                position
            ) // calling of function bind in class ViewHolder to perform the binding

        }

    }

    class ViewHolder(val binding: RecipeHomeListBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        //dito magbibind ng mga views natin

        fun bind(recipe: Recipe, position: Int) {
            // it sets the data to the text views and image views on EACH ITEM (DEPENDS ON POSITION).

            Glide.with(context)
                .load(recipe.imageUrl)
                .apply(RequestOptions().centerCrop().override(50, 50))
                .into(binding.imgRecipe)

            binding.tvRecipeName.text = recipe.name
            binding.tvCookingTime.text = recipe.cookingTime


            var databaseRef = Firebase.database.reference
            var creatorID = recipe.createdBy.toString()


            val objectListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //snapshot - object that handles the current data from the db
                    //since snapshot will handle a list, it needs to loop through each record it will retrieve
                    //and give to our array list (contactList)

                    //needs for loop


                    val userInfo = snapshot.getValue<Users>()
                    binding.tvCreatedBy.text = "Created by : ${userInfo?.name}"

                }

                //after retrieving the record, the state will be default


                override fun onCancelled(error: DatabaseError) {


                }

            }
            databaseRef
                .child("users")
                .child(creatorID)
                .addListenerForSingleValueEvent(objectListener)

            binding.linearParent.setOnClickListener {

//                val intent = Intent(context, CreateRecipeActivity::class.java)
//                intent.putExtra("Recipe", recipe)

//                context.startActivity(intent)
            }
//
//

        }
    }
}
