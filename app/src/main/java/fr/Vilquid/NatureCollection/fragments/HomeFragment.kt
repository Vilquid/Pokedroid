package fr.Vilquid.NatureCollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.Vilquid.NatureCollection.MainActivity
import fr.Vilquid.NatureCollection.PokemonRepository.Singleton.pokemons_list
import fr.Vilquid.NatureCollection.R
import fr.Vilquid.NatureCollection.adapter.PokemonAdapter
import fr.Vilquid.NatureCollection.adapter.PokemonItemDecoration


class HomeFragment(private val context: MainActivity) : Fragment()
{
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		val view = inflater?.inflate(R.layout.fragment_home, container, false)

		// récupérer le premier reclycler
		val horizontal_recycler_view = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
		horizontal_recycler_view.adapter = PokemonAdapter(context, pokemons_list.filter { it.liked }, R.layout.item_horizontal_pokemon)

		// récupérer le deuxième reclycler
		val verticel_recycler_view = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
		verticel_recycler_view.adapter = PokemonAdapter(context, pokemons_list, R.layout.item_vertical_pokemon)
		verticel_recycler_view.addItemDecoration((PokemonItemDecoration()))

		return view
	}
}