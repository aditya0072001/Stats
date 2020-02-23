package tripathi.aditya.stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class result : AppCompatActivity() {
    internal lateinit var resultClassInterval: TextView
    internal lateinit var resultFrequency: TextView
    internal lateinit var resultMean: TextView
    internal lateinit var resultMedian: TextView
    internal lateinit var resultMode: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        resultClassInterval = findViewById(R.id.resultClassInterval) as TextView
        resultFrequency = findViewById(R.id.resultFrequency) as TextView
        resultMean =findViewById(R.id.resultMean) as TextView
        resultMedian =findViewById(R.id.resultMedian) as TextView
        resultMode =findViewById(R.id.resultMode) as TextView

        var cResult=intent.getStringExtra("cResult")
        if(cResult=="true"){
            resultClassInterval.setText("C Interval")
            resultFrequency.setText("Frequency")
            resultMean.setText("Mean")
            resultMedian.setText("Median")
            resultMode.setText("Mode")
        }else{
            var cInterval=intent.getStringArrayListExtra("Interval")
            for(i in cInterval){
                resultClassInterval.append("\n"+i+"\n")
            }
            var freq=intent.getStringArrayListExtra("Fre")
            for(i in freq){
                resultFrequency.append("\n"+i+"\n")
            }
            var mean=intent.getStringExtra("Mean")
            resultMean.append("\n"+mean+"\n")
            var median=intent.getStringExtra("Median")
            resultMedian.append("\n"+median+"\n")
            var mode=intent.getStringExtra("Mode")
            resultMode.append("\n"+mode+"\n")
        }
    }
}
