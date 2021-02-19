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
import com.example.recyclerviewform.databinding.SecondFragmentBinding

class SecondFrgament : Fragment() {

    lateinit var viewModel: SecondViewModel
    lateinit var viewModelFactory: SecondFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SecondFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.second_fragment, container, false)

        val argument = SecondFrgamentArgs.fromBundle(requireArguments())
        val firstData = argument.firstData
        val secondData = argument.secondData
        val userId=argument.userId

        Toast.makeText(activity, "$firstData $secondData", Toast.LENGTH_SHORT).show()

        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDao

        viewModelFactory = SecondFactory(firstData, secondData,userId, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SecondViewModel::class.java)

        val adapter = UserAdapter(UserListener { userId ->
            //Toast.makeText(activity, "$userId", Toast.LENGTH_SHORT).show()
            //viewModel.onNavigateDetail(userId)
            val action = SecondFrgamentDirections.actionSecondFrgamentToUpdateFragment(userId)
            findNavController().navigate(action)

        })
        binding.recyclerList.adapter = adapter

        viewModel.table.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

       /* viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { userId ->
            userId?.let {
                val action = SecondFrgamentDirections.actionSecondFrgamentToUpdateFragment(it)
                findNavController().navigate(action)
            }
            viewModel.onNavigated()
        })*/



        return binding.root
    }
}