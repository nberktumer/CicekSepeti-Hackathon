package com.hackathon.ui.home

import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hackathon.R
import com.hackathon.databinding.HomeFragmentBinding
import com.hackathon.di.ILogger
import com.hackathon.lib.builders.DialogBuilder
import com.hackathon.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import androidx.core.graphics.drawable.DrawableCompat
import android.os.Build
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable
import android.graphics.Canvas
import com.google.maps.android.ui.IconGenerator


class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class), OnMapReadyCallback {
    private val logger: ILogger by inject()
    private lateinit var dataBinding: HomeFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Base Fragment on create view, calls view model on screen created
        super.onCreateView(inflater, container, savedInstanceState)

        // Bind View Model to the layout
        dataBinding = HomeFragmentBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = this

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.solveProblem()

        return dataBinding.root
    }

    private fun getStoreVector(generator: IconGenerator, color: String): BitmapDescriptor {
        generator.setStyle(when(color) {
            "Yeşil" -> (IconGenerator.STYLE_GREEN)
            "Kırmızı" -> (IconGenerator.STYLE_RED)
            "Mavi" -> (IconGenerator.STYLE_BLUE)
            else -> (IconGenerator.STYLE_PURPLE)
        })
        val icon = generator.makeIcon("$color Mağaza")
        return BitmapDescriptorFactory.fromBitmap(icon)
    }


    private fun convertColor(color: String): Float {
        return when(color) {
            "Yeşil" -> BitmapDescriptorFactory.HUE_GREEN
            "Kırmızı" -> BitmapDescriptorFactory.HUE_RED
            "Mavi" -> BitmapDescriptorFactory.HUE_BLUE
            else -> BitmapDescriptorFactory.HUE_CYAN
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val iconFactory = IconGenerator(requireContext())
        viewModel.onProblemSolved.runWhenFinished(this,
                onSuccess = {
                    dataBinding.progressLayout.visibility = View.GONE

                    it.stores.forEach {
                        val store = it.value.store

                        googleMap?.addMarker(
                                MarkerOptions()
                                        .position(LatLng(store.lat, store.long))
                                        .icon(getStoreVector(iconFactory, store.name))
                                        .title(store.name))

                        it.value.jobs.forEach {
                            googleMap?.addMarker(
                                    MarkerOptions()
                                            .position(LatLng(it.lat, it.long))
                                            .icon(BitmapDescriptorFactory.defaultMarker(convertColor(store.name)))
                                            .title(it.id.toString())
                                            )
                        }
                    }
                    val firstStore = it.stores.values.first().store
                    googleMap?.moveCamera(CameraUpdateFactory.zoomTo(12F))
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(firstStore.lat, firstStore.long)))
                },
                onError = { err ->
                    DialogBuilder(requireContext()).let {
                        it.title = err.parseError(requireContext()).first
                        it.content = err.parseError(requireContext()).second
                        it.cancelable = false
                        it.positiveButton {
                            this.content = getString(R.string.retry)
                            this.onClick = { dialog, _ ->
                                viewModel.solveProblem()
                                dialog.dismiss()
                            }
                        }
                        it.build()
                    }.show()
                })
    }
}
