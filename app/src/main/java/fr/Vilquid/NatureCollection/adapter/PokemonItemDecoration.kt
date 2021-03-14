package fr.Vilquid.NatureCollection.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class PokemonItemDecoration : RecyclerView.ItemDecoration()
{
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
	{
		outRect.bottom = 20
	}
}