package com.example.recyclerviewform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewform.database.UserDatabase
import com.example.recyclerviewform.databinding.UpdateFragmentBinding
import kotlinx.android.synthetic.*

class UpdateFragment : Fragment() {
    lateinit var viewModel: UpdateViewModel
    lateinit var viewModelFactory: UpdateModelFactory
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: UpdateFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.update_fragment, container, false)
        val argument = UpdateFragmentArgs.fromBundle(requireArguments())
        val userId = argument.userId

        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDao

        viewModelFactory = UpdateModelFactory(userId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpdateViewModel::class.java)

        Toast.makeText(activity, "$userId", Toast.LENGTH_SHORT).show()

        viewModel.table.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.firstDataUpdate.setText(it.firstData)
                binding.secondDataUpdate.setText(it.secondData)
            }
        })
        binding.updateButton.setOnClickListener {
            val firstData=binding.firstDataUpdate.text.toString()
            val secondData=binding.secondDataUpdate.text.toString()
            val action=UpdateFragmentDirections.actionUpdateFragmentToSecondFrgament(firstData,secondData,userId)
            findNavController().navigate(action)
        }

        return binding.root
    }
}