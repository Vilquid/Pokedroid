package fr.Vilquid.NatureCollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.database_ref
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.download_uri
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.pokemons_list
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.storage_reference
import java.util.*


class PokemonRepository
{
	object Singleton
	{
		private val BUCKET_URL: String = "gs://nature-collection-dd858.appspot.com"

		val storage_reference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL) // se connecter à notre espace de stockage

		val database_ref = FirebaseDatabase.getInstance().getReference("pokemons"); // se connecter à la ref pokémon

		val pokemons_list = arrayListOf<PokemonModel>() // liste contenant nos pokémons

		var download_uri: Uri? = null // contenir le lien de l'image courante
	}

	fun update_data(callback: () -> Unit)
	{
		database_ref.addValueEventListener(object : ValueEventListener // absorber la data
		{
			override fun onDataChange(snapshot: DataSnapshot)
			{
				pokemons_list.clear() // retirer les anciens pokémons de la liste (Màj)

				// récolter la liste
				for (ds in snapshot.children)
				{
					val pokemon = ds.getValue(PokemonModel::class.java) // construire un objet plante

					// vérification si la plante n'est pas nulle
					if (pokemon != null)
					{
						pokemons_list.add(pokemon) // ajout du pokémon à la liste
					}
				}

				callback() // actionner le callback
			}

			override fun onCancelled(p0: DatabaseError)
			{
				TODO("Not yet implemented")
			}
		})
	}

	// créer une fonction pour envoyer des fichiers sur le storage
	fun upload_image(file: Uri, callback: () -> Unit)
	{
		// vérifier que ce fichier n'est pas nul
		if (file != null)
		{
			val file_name = UUID.randomUUID().toString() + ".jpg"
			val reference = storage_reference.child(file_name)
			val uploadTask = reference.putFile(file)

			// démarrer
			uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

				// s'il y a eu un problème lors de l'envoie du fichier
				if (!task.isSuccessful)
				{
					task.exception?.let { throw it }
				}

				return@Continuation reference.downloadUrl
			}).addOnCompleteListener{ task ->

				// vérifier que tout a bien fonctionné
				if (task.isSuccessful)
				{
					// récupérer l'image
					download_uri = task.result

					callback()
				}
			}
		}
	}

	fun update_pokemon(pokemon: PokemonModel) = database_ref.child(pokemon.ID).setValue(pokemon)

	// insérer un nouveau poké dans la bdd
	fun insert_pokemon(pokemon: PokemonModel) = database_ref.child(pokemon.ID).setValue(pokemon)

	fun delete_pokemon(pokemon: PokemonModel) = database_ref.child(pokemon.ID).removeValue()
}