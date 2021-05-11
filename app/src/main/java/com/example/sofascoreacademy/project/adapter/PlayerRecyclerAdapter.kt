
package com.example.sofascoreacademy.project.adapter
/*
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.transform.CircleCropTransformation
import com.example.sofascoreacademy.R
import com.example.sofascoreacademy.databinding.PlayerItemViewBinding
import com.example.sofascoreacademy.project.model.Footballer
import com.example.sofascoreacademy.project.ui.citydetail.CityDetail

class PlayerRecyclerAdapter(
    private val context: Context,
    private val playerList: ArrayList<Footballer>
) : RecyclerView.Adapter<PlayerRecyclerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PlayerItemViewBinding.bind(view)

        init {
            view.setOnClickListener {
                it.context.startActivity(
                    Intent(
                        it.context,
                        CityDetail::class.java
                    ).putExtra("extra", playerList[adapterPosition])
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.location_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playerList[position]
        holder.binding.itemImage.load(player.image) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.hns)
        }
        holder.binding.itemName.text = player.name.plus(" ").plus(player.surname)
        holder.binding.itemClub.text = player.club
        holder.binding.itemPos.text =
            context.getString(R.string.textPosition).plus(": ").plus(context.getString(player.position.id))
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

}


*/
