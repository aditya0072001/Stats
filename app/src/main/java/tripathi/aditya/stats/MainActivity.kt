package tripathi.aditya.stats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    internal lateinit var data: EditText
    internal lateinit var dataselect: Switch
    internal lateinit var dataf: EditText
    internal lateinit var formulas: TextView
    internal lateinit var mean: ToggleButton
    internal lateinit var median: ToggleButton
    internal lateinit var mode: ToggleButton
    internal lateinit var resetResult: Button
    internal lateinit var viewresult: Button
    var checkMean =false
    var checkMedian =false
    var checkMode =false
    var groupedData=false
    var fmean=0.00
    var cData= listOf<String>()
    var freqData= listOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mean = findViewById(R.id.mean) as ToggleButton
        median = findViewById(R.id.median) as ToggleButton
        mode = findViewById(R.id.mode) as ToggleButton
        resetResult = findViewById(R.id.resetResult) as Button
        viewresult = findViewById(R.id.viewresult) as Button
        data = findViewById(R.id.data) as EditText
        dataf = findViewById(R.id.dataf) as EditText
        dataselect = findViewById(R.id.dataselect) as Switch
        formulas = findViewById(R.id.formulas) as TextView
        val temp= """Formulas 
Mean x =∑fx/n"""
        formulas.setText(temp);
        checkData();
        calculateMean();
        calculateMedian();
        calculateMode();
        resetResultValue();
        viewResult();
    }

    private fun checkData(){
        if(dataselect.isChecked()){
            groupedData=true
           // dataf.isEnabled(true)
        }else if(!dataselect.isChecked()){
            groupedData=false
            //dataf.isEnabled(false)
        }
    }

    private fun resetResultValue() {
        resetResult.setOnClickListener(View.OnClickListener {
            if(data.getText().isNotEmpty()&&dataf.getText().isNotEmpty()){
                data.setText("")
                dataf.setText("")
                //calculate.setText("Calculated values will display here")
            }else{
                data.setText("")
                dataf.setText("")
                //calculate.setText("Calculated values will display here")
            }
        })
    }

    private fun calculateMode() {

        mode.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMode=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){
                    }else {
                        try {
                            var classData = data.getText().toString().split(",")
                            var cData = classData.map { it.toFloat() }
                            cData = cData.sorted()
                            var mCount = 0
                            var count = 0
                            var max = 0.00
                            for (i in cData) {
                                count = 0
                                for (j in cData) {
                                    if (j == i)
                                        ++count
                                }
                                if (count > mCount) {
                                    mCount = count;
                                    max = i.toDouble();
                                }

                            }
                            if (max == 0.00) {
                                // calculate.append("\nThere is no Mode")
                            } else {
                                // calculate.append("\nMode is "+max)
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "Enter Appropriate Values Buddy",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }

            } else { // The toggle is disabled
                checkMode=false
            }
        })

    }

    private fun calculateMedian() {
        median.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMedian=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){

                    }else{
                        try {
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
                            //calculate.append("\nMedian is "+valueM)
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Enter Appropriate Values Buddy",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }

            } else { // The toggle is disabled
                checkMedian=false
            }
        })

    }

    private fun calculateMean() {
        mean.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) { // The toggle is enabled
                checkMean=true
                if(data.getText().isNotEmpty()) {
                    if(groupedData){
                        try{
                            val classData = data.getText().toString().split(",")
                            this.cData=classData
                            val cData= classData.map{it.toFloat()}
                            val freqData = dataf.getText().toString().split(",")
                            this.freqData=freqData
                            val fData= freqData.map{it.toFloat()}
                            var sumfx=0.00
                            var N=0.00
                            var j=0
                            for(i in cData){
                                sumfx += (i * fData[j]).toDouble()
                                N+=fData[j]
                                j++
                            }
                            var fmean=sumfx/N
                            this.fmean=fmean
                            //calculate.append("\nMean is "+avg)
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Enter Appropriate Values Buddy",Toast.LENGTH_SHORT).show()
                        }
                    }else if(!groupedData){
                        try{
                            val classData = data.getText().toString().split(",")
                            this.cData=classData
                            val cData= classData.map{it.toFloat()}
                            var fmean=cData.average()
                            this.fmean=fmean
                            //calculate.append("\nMean is "+avg)
                        }catch (e:Exception){
                            Toast.makeText(applicationContext,"Enter Appropriate Values Buddy",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext,"Dont Enter frequency in ungrouped data",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext,"Enter Something Buddy Please",Toast.LENGTH_SHORT).show()
                }
            } else { // The toggle is disabled
            }
        })
    }

    private fun viewResult() {
        viewresult.setOnClickListener(View.OnClickListener{
            // Intent intent = new Intent(this@MainActivity,result::class.java)
            val intent = Intent(applicationContext, result::class.java)
            intent.putStringArrayListExtra("Interval",ArrayList(cData))
            intent.putExtra("Fre",ArrayList(freqData))
            intent.putExtra("Mean",fmean.toString())
            startActivity(intent)
        })
    }
}
