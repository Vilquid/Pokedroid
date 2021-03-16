package fr.Vilquid.NatureCollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.Vilquid.NatureCollection.fragments.AddPokemonFragment
import fr.Vilquid.NatureCollection.fragments.CollectionFragment
import fr.Vilquid.NatureCollection.fragments.HomeFragment

class MainActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		load_fragment(HomeFragment(this), R.string.home_page_title) // page au démarrage

		// importer la bottom nav view
		val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
		navigationView.setOnNavigationItemReselectedListener {
			when(it.itemId)
			{
				R.id.home_page ->
				{
					load_fragment(HomeFragment(this), R.string.home_page_title)

					return@setOnNavigationItemReselectedListener true
				}

				R.id.collection_page ->
				{
					load_fragment(CollectionFragment(this), R.string.collection_page_title)

					return@setOnNavigationItemReselectedListener true
				}

				R.id.add_pokemon_page ->
				{
					load_fragment(AddPokemonFragment(this), R.string.add_pokemon_page_title)

					return@setOnNavigationItemReselectedListener true
				}

				else -> false
			}
		}
	}

	private fun load_fragment(fragment: Fragment, string: Int)
	{
		// charger notre repo
		val repository = PokemonRepository()

		// actualiser le titre de la page
		findViewById<TextView>(R.id.page_title).text = resources.getString(string)

		// update de la liste
		repository.update_data {
			val transaction = supportFragmentManager.beginTransaction() // injecter le fragment container

			transaction.replace(R.id.fragment_container, fragment)
			transaction.addToBackStack(null)
			transaction.commit()
		}
	}
}