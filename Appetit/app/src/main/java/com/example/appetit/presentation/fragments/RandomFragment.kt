package com.example.appetit.presentation.fragments

import android.animation.Animator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appetit.R
import com.example.appetit.databinding.FragmentRandomBinding
import com.example.appetit.presentation.viewmodels.RandomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sqrt

@AndroidEntryPoint
class RandomFragment : Fragment() {

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RandomViewModel by viewModels()

    private lateinit var sensorManager: SensorManager
    private var shakeThreshold: Float = 1.5f
    private lateinit var shakeListener: SensorEventListener

    private var isAnimationPlaying = false
    private var randomRecipeUri: String? = null

    private val shakeAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.shake).apply {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.hintTextView.postDelayed({
                        if (isAdded && !isAnimationPlaying) {
                            binding.hintTextView.startAnimation(shakeAnimation)
                        }
                    }, 1000)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupShakeAnimation()
        setupSensorManager()
        setupObservers()
        setupSpinner()
    }

    private fun setupSpinner() {
        binding.mealTypeSpinner.setSelection(2)
        val mealTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.meal_types_random,
            R.layout.custom_spinner_item
        )
        mealTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.mealTypeSpinner.adapter = mealTypeAdapter
    }

    private fun setupShakeAnimation() {
        binding.hintTextView.visibility = View.VISIBLE
        binding.hintTextView.startAnimation(shakeAnimation)
        binding.animationView.cancelAnimation()
        binding.animationView.visibility = View.GONE
    }

    private fun setupSensorManager() {
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val gX = x / SensorManager.GRAVITY_EARTH
                val gY = y / SensorManager.GRAVITY_EARTH
                val gZ = z / SensorManager.GRAVITY_EARTH

                val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

                if (gForce > shakeThreshold && !isAnimationPlaying) {
                    onShakeDetected()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(shakeListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    private fun setupObservers() {
        viewModel.randomRecipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                randomRecipeUri = it.uri
                if (isAnimationPlaying) {
                    navigateToRecipeFragment()
                }
            }
        }
    }

    private fun onShakeDetected() {
        isAnimationPlaying = true
        randomRecipeUri = null

        binding.hintTextView.clearAnimation()
        binding.hintTextView.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE

        binding.animationView.playAnimation()
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                if (isAdded) {
                    if (randomRecipeUri == null) {
                        binding.animationView.playAnimation()
                    } else {
                        navigateToRecipeFragment()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        val selectedMealType = listOf(binding.mealTypeSpinner.selectedItem.toString())
        viewModel.getRandomRecipe(selectedMealType)
    }

    private fun navigateToRecipeFragment() {
        randomRecipeUri?.let { uri ->
            val action = RandomFragmentDirections.actionNavigationRandomToRecipeFragment(uri)
            findNavController().navigate(action)
        }
        isAnimationPlaying = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.hintTextView.clearAnimation()
        binding.animationView.cancelAnimation()
        sensorManager.unregisterListener(shakeListener)
        _binding = null
    }
}
