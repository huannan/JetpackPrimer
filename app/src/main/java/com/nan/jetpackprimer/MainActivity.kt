package com.nan.jetpackprimer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class RouteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RouteButton)
        val routePath = typedArray.getString(R.styleable.RouteButton_routePath)
        typedArray.recycle()

        setOnClickListener {
            routePath?.let {
                val intent = Intent(context, Class.forName(routePath))
                context.startActivity(intent)
            }
        }
    }

}