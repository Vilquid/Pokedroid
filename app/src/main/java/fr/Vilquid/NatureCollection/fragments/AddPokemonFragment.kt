package fr.Vilquid.NatureCollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.Vilquid.NatureCollection.MainActivity
import fr.Vilquid.NatureCollection.PokemonModel
import fr.Vilquid.NatureCollection.PokemonRepository
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.download_uri
import fr.Vilquid.NatureCollection.R
import java.util.*

class AddPokemonFragment(private val context: MainActivity): Fragment()
{
	private var file:Uri? = null
	private var uploaded_image:ImageView? = null

	override fun onCreateView(inflater: LayoutInflater,	container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		val view = inflater?.inflate(R.layout.fragment_add_pokemon, container, false)

		// récupérer uploaded_image pour lui associer un composant
		uploaded_image = view.findViewById(R.id.preview_image)

		// récupere le bouton charger image
		val pick_up_image_button = view.findViewById<Button>(R.id.upload_button)

		// lorsque l'on clique sur le bouton, ça ouvre les images du tel
		pick_up_image_button.setOnClickListener { pickupImage() }

		// récupérer le bouton confirmer
		val confirmButton = view.findViewById<Button>(R.id.confirm_button)
		confirmButton.setOnClickListener{ send_form(view) }

		return view
	}

	private fun send_form(view: View)
	{
		val repository = PokemonRepository()
		repository.upload_image(file!!)
		{
			val pokemon_name = view.findViewById<EditText>(R.id.name_input).text.toString()
			val pokemon_description = view.findViewById<EditText>(R.id.description_input).text.toString()
			val pokemon_tall = view.findViewById<Spinner>(R.id.tall_input).selectedItem.toString()
			val pokemon_puissance = view.findViewById<Spinner>(R.id.power_input).selectedItem.toString()
			val download_image_url = download_uri

			// créer un nouvel objet pokémonModel
			val pokemon = PokemonModel( UUID.randomUUID().toString(), pokemon_name,	pokemon_description, download_image_url.toString(), pokemon_tall, pokemon_puissance)
			repository.insert_pokemon(pokemon)
		}
	}

	private fun pickupImage()
	{
		val intent = Intent()
		intent.type = "image/"
		intent.action = Intent.ACTION_GET_CONTENT
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
	{
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == 47 && resultCode == Activity.RESULT_OK)
		{
			// vérifier si les données sont nulles
			if (data == null || data.data == null) return

			// récupérer l'image sélectionné
			val file = data.data

			// Màj
			uploaded_image?.setImageURI(file)
		}
	}
}