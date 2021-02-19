package com.example.recyclerviewform

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewform.database.UserDatabase
import com.example.recyclerviewform.databinding.SecondFragmentBinding
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*

class SecondFrgament : Fragment() {

    lateinit var viewModel: SecondViewModel
    lateinit var viewModelFactory: SecondFactory

    @SuppressLint("UseCompatLoadingForDrawables")
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
        val userId = argument.userId

        Toast.makeText(activity, "$firstData $secondData", Toast.LENGTH_SHORT).show()

        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDao

        viewModelFactory = SecondFactory(firstData, secondData, userId, dataSource)
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

        //this is setting the swip left delete function
        val myCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                viewModel.startDelete(adapter.getPositionAt(position))
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                context?.let { ContextCompat.getColor(it, R.color.colorAccent) }?.let {
                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        binding.recyclerList,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.colorAccent
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                context!!,
                                R.color.design_default_color_on_primary
                            )
                        )
                        .addSwipeRightActionIcon(R.drawable.ic_archive)
                        .addBackgroundColor(it)
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate()
                }

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.recyclerList)



        return binding.root
    }
}