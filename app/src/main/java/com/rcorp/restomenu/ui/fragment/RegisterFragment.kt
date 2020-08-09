package com.rcorp.restomenu.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.rcorp.restomenu.R
import com.rcorp.restomenu.databinding.FragmentRegisterBinding
import com.rcorp.restomenu.ui.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer {
            if (it == RegisterViewModel.RegisterState.DONE) {
                Snackbar.make(requireActivity().window.decorView.findViewById(android.R.id.content), getString(R.string.register_screen_success), Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}