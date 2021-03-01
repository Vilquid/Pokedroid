package fr.Vilquid.NatureCollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.Vilquid.NatureCollection.*

class PokemonAdapter(val context: MainActivity, private val pokemon_list: List<PokemonModel>, private val layout_id: Int) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>()
{
	// boite pour ranger tous les composants à controller
	class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
	{
		// image du pokémon
		val pokemon_image = view.findViewById<ImageView>(R.id.image_item)
		val pokemon_name:TextView? = view.findViewById(R.id.name_item)
		val pokemon_description:TextView? = view.findViewById(R.id.description_item)
		val star_icon = view.findViewById<ImageView>(R.id.star_icone)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val view = LayoutInflater.from(parent.context).inflate(layout_id , parent, false)

		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		//  récupérer les info du pokémon
		val current_pokemon = pokemon_list[position]

		// récupérer le repo
		val repository = PokemonRepository()

		// utiliser glide pour récupérer l'image à partir de son lien
		Glide.with(context).load(Uri.parse(current_pokemon.image_URL)).into(holder.pokemon_image)

		// mettre à jour les noms
		holder.pokemon_name?.text = current_pokemon.name

		// mettre à jour les descriptionds
		holder.pokemon_description?.text = current_pokemon.description

		// vérif si le pokémon a été liké
		if(current_pokemon.liked)
		{
			holder.star_icon.setImageResource(R.drawable.ic_unlike)
		}

		else
		{
			holder.star_icon.setImageResource(R.drawable.ic_like)
		}

		// rajouter une interaction sur cette étoile
		holder.star_icon.setOnClickListener	{
			current_pokemon.liked = !current_pokemon.liked

			// màj de l'objet pokémon
			repository.update_pokemon(current_pokemon)
		}

		// intéraction lors d'un cilck sur une plante
		holder.itemView.setOnClickListener {
			PokemonPopup(this, current_pokemon).show()
		}
	}

	override fun getItemCount(): Int = pokemon_list.size
}