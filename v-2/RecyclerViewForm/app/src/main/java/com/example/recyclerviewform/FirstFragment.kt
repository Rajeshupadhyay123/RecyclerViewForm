package com.example.recyclerviewform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewform.databinding.FirstFragmentBinding

class FirstFragment : Fragment(){

    lateinit var firstData:String
    lateinit var secondData:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FirstFragmentBinding=DataBindingUtil.inflate(inflater,R.layout.first_fragment,container,false)

        binding.submitText.setOnClickListener {
            firstData=binding.firstDataText.text.toString()
            secondData=binding.secondDataText.text.toString()
            if(firstData!="" && secondData!=""){
                val action=FirstFragmentDirections.actionFirstFragmentToSecondFrgament(firstData,secondData,0L)
                findNavController().navigate(action)
            }
        }

        binding.detailButton.setOnClickListener {
            val action=FirstFragmentDirections.actionFirstFragmentToSecondFrgament("Empty","Empty",0L)
            findNavController().navigate(action)
        }



        return binding.root
    }
}