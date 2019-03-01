package com.hackathon.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hackathon.R
import com.hackathon.databinding.HomeFragmentBinding
import com.hackathon.di.ILogger
import com.hackathon.lib.builders.DialogBuilder
import com.hackathon.ui.base.BaseFragment
import org.koin.android.ext.android.inject


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

    override fun onMapReady(googleMap: GoogleMap?) {
        val sydney1 = LatLng(-33.852, 151.211)
        val sydney2 = LatLng(-33.852, 151.214)
        val sydney3 = LatLng(-33.852, 151.217)
        googleMap?.addMarker(MarkerOptions().position(sydney1)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("Marker in Sydney"))
        googleMap?.addMarker(MarkerOptions().position(sydney2)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Marker in Sydney"))
        googleMap?.addMarker(MarkerOptions().position(sydney3)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title("Marker in Sydney"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney1))

        viewModel.onProblemSolved.runWhenFinished(this,
                onSuccess = {
                    dataBinding.progressLayout.visibility = View.GONE
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
