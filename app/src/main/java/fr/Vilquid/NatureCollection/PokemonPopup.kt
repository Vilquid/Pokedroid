package fr.Vilquid.NatureCollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.Vilquid.NatureCollection.adapter.PokemonAdapter

class PokemonPopup(private val adapter: PokemonAdapter, private val current_pokemon: PokemonModel) : Dialog(adapter.context)
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		setContentView(R.layout.popup_pokemon_details)
		setupComponents()
		setupCloseButton()
		setupDeleteButton()
		setupStarButton()
	}

	private fun updateStar(button: ImageView)
	{
		if (current_pokemon.liked)
		{
			button.setImageResource(R.drawable.ic_like)
		}

		else
		{
			button.setImageResource(R.drawable.ic_unlike)
		}
	}
	private fun setupStarButton() {
		// récupérer
		val star_button = findViewById<ImageView>(R.id.star_button)

		updateStar(star_button)

		// intéraction
		star_button.setOnClickListener{
			current_pokemon.liked = !current_pokemon.liked

			val repository = PokemonRepository()
			repository.update_pokemon(current_pokemon)

			updateStar(star_button)
		}
	}

	private fun setupDeleteButton() {
		findViewById<ImageView>(R.id.delete_button).setOnClickListener {
			val repository = PokemonRepository()
			repository.delete_pokemon(current_pokemon)
			dismiss()
		}
	}

	private fun setupCloseButton() {
		findViewById<ImageView>(R.id.close_button).setOnClickListener {
			dismiss()
		}
	}

	private fun setupComponents()
	{
		// actualiser l'image du pokémon
		val pokemon_image = findViewById<ImageView>(R.id.image_item)
		Glide.with(adapter.context).load(Uri.parse(current_pokemon.image_URL)).into(pokemon_image)

		// actuliser son nom
		findViewById<TextView>(R.id.popup_pokemon_name).text = current_pokemon.name

		// actualiser la description du pokémon
		findViewById<TextView>(R.id.popup_pokemon_description_subtitle).text = current_pokemon.description

		// actualiser la  du pokémon
		findViewById<TextView>(R.id.popup_pokemon_tall_subtitle).text = current_pokemon.taille

		// actualiser la  du pokémon
		findViewById<TextView>(R.id.popup_pokemon_power_subtitle).text = current_pokemon.puissance
	}
}