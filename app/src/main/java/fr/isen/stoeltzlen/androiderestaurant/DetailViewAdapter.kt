package fr.isen.stoeltzlen.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailViewAdapter (activity: AppCompatActivity, val items: List<String>): FragmentStateAdapter(activity){

    override fun getItemCount():Int = items.size

    override fun createFragment(position: Int): Fragment {
        return DetailFragment.newInstance(items[position])
    }
}