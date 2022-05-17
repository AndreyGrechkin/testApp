package com.example.testapp.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testapp.R
import com.example.testapp.databinding.ChartFragmentBinding
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class ChartFragment: Fragment(R.layout.chart_fragment) {
    private val binding by viewBinding(ChartFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartView = binding.lineChart
        val chartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("Chart")
            .backgroundColor(Color.LTGRAY)
            .dataLabelsEnabled(true)
            .series(arrayOf(
                AASeriesElement()
                    .name("Line 1")
                    .data(arrayOf(5.0, 7.5, 6.0, 4.5, 10.0 , 8.0, 10.5, 17.0, 15.1, 6.5)),
                AASeriesElement()
                    .name("Line 2")
                    .data(arrayOf(25.0, 17.5, 16.0, 6.5, 12.0 , 18.0, 13.5, 7.0, 5.1, 16.5)),
                AASeriesElement()
                    .name("Line 3")
                    .data(arrayOf(15.0, 27.5, 10.0, 14.5, 20.0 , 16.0, 7.5, 27.0, 25.1, 11.5))
            ))
        chartView.aa_drawChartWithChartModel(chartModel)
    }
}