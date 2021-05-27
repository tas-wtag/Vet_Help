package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FooFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.example_fragment, parent, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
    }
}