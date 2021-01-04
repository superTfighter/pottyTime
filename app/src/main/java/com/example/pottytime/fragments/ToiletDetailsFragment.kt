package com.example.pottytime.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pottytime.R
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType
import com.example.pottytime.databinding.FragmentToiletDetailsBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class ToiletDetailsFragment(private var toilet: Toilet) : Fragment() {

    private lateinit var ctx : Context

    companion object {

        fun newInstance(toilet: Toilet): ToiletDetailsFragment {
            return ToiletDetailsFragment(toilet)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context;

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding =
            FragmentToiletDetailsBinding.inflate(inflater, container, false)

        setImage(toilet.type,binding);

        setMap(binding,this.ctx);

        binding.toilet = this.toilet
        return binding.root
    }

    private fun setImage(toiletType: ToiletType?, binding :FragmentToiletDetailsBinding)
    {
        when (toiletType) {
            ToiletType.BURGERKING -> {
                binding.image.setImageResource(R.drawable.burgerking)
            }
            ToiletType.MCDONALDS -> {
            binding.image.setImageResource(R.drawable.mcdonaldshu)
            }
            ToiletType.KFC -> {
                binding.image.setImageResource(R.drawable.kfc)
            }
            else -> {
                binding.image.setImageResource(R.drawable.other)
            }
        }

    }

    private fun setMap(binding :FragmentToiletDetailsBinding, ctx: Context){

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        val map : MapView = binding.map;
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setMultiTouchControls(true);

        //Set default position
        val mapController = map.controller
        mapController.setZoom(9.5)

        val startPoint = GeoPoint(toilet.latitude ?: 0.0, toilet.longitude ?: 0.0);

        mapController.setCenter(startPoint)

        if(!(toilet.latitude == null || toilet.longitude == null)){
            //Marker
            this.addMarkerToPosition(toilet.name,toilet.latitude ?: 0.0,toilet.longitude ?: 0.0,map);
        }


    }

    private fun addMarkerToPosition(name: String,lan : Double, lon : Double,map : MapView, icon : Drawable = resources.getDrawable(R.drawable.ic_test) )
    {
        //Marker
        val m = Marker(map)
        m.position = GeoPoint(lan, lon)
        m.textLabelBackgroundColor = Color.TRANSPARENT
        m.textLabelForegroundColor = Color.RED
        m.textLabelFontSize = 40
        m.setIcon(icon);
        m.setTitle(name)
        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP)
        map.getOverlays()
            .add(m)

    }
}