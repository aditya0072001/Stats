package tripathi.aditya.stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    internal lateinit var data: EditText
    internal lateinit var calculate: TextView
    internal lateinit var mean: Button
    internal lateinit var median: Button
    internal lateinit var mode: Button
    internal lateinit var resetResult: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mean = findViewById(R.id.mean) as Button
        median = findViewById(R.id.median) as Button
        mode = findViewById(R.id.mode) as Button
        resetResult = findViewById(R.id.resetResult) as Button
        data = findViewById(R.id.data) as EditText
        calculate = findViewById(R.id.calculate) as TextView
        calculateMean();
        calculateMedian();
        calculateMode();
        resetResultValue();
    }

    private fun resetResultValue() {
        resetResult.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()){
                data.setText("")
                calculate.setText("Calculated values will display here")
            }else{
                calculate.setText("Calculated values will display here")
            }
        })
    }

    private fun calculateMode() {

        mode.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()) {
                var classData = data.getText().toString().split(",")
                var cData= classData.map{it.toFloat()}
                cData=cData.sorted()
                var mCount=0
                var count=0
                var max=0.00
                for(i in cData){
                   count=0
                    for (j in cData){
                        if (j == i)
                            ++count
                    }
                    if (count > mCount) {
                        mCount = count;
                        max = i.toDouble();
                    }

                }
                if(max==0.00){
                    calculate.append("\nThere is no Mode")
                }else{
                    calculate.append("\nMode is "+max)
                }
            }else{
                Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun calculateMedian() {
        median.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()) {
                val classData = data.getText().toString().split(",")
                var cData= classData.map{it.toFloat()}
                cData=cData.sorted()
                val size=cData.size
                var valueM =0.00
                if(size%2==0){
                    valueM=(cData[(size/2)-1]+cData[(size/2)])/2.00
                }else{
                    valueM= cData[(size/2)].toDouble();
                }
                calculate.append("\nMedian is "+valueM)
            }else{
                Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun calculateMean() {
        mean.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()) {
                val classData = data.getText().toString().split(",")
                val cData= classData.map{it.toFloat()}
                val avg=cData.average()
                calculate.append("\nMean is "+avg)
            }else{
                Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
